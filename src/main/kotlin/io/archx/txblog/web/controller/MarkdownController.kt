package io.archx.txblog.web.controller

import io.archx.txblog.common.def.CodeDef
import io.archx.txblog.common.getLogger
import io.archx.txblog.common.qiniu.Kodo
import io.archx.txblog.common.web.Result
import io.archx.txblog.data.service.DraftService
import io.archx.txblog.web.bean.MarkdownUnZip
import io.archx.txblog.web.bean.MarkdownZip
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import springfox.documentation.annotations.ApiIgnore
import java.io.ByteArrayOutputStream
import java.net.URLEncoder
import java.nio.charset.Charset
import java.util.zip.ZipOutputStream
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("markdown")
@Api(tags = ["Markdown 控制器"])
class MarkdownController(
    val kodo: Kodo,
    val draftService: DraftService) {

    val logger = getLogger()

    @PostMapping("/import")
    @ApiOperation("导入Markdown", notes = "导入zip格式的资源压缩包")
    @ApiImplicitParam("资源文件", name = "file", paramType = "body")
    fun import(@RequestParam("file") file: MultipartFile): Result<String> {
        // condition
        if (file.isEmpty) {
            return Result.code(CodeDef.MISSING_PARAMETER)
        }

        if ("application/x-zip-compressed" != file.contentType && "application/zip" != file.contentType) {
            return Result.code(CodeDef.UNSUPPORTED_FILE_FORMAT)
        }

        val zip = MarkdownUnZip(file.inputStream)
        // 替换掉图片
        zip.replace { entry ->
            try {
                val v = kodo.upload(entry.value)
                KV(entry.key, v)
            } catch (ex: RuntimeException) {
                logger.error("upload image error [ key = '{}', err = '{}'] ", entry.key, ex.message)
                KV(entry.key, entry.key)
            }
        }

        val markdown = zip.markdown()
        return if (StringUtils.hasText(markdown)) Result.ok(markdown!!) else Result.code(CodeDef.INVALID_CONTENT)
    }

    @GetMapping("/export/{id}")
    @ApiOperation("导出Markdown", notes = "根据文章ID导出Markdown文件")
    @ApiImplicitParam("草稿文章ID", name = "id", paramType = "path")
    fun export(
        @PathVariable("id") id: Long,
        @ApiIgnore @RequestHeader("User-Agent") ua: String,
        @ApiIgnore response: HttpServletResponse): String {
        val draft = draftService.findById(id) ?: return "Draft not found"

        val out = ByteArrayOutputStream()
        val zip = MarkdownZip(draft, ZipOutputStream(out))
        // zip 压缩
        zip.zip()
        zip.flush()

        var filename = draft.title + ".zip"
        if (filename.length > 17) {
            filename = filename.substring(0, 17) + ".zip"
        }
        filename = URLEncoder.encode(filename, "UTF-8")
        val utf8 = Charset.forName("UTF-8")
        val iso = Charset.forName("ISO8859-1")

        // 交给框架关闭
        filename = when {
            ua.toLowerCase().indexOf("firefox") > 0 -> {
                String(filename.toByteArray(utf8), iso) // firefox浏览器
            }
            ua.toUpperCase().indexOf("MSIE") > 0 -> {
                URLEncoder.encode(filename, "UTF-8")// IE浏览器
            }
            ua.toUpperCase().indexOf("CHROME") > 0 -> {
                String(filename.toByteArray(utf8), iso)// 谷歌
            }
            else -> {
                String(filename.toByteArray(utf8), iso)//
            }
        }
        response.setHeader("Content-Type", "application/zip;charset=utf-8");
        response.contentType = "application/zip";
        // 下载文件能正常显示中文
        response.setHeader("Content-Disposition", "attachment;filename=$filename");

        out.use { it ->
            val data = it.toByteArray()
            val ops = response.outputStream
            ops.write(data)
            ops.flush() // 交给框架关闭
        }

        return ""
    }

    class KV<K, V>(override val key: K, override val value: V) : Map.Entry<K, V>
}
package io.archx.txblog.web.controller

import io.archx.txblog.common.getLogger
import io.archx.txblog.common.qiniu.Kodo
import io.archx.txblog.common.web.Result
import io.archx.txblog.web.bean.Account
import io.archx.txblog.web.bind.annotation.LoginAccount
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("qiniu")
@Api(tags = ["七牛云存储控制器"])
class QiniuController(val kodo: Kodo) {

    val logger = getLogger()

    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ApiOperation("EditorMD文件上传", notes = "适配Editor编辑器文件上传")
    @ApiImplicitParam("图片文件", name = "file", paramType = "form")
    fun upload(@LoginAccount account: Account, @RequestParam("file", required = false) file: MultipartFile?): Result<String> {
        return if (file == null || file.isEmpty) Result.fail(1, "file not found") else {
            return try {
                val url = kodo.upload(file.bytes)
                Result.ok(url)
            } catch (ex: RuntimeException) {
                logger.error("upload file error = '{}'", ex.message)
                Result.fail(1, "file upload fail")
            }
        }
    }
}
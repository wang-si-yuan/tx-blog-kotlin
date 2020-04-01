package io.archx.txblog.web.bean

import io.archx.txblog.data.domain.Draft
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.util.DigestUtils
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class MarkdownZip(val draft: Draft, val out: ZipOutputStream) {

    private val regex = Regex("""!\[.*?\]\((.*?)\)""")

    fun zip() {
        // 解析下载图片
        val imageMap = HashMap<String, ImageData?>()
        val text = draft.content

        for (s in text.split("\n")) {
            for (result in regex.findAll(s)) {
                val values = result.groupValues
                imageMap[values[0]] = executeOKHttp3download(values[1])
            }
        }


        // 写入图片
        out.putNextEntry(ZipEntry("resources/"))
        out.closeEntry()

        imageMap.forEach { (t, u) ->
            if (u != null) {
                val name = DigestUtils.md5DigestAsHex(t.toByteArray())
                out.putNextEntry(ZipEntry("resources/" + u.named(name)))
                out.write(u.data)
                out.closeEntry()
            }
        }

        // 替换图片
        val content = text.replace(regex) {
            val key = it.groupValues[0]
            val d = imageMap[key]
            val pic = if (d != null) ("resources/" + d.named(DigestUtils.md5DigestAsHex(key.toByteArray()))) else key
            "![]($pic)"
        }

        // 生成Markdown文本
        val builder = StringBuilder()
        builder.append("```text\n")
        builder.append("meta-info:\n")
        builder.append("category=").append(draft.category.category).append("\n")
        val tags = draft.tags.map { "#" + it.name }.toTypedArray()
        builder.append("tags=").append(tags.contentToString()).append("\n")
        builder.append("```\n")
        builder.append("\n")
        // 摘要
        builder.append("```text\n")
        builder.append("brief:\n")
        builder.append(draft.brief).append("\n")
        builder.append("```\n")
        builder.append("\n")

        // 内容
        builder.append(content)

        // 写入文章
        out.putNextEntry(ZipEntry(draft.title + ".md"))
        out.write(builder.toString().toByteArray())
        out.closeEntry()

        out.finish()
    }

    fun close() {
        out.close()
    }

    fun flush() {
        out.flush()
    }

    private fun executeOKHttp3download(url: String): ImageData? {
        val http = OkHttpClient.Builder().build()
        val request = Request.Builder().get().url(url).build()
        val response = http.newCall(request).execute()

        response.use { it ->
            val body = it.body() ?: return null

            // 类型
            val type = body.contentType().toString()
            val ins = body.byteStream().readBytes()
            return ImageData(type, ins)
        }
    }
}
package io.archx.txblog.web.bean

import java.io.InputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class MarkdownUnZip(ins: InputStream) {

    private val map = HashMap<String, ByteArray>()
    private val mip = HashMap<String, String>()
    private val regex = Regex("""!\[.*?\]\((.*?)\)""")
    private val split = Regex("[,|#| ]")

    private var markdown: String? = null

    init {

        val zis = ZipInputStream(ins)

        var entry: ZipEntry? = zis.nextEntry
        while (entry != null) {
            val name = entry.name
            if (entry.isDirectory || entry.name.startsWith("__MACOSX")) {
                entry = zis.nextEntry
                continue
            }
            map[name] = zis.readBytes()
            entry = zis.nextEntry
        }
        zis.close()

        initMarkdown()
    }

    /**
     * 直接获取Markdown内容
     */
    fun markdown(): String? {
        this.markdown = markdown?.replace(regex) { mr ->
            val key = mr.groupValues[1]
            val pic = image(key) ?: key
            "![]($pic)"
        }
        return this.markdown
    }

    /**
     * 替换掉图片
     */
    fun replace(mapper: (Map.Entry<String, ByteArray>) -> Map.Entry<String, String>) {
        map.filter { it.key.endsWith(".jpg") || it.key.endsWith(".jpeg") || it.key.endsWith(".png") }
            .entries.parallelStream().map(mapper).forEach { mip[it.key] = it.value }
    }

    private fun initMarkdown() {
        val it = map.filter { it.key.endsWith(".md") }.entries.iterator()
        if (it.hasNext()) {
            this.markdown = String(it.next().value)
        }
    }

    private fun image(key: String): String? {
        val values = key.split(split)
        return mip[values[0]]
    }
}
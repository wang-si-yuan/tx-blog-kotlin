package io.archx.txblog

import io.archx.txblog.web.bean.MarkdownUnZip
import java.io.ByteArrayInputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

object ZipFileTests {

    @JvmStatic
    fun main(args: Array<String>) {
        val text = "resources/04C6AAE3C65C49AB9B6F8AD38EAE06B4.jpg =1040x399"
        println(text.split(Regex("[,|#| ]")).size)

        test1()
    }

    fun test1() {
        val ins = ByteArrayInputStream(Files.readAllBytes(Paths.get("/Users/archx/archive.zip")))
        val md = MarkdownUnZip(ins)
//        val text = md.replace { key, data ->
//            println(key + "," + data.size)
//            key
//        }
//        println(text)
    }

    fun test() {
        val ins = ByteArrayInputStream(Files.readAllBytes(Paths.get("/Users/archx/archive.zip")))
        val map = HashMap<String, ByteArray>()
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

        map.forEach { (k, v) ->
            println("$k -> ${v.size}")
        }
    }
}
package io.archx.txblog

import io.archx.txblog.common.qiniu.Kodo
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.nio.file.Files
import java.nio.file.Paths


@RunWith(SpringRunner::class)
@SpringBootTest
class QiniuKodoTests {

    @Autowired
    lateinit var kodo: Kodo

    @Test
    fun testUpload() {
        val sourceData: ByteArray = getFile("/Users/archx/Downloads/IMG_7FEC83E8E011-1.jpeg")
        println("upload -> " + kodo.upload(sourceData))
    }

    fun getFile(filename: String): ByteArray {
        return Files.readAllBytes(Paths.get(filename))
    }
}
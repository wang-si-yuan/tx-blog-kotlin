package io.archx.txblog.common.qiniu

import com.google.gson.Gson
import com.qiniu.common.QiniuException
import com.qiniu.storage.model.DefaultPutRet
import com.tinify.Tinify
import io.archx.txblog.common.getLogger
import io.archx.txblog.configuration.bean.QiniuOption
import java.io.ByteArrayInputStream

/**
 * 七牛云存储
 */
class Kodo(private val option: QiniuOption) {

    private val logger = getLogger()
    private val auth = option.createAuth()
    private val uploadManager = option.getUploadManager()

    init {
        // Tiny PNG
        Tinify.setAppIdentifier("tx-blog")
        Tinify.setKey(option.tinyApiKey)
    }

    fun upload(bytes: ByteArray): String {
        var data: ByteArray = bytes
        if (option.tinify) {
            data = Tinify.fromBuffer(bytes).toBuffer()
        }
        val stream = ByteArrayInputStream(data)
        try {
            val response = uploadManager.put(stream, null, auth.uploadToken(option.bucket), null, null)
            val putRet: DefaultPutRet = Gson().fromJson(response.bodyString(), DefaultPutRet::class.java)
            logger.info("upload success [ key = ${putRet.key}, hash = ${putRet.hash}] ")
            return option.cdn + putRet.key
        } catch (ex: QiniuException) {
            logger.error("图片上传失败, err = '{}'", ex.message)
            throw ex
        }
    }
}
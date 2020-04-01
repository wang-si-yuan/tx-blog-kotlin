package io.archx.txblog.configuration.bean

import com.qiniu.storage.Region
import com.qiniu.storage.UploadManager
import com.qiniu.util.Auth

/**
 * region 0:华东, 1:华北, 2:华南, 3:北美, 4: 东南亚
 */
data class QiniuOption(
    var accessKey: String = "",
    var secretKey: String = "",
    var bucket: String = "",
    var region: Int = 0,
    var cdn: String = "http://q7vu0v5rm.bkt.clouddn.com/",
    var tinyApiKey: String = "",
    var tinify: Boolean = false) {

    fun createAuth(): Auth {
        return Auth.create(accessKey, secretKey)
    }

    fun getUploadManager(): UploadManager {
        val rg = when (region) {
            0 -> Region.region0()
            1 -> Region.region1()
            2 -> Region.region2()
            3 -> Region.beimei()
            4 -> Region.xinjiapo()
            else -> null
        }
        val cfg = com.qiniu.storage.Configuration(rg)
        return UploadManager(cfg)
    }
}
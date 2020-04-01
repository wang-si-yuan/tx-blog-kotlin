package io.archx.txblog.web.bean

class ImageData(var type: String, var data: ByteArray) {

    fun named(name: String): String {
        return name + "." + type.split("/")[1]
    }
}
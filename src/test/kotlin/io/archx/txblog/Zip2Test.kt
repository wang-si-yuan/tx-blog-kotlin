package io.archx.txblog

import io.archx.txblog.web.bean.ImageData
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.util.DigestUtils
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object Zip2Test {

    @JvmStatic
    fun main(args: Array<String>) {
        val text = "## 项目概要\n" +
            "\n" +
            "> 定位服务旨在通过CB以及Beacon结合室内地图坐标数据提供定位功能，定位目标载体可以是手机、TAG或者其他主动扫描和被动广播蓝牙信号的设备。\n" +
            "\n" +
            "定位服务提供主动和被动两种模式的定位功能实现； 其中主动定位是指定位目标载体通过扫描周边Beacon设备上报至定位服务（Location Server)， 定位服务通过Beacon信号数据计算出定位结果； 被动定位是指场景内的CB或其他类似智能网关接收定位目标载体广播的iBeacon信号， 通过CB点位数据和信号数据计算出定位结果。\n" +
            "\n" +
            "## 服务架构\n" +
            "\n" +
            "> 定位服务将采用`Golang`实现，Go is an open source programming language that makes it easy to build simple, reliable, and efficient software.\n" +
            "\n" +
            "**定位实现**\n" +
            "\n" +
            "通过将广播的蓝牙信号强度`RSSI`转换为距离（米）再集合CB或Beacon的点位数据通过`三角定位`算法计算定位，有效精度最高可达`3 - 5` 米。\n" +
            "\n" +
            "![](http://q7vu0v5rm.bkt.clouddn.com/Flef01z78_ayZZZZ7RxrMeXeXunu)\n" +
            "\n" +
            "**三角定位算法示意图**\n" +
            "\n" +
            "> 设位置节点 D（x,y），已知 A、B、C 三点的坐标为（x1,y1），（x2,y2），（x3,y3）。它们到 D 的距离分别是 d1、d2、d3. 则 D 的位置可以通过下列方程中的任意两个进行求解。\n" +
            "\n" +
            "![](http://q7vu0v5rm.bkt.clouddn.com/FklN5_DYRPcTzwwEbaw3qOPsDf8C)\n" +
            "\n" +
            "## 程序实现\n" +
            "\n" +
            "\n" +
            "## HTTP JSON_RPC 接口设计\n" +
            "\n" +
            "```javascript\n" +
            "# 扫描开关\n" +
            "\n" +
            "{\n" +
            "\t\"version\": \"1.0\"\n" +
            "\t\"method\": \"mth_scan\",\n" +
            "\t\"params\": [\"CB_ID\", 1]\n" +
            "}\n" +
            "\n" +
            "# 蓝牙设置\n" +
            "{\n" +
            "\t\"version\": \"1.0\"\n" +
            "\t\"method\": \"mth_bluetooth\",\n" +
            "\t\"params\": [\"CB_ID\", {\n" +
            "\t\t\"uuid\": \"E2C56DB5DFFB48D2B060D0F5A71096E0\",\n" +
            "\t\t\"major\": 10187,\n" +
            "\t\t\"minor\": 10001,\n" +
            "\t\t\"rssi\": 1,\n" +
            "\t\t\"dbm\": -4\n" +
            "\t\t\"interval\": 852\n" +
            "\t}]\n" +
            "}\n" +
            "\n" +
            "# 上报设置\n" +
            "\n" +
            "{\n" +
            "\t\"version\": \"1.0\"\n" +
            "\t\"method\": \"mth_report\",\n" +
            "\t\"params\": [\"CB_ID\",{\n" +
            "\t\t\"interval\": 10,\n" +
            "\t\t\"http\": \"http://t.cn/httpd\",\n" +
            "\t\t\"tcp\": \"127.0.0.1:55837\",\n" +
            "\t\t\"mqtt\": \"127.0.0.1:1883/MQTT2\"\n" +
            "\t}]\n" +
            "}\n" +
            "\n" +
            "# 重启\n" +
            "\n" +
            "{\n" +
            "\t\"version\": \"1.0\"\n" +
            "\t\"method\": \"mth_reboot\",\n" +
            "\t\"params\": [\"CB_ID\"]\n" +
            "}\n" +
            "\n" +
            "# 升级\n" +
            "\n" +
            "{\n" +
            "\t\"version\": \"1.0\"\n" +
            "\t\"method\": \"mth_upgrage\",\n" +
            "\t\"params\": [\"CB_ID\", \"firmware\",\"http://t.cn/1qZw24B8o\"]\n" +
            "}\n" +
            "```"

        val out = ZipOutputStream(FileOutputStream("/tmp/txblog/test.zip"))

        // 解析下载图片
        val regex = Regex("""!\[.*?\]\((.*?)\)""")
        val imageMap = HashMap<String, ImageData?>()

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
        // 写入文章
        out.putNextEntry(ZipEntry("test.md"))
        out.write(content.toByteArray())
        out.closeEntry()

        out.close()
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
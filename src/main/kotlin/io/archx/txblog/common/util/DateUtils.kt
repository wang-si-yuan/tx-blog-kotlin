package io.archx.txblog.common.util

object DateUtils {

    fun timestamp(): Long {
        return java.util.Calendar.getInstance().timeInMillis
    }
}
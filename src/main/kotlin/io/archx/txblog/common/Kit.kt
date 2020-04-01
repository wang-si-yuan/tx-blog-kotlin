package io.archx.txblog.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory

// 日志
inline fun <reified T> T.getLogger(): Logger {
    if (T::class.isCompanion) {
        return LoggerFactory.getLogger(T::class.java.enclosingClass)
    }
    return LoggerFactory.getLogger(T::class.java)
}
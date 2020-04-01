package io.archx.txblog.web.vo

import kotlin.streams.toList

data class PageVo<T>(
    val total: Int = 0,
    val current: Int = 1,
    val size: Int = 15,
    var records: List<T>,
    var attributes: HashMap<String, Any> = HashMap()) {

    fun <R> convert(mapper: (T) -> R): PageVo<R> {
        val r = records.stream().map(mapper).toList()
        return PageVo<R>(total, current, size, r, attributes)
    }

    fun attribute(name: String, value: Any): PageVo<T> {
        attributes[name] = value
        return this
    }
}
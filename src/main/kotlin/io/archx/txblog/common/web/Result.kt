package io.archx.txblog.common.web

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import io.archx.txblog.common.def.MessageCode
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
class Result<T>(
    @JsonProperty("err_code")
    val errCode: Int = 0,
    @JsonProperty("err_msg")
    val errMsg: String = "ok",
    val data: T?,
    val timestamp: Long) {

    constructor(code: Int, msg: String) : this(
        errCode = code,
        errMsg = msg,
        data = null,
        timestamp = timestamp()
    )

    companion object {

        fun <T> ok(): Result<T> {
            return Result(0, "ok")
        }

        fun <T> ok(data: T): Result<T> {
            return Result(0, "ok", data, timestamp())
        }

        fun <T> fail(msg: String): Result<T> {
            return Result(-1, msg, null, timestamp())
        }

        fun <T> fail(code: Int, msg: String): Result<T> {
            return Result(code, msg, null, timestamp())
        }

        fun <T> code(mc: MessageCode): Result<T> {
            return Result(mc.code, mc.message)
        }

        private fun timestamp(): Long {
            return Calendar.getInstance().timeInMillis
        }
    }
}
package io.archx.txblog.common.exception

import io.archx.txblog.common.def.MessageCode

open class MessageCodeException(open val code: Int, override val message: String) : RuntimeException(message) {

    constructor(mc: MessageCode) : this(mc.code, mc.message)
}
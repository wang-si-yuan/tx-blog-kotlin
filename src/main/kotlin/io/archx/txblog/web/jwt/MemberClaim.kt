package io.archx.txblog.web.jwt

import java.util.*

data class MemberClaim(

    /**
     * 用于说明该JWT发送给的用户
     */
    var subject: String = "member",

    /**
     * 用于说明该JWT面向的对象(标记来源)
     */
    var audience: String = "pc",

    /**
     * 用户编号
     */
    var userId: Int,

    /**
     * 用户名称
     */
    var username: String,

    /**
     * 生效时间
     */
    var issuedAt: Date,

    /**
     * 过期时间
     */
    var expiresAt: Date
) {
    fun isExpired(): Boolean {
        return Calendar.getInstance().timeInMillis > expiresAt.time
    }
}
package io.archx.txblog.web.bean

import io.archx.txblog.web.jwt.MemberClaim

data class Account(val id: Int, val username: String) {
    companion object {
        fun of(memberClaim: MemberClaim?): Account? {
            if (memberClaim != null && !memberClaim.isExpired()) {
                return Account(memberClaim.userId, memberClaim.username)
            }
            return null
        }
    }
}
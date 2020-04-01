package io.archx.txblog.web.vo

import io.archx.txblog.data.domain.User

data class UserVo(val id: Int, val username: String, val state: Int) {
    companion object {
        fun of(u: User): UserVo {
            return UserVo(u.id, u.username, u.state)
        }
    }
}
package io.archx.txblog.data.service

import io.archx.txblog.common.getLogger
import io.archx.txblog.web.exception.InvalidCertificateException
import io.archx.txblog.web.jwt.HttpJwtToken
import io.archx.txblog.web.jwt.MemberClaim
import org.springframework.stereotype.Service
import org.springframework.util.DigestUtils
import java.util.*

interface LoginService {

    fun login(username: String, password: String): String?
}

@Service
class LoginServiceImpl(val us: UserService) : LoginService {

    val logger = getLogger()

    override fun login(username: String, password: String): String? {
        return us.findByUsername(username)?.let {
            val encodedPwd = DigestUtils.md5DigestAsHex((password + "\n" + it.salt).byteInputStream())
            if (encodedPwd == it.password) {
                logger.info("user [ username = {} ] logged in", username)
                val now = Date()
                return HttpJwtToken.create(MemberClaim(userId = it.id, username = username, issuedAt = now, expiresAt = HttpJwtToken.getExpiresDate(now)))
            } else throw InvalidCertificateException()
        }
    }
}
package io.archx.txblog.web.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.archx.txblog.common.getLogger
import java.util.*

object HttpJwtToken {

    private const val DEFAULT_EXPIRES = 15 // 默认过期时间：15天
    private const val issuer = "tx_blog"
    private val algorithm = Algorithm.HMAC256("324yz5IdGWF0bFtZ5d0")
    private val header: MutableMap<String, Any> = HashMap()

    private val logger = getLogger()

    init {
        header["alg"] = "HS256"
        header["typ"] = "JWT"
    }

    fun create(claim: MemberClaim): String? {
        return try {
            JWT.create()
                .withHeader(header)
                .withClaim("userId", claim.userId)
                .withClaim("username", claim.username)
                .withIssuer(issuer)
                .withSubject(claim.subject)
                .withAudience(claim.audience)
                .withIssuedAt(claim.issuedAt) // 生成签名的时间
                .withExpiresAt(claim.expiresAt) // 签名过期的时间
                .sign(algorithm)
        } catch (ex: RuntimeException) {
            logger.error("create token error", ex)
            null
        }
    }

    fun verify(token: String?): MemberClaim? {
        return try {
            val verifier = JWT.require(algorithm).withIssuer(issuer).build()
            val jwt = verifier.verify(token)
            val claims = jwt.claims
            MemberClaim(
                userId = claims["userId"]!!.asInt(),
                username = claims["username"]!!.asString(),
                audience = jwt.audience[0],
                subject = jwt.subject,
                issuedAt = jwt.issuedAt,
                expiresAt = jwt.expiresAt
            )
        } catch (ex: RuntimeException) {
            this.logger.error("verify token error", ex)
            null
        }
    }

    fun getExpiresDate(date: Date, expireDays: Int = DEFAULT_EXPIRES): Date {
        val instance = Calendar.getInstance()
        instance.time = date
        instance.add(Calendar.DATE, expireDays)
        return instance.time
    }
}
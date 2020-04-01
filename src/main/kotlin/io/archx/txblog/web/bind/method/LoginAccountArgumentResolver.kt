package io.archx.txblog.web.bind.method

import io.archx.txblog.common.getLogger
import io.archx.txblog.web.bean.Account
import io.archx.txblog.web.bind.annotation.LoginAccount
import io.archx.txblog.web.exception.UnknownAccountException
import io.archx.txblog.web.jwt.HttpJwtToken
import org.springframework.core.MethodParameter
import org.springframework.util.StringUtils
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class LoginAccountArgumentResolver : HandlerMethodArgumentResolver {

    companion object {
        val logger = getLogger()
    }

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginAccount::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?): Any? {
        parameter.getParameterAnnotation(LoginAccount::class.java)?.let {
            val token = webRequest.getHeader(it.value)
            if (StringUtils.hasText(token)) {
                try {
                    val u = Account.of(HttpJwtToken.verify(token!!))
                    if (u != null) {
                        return u
                    }
                } catch (ex: RuntimeException) {
                    logger.error("resolve account error", ex)
                }
            }

        }
        throw UnknownAccountException()
    }
}
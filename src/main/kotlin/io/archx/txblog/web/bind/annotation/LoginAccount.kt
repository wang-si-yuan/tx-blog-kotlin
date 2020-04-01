package io.archx.txblog.web.bind.annotation

import io.archx.txblog.web.Constants

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class LoginAccount(val value: String = Constants.HEADER_JWT_TOKEN) {
}
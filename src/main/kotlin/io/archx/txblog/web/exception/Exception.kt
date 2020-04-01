package io.archx.txblog.web.exception

import io.archx.txblog.common.def.CodeDef
import io.archx.txblog.common.def.MessageCode
import io.archx.txblog.common.exception.MessageCodeException

open class AccountException(mc: MessageCode) : MessageCodeException(mc)
class UnknownAccountException : AccountException(CodeDef.UNKNOWN_ACCOUNT)
class InvalidCertificateException : AccountException(CodeDef.INVALID_CERTIFICATE)
class UnauthorizedException : AccountException(CodeDef.UNAUTHORIZED)
package io.archx.txblog.web.handler

import io.archx.txblog.common.def.CodeDef
import io.archx.txblog.common.exception.MessageCodeException
import io.archx.txblog.common.getLogger
import io.archx.txblog.common.web.Result
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import javax.servlet.http.HttpServletRequest

/**
 * 默认异常处理器
 * <p/>
 * 方便APP或三方调用，服务器Http状态码始终为200
 */
@ControllerAdvice(annotations = [RestController::class])
class DefaultExceptionHandler {
    val logger = getLogger()

    @ExceptionHandler(MessageCodeException::class)
    fun messageCodeExceptionHandle(nw: NativeWebRequest, ex: MessageCodeException): ResponseEntity<Result<Any>> {
        printLogging(nw, ex)
        return ResponseEntity(Result.fail(ex.code, ex.message), HttpStatus.OK)
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun missingServletRequestParameterExceptionHandle(nw: NativeWebRequest, ex: MissingServletRequestParameterException): ResponseEntity<Result<Any>> {
        printLogging(nw, ex)
        return ResponseEntity(Result.code(CodeDef.MISSING_PARAMETER), HttpStatus.OK)
    }

    @ExceptionHandler(Exception::class)
    fun uncheckedExceptionHandle(nw: NativeWebRequest, ex: Exception): ResponseEntity<Result<Any>> {
        printLogging(nw, ex)
        return ResponseEntity(Result.fail(500, ex.message ?: ex.javaClass.name), HttpStatus.OK)
    }

    private fun <EX : java.lang.Exception> printLogging(nw: NativeWebRequest, ex: EX) {
        val req = nw.getNativeRequest(HttpServletRequest::class.java)
        req?.let {
            logger.error("except -> [ uri = '{}', message = '{}' ]", req.requestURI, ex.message)
            logger.error("exception:", ex)
        }
    }
}
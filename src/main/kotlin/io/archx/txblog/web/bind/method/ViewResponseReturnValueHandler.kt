package io.archx.txblog.web.bind.method

import io.archx.txblog.common.getLogger
import io.archx.txblog.data.service.CategoryService
import io.archx.txblog.data.service.TagService
import io.archx.txblog.web.bind.annotation.ViewResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

class ViewResponseReturnValueHandler : HandlerMethodReturnValueHandler {

    val logger = getLogger()

    @Autowired
    lateinit var categoryService: CategoryService
    @Autowired
    lateinit var tagService: TagService

    override fun supportsReturnType(returnType: MethodParameter): Boolean {
        return returnType.hasMethodAnnotation(ViewResponse::class.java)
    }

    override fun handleReturnValue(returnValue: Any?,
        returnType: MethodParameter,
        mavContainer: ModelAndViewContainer,
        webRequest: NativeWebRequest) {

        webRequest.getNativeRequest(HttpServletRequest::class.java)?.let {
            logger.info("Handle return value -> [ uri = '{}', query = '{}' ]", it.requestURI, it.queryString)
            // findAll 返回带有统计文章数量
            val categories = categoryService.findAll()
            val tags = tagService.list()

            mavContainer.addAttribute("categories", categories)
            mavContainer.addAttribute("tags", tags)
        }

    }
}
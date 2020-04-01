package io.archx.txblog.web.bind.method

import io.archx.txblog.common.getLogger
import io.archx.txblog.data.service.CategoryService
import io.archx.txblog.data.service.NavigationService
import io.archx.txblog.data.service.TagService
import io.archx.txblog.web.bind.annotation.ViewResponse
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model

@Aspect
class ViewResponseAdvice {

    val logger = getLogger()

    @Autowired
    lateinit var categoryService: CategoryService
    @Autowired
    lateinit var tagService: TagService
    @Autowired
    lateinit var navService: NavigationService

    @Pointcut("@annotation(io.archx.txblog.web.bind.annotation.ViewResponse)")
    fun pointcut() {
    }

    @Around(value = "pointcut() && @annotation(vr)", argNames = "pjp,vr")
    fun after(pjp: ProceedingJoinPoint, vr: ViewResponse): Any? {
        val result = pjp.proceed()

        val ms = pjp.signature as MethodSignature
        for (arg in pjp.args) {
            if (arg is Model) {
                logger.info("Handle return value -> [ method = '{}', params = '{}' ]", ms.method.name, ms.parameterNames)
                // findAll 返回带有统计文章数量
                val categories = categoryService.findAll()
                val tags = tagService.list()
                val navs = navService.findAll()

                arg.addAttribute("categories", categories)
                arg.addAttribute("tags", tags)
                arg.addAttribute("navs", navs)
            }
        }

        return result
    }

}
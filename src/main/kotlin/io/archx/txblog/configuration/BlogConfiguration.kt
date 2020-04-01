package io.archx.txblog.configuration

import io.archx.txblog.web.bind.method.LoginAccountArgumentResolver
import io.archx.txblog.web.bind.method.ViewResponseAdvice
import io.archx.txblog.web.bind.method.ViewResponseReturnValueHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.HandlerMethodReturnValueHandler
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
class BlogConfiguration : WebMvcConfigurer {

    @Bean
    fun loginAccountArgumentResolver() = LoginAccountArgumentResolver()

    @Bean
    fun viewResponseReturnValueHandler() = ViewResponseReturnValueHandler()

    @Bean
    fun viewResponseAdvice() = ViewResponseAdvice()

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(loginAccountArgumentResolver())
    }

    override fun addReturnValueHandlers(handlers: MutableList<HandlerMethodReturnValueHandler>) {
        super.addReturnValueHandlers(handlers)
        // not works
        // see viewResponseAdvice
        handlers.add(viewResponseReturnValueHandler())
    }


    /*
    @Bean // 临时文件配置,未使用
    fun multipartConfigElement(): MultipartConfigElement {
        val factory = MultipartConfigFactory()
        factory.setLocation("/tmp/txblog")
        return factory.createMultipartConfig()
    }
     */
}
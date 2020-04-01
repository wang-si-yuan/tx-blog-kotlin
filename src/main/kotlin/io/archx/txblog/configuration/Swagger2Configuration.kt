package io.archx.txblog.configuration

import io.archx.txblog.web.bind.annotation.LoginAccount
import io.swagger.annotations.Api
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.ParameterBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.schema.ModelRef
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class Swagger2Configuration {

    @Bean
    fun createRestApi(): Docket? {
        val select = Docket(DocumentationType.SWAGGER_2).groupName("系统服务").apiInfo(buildApiInfo())
            .select()
        return select.apis(RequestHandlerSelectors.basePackage("io.archx.txblog.web.controller"))
            .apis(RequestHandlerSelectors.withClassAnnotation(Api::class.java))
            .paths(PathSelectors.any()).build()
            .securitySchemes(listOf(ApiKey("授权访问令牌", "Authorization-Token", "header")))
            .securityContexts(securityContexts())
            .ignoredParameterTypes(LoginAccount::class.java)
            .globalOperationParameters(parameters())
    }

    private fun buildApiInfo(): ApiInfo? {
        return ApiInfoBuilder().title("应用接口").description("HTTP RESTful APIs").version("1.0").build()
    }


    private fun securityContexts(): List<SecurityContext?>? {
        return listOf(
            SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any()) //.forPaths(Predicates.or(PathSelectors.ant("/users/**"),PathSelectors.ant("/authorized/**")))
                .build())
    }

    private fun defaultAuth(): List<SecurityReference?>? {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return listOf(
            SecurityReference("Authorization-Token", authorizationScopes))
    }

    private fun parameters(): List<Parameter> {
        return listOf(ParameterBuilder().name("Authorization-Token")
            .description("请求头：JWT Token")
            .modelRef(ModelRef("string"))
            .parameterType("header")
            .required(false)
            .build())
    }
}
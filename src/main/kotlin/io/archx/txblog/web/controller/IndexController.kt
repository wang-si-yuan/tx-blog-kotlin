package io.archx.txblog.web.controller

import io.archx.txblog.common.web.Result
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class IndexController {

    @RequestMapping(value = ["/", "health"], headers = ["content-type=application/json*"])
    fun index() = Result.ok<Any>()
}
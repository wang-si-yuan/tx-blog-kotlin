package io.archx.txblog.web.controller

import io.archx.txblog.common.def.CodeDef
import io.archx.txblog.common.web.Result
import io.archx.txblog.data.service.LoginService
import io.archx.txblog.web.bean.Account
import io.archx.txblog.web.bind.annotation.LoginAccount
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(tags = ["登录控制器"])
class LoginController(val ls: LoginService) {

    @PostMapping("/login")
    @ApiOperation("登录", notes = "输入用户名密码进行登录")
    @ApiImplicitParams(
        ApiImplicitParam("用户名", name = "username"),
        ApiImplicitParam("用户密码", name = "password")
    )
    fun login(@RequestParam("username") username: String, @RequestParam("password") password: String): Result<String> {
        val token = ls.login(username, password)

        return if (StringUtils.hasText(token)) Result.ok(token!!) else Result.code(CodeDef.UNKNOWN_ACCOUNT)
    }

    @GetMapping("/login")
    @ApiOperation("获取登录账户信息", notes = "获取登录账户信息")
    fun login(@LoginAccount account: Account) = Result.ok(account)
}
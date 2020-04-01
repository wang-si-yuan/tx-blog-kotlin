package io.archx.txblog.web.controller

import io.archx.txblog.common.def.CodeDef
import io.archx.txblog.common.web.Result
import io.archx.txblog.data.service.UserService
import io.archx.txblog.web.bean.Account
import io.archx.txblog.web.bind.annotation.LoginAccount
import io.archx.txblog.web.vo.UserVo
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
@Api(tags = ["用户控制器"])
class UserController(val us: UserService) {

    @GetMapping
    @ApiOperation("用户列表", notes = "返回系统中存在的用户列表")
    fun index() = Result.ok(us.list().map { UserVo.of(it) })

    @GetMapping("/init")
    @ApiOperation("初始化管理员账户", notes = "初始化管理员账户，仅当未被初始化时执行", response = UserVo::class)
    fun init(): Result<UserVo> {
        val user = us.init()
        return if (user != null) Result.ok(UserVo.of(user)) else Result.code(CodeDef.FAILED_TO_SAVE_DATA)
    }

    @PutMapping("/changepwd")
    @ApiOperation("修改登录密码", notes = "修改登录账户的密码")
    @ApiImplicitParams(
        ApiImplicitParam("原始密码", name = "checkpwd"),
        ApiImplicitParam("新密码", name = "password")
    )
    fun changepwd(
        @LoginAccount account: Account,
        @RequestParam("checkpwd") checkpwd: String,
        @RequestParam("password") password: String): Result<Any> {
        us.changepwd(account.id, checkpwd, password)
        return Result.ok()
    }
}
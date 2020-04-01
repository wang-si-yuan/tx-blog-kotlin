package io.archx.txblog.web.controller

import io.archx.txblog.common.def.CodeDef
import io.archx.txblog.common.web.Result
import io.archx.txblog.data.domain.Tag
import io.archx.txblog.data.service.TagService
import io.archx.txblog.web.bean.Account
import io.archx.txblog.web.bind.annotation.LoginAccount
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tags")
@Api(tags = ["标签控制器"])
class TagController(val ts: TagService) {

    @GetMapping
    @ApiOperation("标签列表", notes = "可用标签列表")
    fun tags() = Result.ok<List<Tag>>(ts.list().filter { it.state == 0 })

    @PostMapping
    @ApiOperation("创建标签", notes = "创建标签并返回")
    @ApiImplicitParam(value = "标签名称", name = "name", required = true)
    fun tags(@LoginAccount account: Account, @RequestParam("name") name: String): Result<Tag> {
        val tag = ts.create(name, account.id)

        return if (tag != null) Result.ok(tag) else {
            Result.code(CodeDef.FAILED_TO_SAVE_DATA)
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("更新标签名称", notes = "根据标签ID更新标签名称")
    @ApiImplicitParams(
        ApiImplicitParam(value = "标签标识", name = "id", required = true, dataType = "Int"),
        ApiImplicitParam(value = "标签名称", name = "name", required = true)
    )
    fun tags(@LoginAccount account: Account, @PathVariable("id") id: Int, @RequestParam("name") name: String)
        : Result<Tag> {
        val tag = ts.update(id, name, account.id)

        return if (tag != null) Result.ok(tag) else {
            Result.code(CodeDef.FAILED_TO_SAVE_DATA)
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("移除标签", notes = "根据ID移除标签")
    @ApiImplicitParam(value = "标签标识", name = "id", required = true, paramType = "path", dataType = "Int")
    fun tags(@LoginAccount account: Account, @PathVariable("id") id: Int): Result<Any> {
        ts.delete(id, account.id)
        return Result.ok()
    }
}
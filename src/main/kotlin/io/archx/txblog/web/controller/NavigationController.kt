package io.archx.txblog.web.controller

import io.archx.txblog.common.def.CodeDef
import io.archx.txblog.common.getLogger
import io.archx.txblog.common.web.Result
import io.archx.txblog.data.domain.Navigation
import io.archx.txblog.data.dto.NavigationDto
import io.archx.txblog.data.service.NavigationService
import io.archx.txblog.web.bean.Account
import io.archx.txblog.web.bind.annotation.LoginAccount
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/navigation")
@Api(tags = ["导航条控制器"])
class NavigationController(val ns: NavigationService) {

    val logger = getLogger()

    @GetMapping
    @ApiOperation("导航条列表", notes = "返回导航条条目", response = Navigation::class, responseContainer = "List")
    fun index() = Result.ok<List<Navigation>>(ns.list().filter { it.state == 0 })

    @PostMapping(consumes = ["application/json"])
    @ApiOperation("保存或更新导航", notes = "保存或更新导航条条目", response = Navigation::class)
    @ApiImplicitParam("消息体", name = "dto", dataTypeClass = NavigationDto::class, paramType = "body")
    fun post(@LoginAccount account: Account, @RequestBody dto: NavigationDto): Result<Navigation> {
        val nav = when (dto.id) {
            0 -> ns.create(dto)
            else -> ns.update(dto)
        }
        return if (nav != null) Result.ok(nav) else Result.code(CodeDef.FAILED_TO_SAVE_DATA)
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除导航条目", notes = "删除指定ID的导航条目")
    fun delete(@LoginAccount account: Account, @PathVariable("id") id: Int): Result<Any> {
        // TODO 限定只有管理员可删除
        logger.info("user {} delete nav id = {}", account, id)

        return if (ns.removeById(id)) Result.ok() else Result.code(CodeDef.RECORD_NOT_FOUND)
    }

}
package io.archx.txblog.web.controller

import io.archx.txblog.common.def.CodeDef
import io.archx.txblog.common.web.Result
import io.archx.txblog.data.domain.Category
import io.archx.txblog.data.service.CategoryService
import io.archx.txblog.web.bean.Account
import io.archx.txblog.web.bind.annotation.LoginAccount
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/categories")
@Api(tags = ["分类控制器"])
class CategoryController(val cs: CategoryService) {

    @GetMapping
    @ApiOperation("分类列表", notes = "返回可用的分类列表")
    fun index() = Result.ok<List<Category>>(cs.list().filter { it.state == 0 })

    @PostMapping
    @ApiOperation("创建分类", notes = "创建分类并返回")
    @ApiImplicitParam(value = "分类名称", name = "category", required = true)
    fun post(@LoginAccount account: Account, @RequestParam("category") category: String): Result<Category> {
        val cat = cs.create(category, account.id)

        return if (cat != null) Result.ok(cat) else {
            Result.code(CodeDef.FAILED_TO_SAVE_DATA)
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("更新分类名称", notes = "根据标签ID更新分类名称")
    @ApiImplicitParams(
        ApiImplicitParam(value = "分类标识", name = "id", required = true, dataType = "Int"),
        ApiImplicitParam(value = "分类名称", name = "category", required = true)
    )
    fun update(@LoginAccount account: Account, @PathVariable("id") id: Int, @RequestParam("category") category: String)
        : Result<Category> {
        val cat = cs.update(id, category, account.id)

        return if (cat != null) Result.ok(cat) else {
            Result.code(CodeDef.FAILED_TO_SAVE_DATA)
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("移除分类", notes = "根据ID移除分类")
    @ApiImplicitParam(value = "分类标识", name = "id", required = true, dataType = "Int", paramType = "path")
    fun delete(@LoginAccount account: Account, @PathVariable("id") id: Int): Result<Any> {
        cs.delete(id, account.id)
        return Result.ok()
    }
}
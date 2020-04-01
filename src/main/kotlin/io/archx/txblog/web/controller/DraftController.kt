package io.archx.txblog.web.controller

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import io.archx.txblog.common.def.CodeDef
import io.archx.txblog.common.util.DateUtils
import io.archx.txblog.common.web.Result
import io.archx.txblog.data.domain.Draft
import io.archx.txblog.data.service.DraftService
import io.archx.txblog.web.bean.Account
import io.archx.txblog.web.bind.annotation.LoginAccount
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/drafts")
@Api(tags = ["草稿控制器"])
class DraftController(val ds: DraftService) {

    @GetMapping("/{id}")
    @ApiOperation("获取文章草稿", notes = "查找文章草稿并返回")
    @ApiImplicitParam("草稿ID", name = "id", required = true, paramType = "path")
    fun index(@LoginAccount account: Account, @PathVariable("id") id: Long): Result<Draft> {
        val draft = ds.findById(id)
        return if (draft != null) Result.ok(draft) else Result.code(CodeDef.RECORD_NOT_FOUND)
    }


    @GetMapping("/posts/{id}")
    @ApiOperation("获取文章草稿", notes = "查找文章草稿并返回")
    @ApiImplicitParam("文章ID", name = "id", required = true, paramType = "path")
    fun post(@LoginAccount account: Account, @PathVariable("id") id: Int): Result<Draft> {
        val draft = ds.findByPostId(id)
        return if (draft != null) Result.ok(draft) else Result.code(CodeDef.RECORD_NOT_FOUND)
    }

    @PostMapping
    @ApiOperation("保存文章草稿", notes = "保存文章草稿并返回")
    @ApiImplicitParam("文章草稿", name = "draft", required = true, paramType = "body")
    fun post(@LoginAccount account: Account, @RequestBody draft: Draft): Result<Draft> {
        val copy = draft.copy(id = 0, createdAt = DateUtils.timestamp(), updatedAt = 0)
        if (ds.saveAsNew(copy)) {
            return Result.ok(copy)
        }
        return Result.code(CodeDef.FAILED_TO_SAVE_DATA)
    }

    @PostMapping("/publish")
    @ApiOperation("发布文章草稿", notes = "保存文章草稿并发布")
    @ApiImplicitParam("文章草稿", name = "draft", required = true, paramType = "body")
    fun publish(@LoginAccount account: Account, @RequestBody draft: Draft): Result<Draft> {
        val copy = ds.publish(draft.copy(id = 0, createdAt = DateUtils.timestamp(), updatedAt = 0))
        if (copy != null) {
            return Result.ok(copy)
        }
        return Result.code(CodeDef.FAILED_TO_SAVE_DATA)
    }

    @ApiOperation("获取文章草稿", notes = "查找文章草稿并返回")
    @ApiImplicitParam("文章ID", name = "postId", required = true, paramType = "path")
    @GetMapping("/history/{postId}")
    fun history(@LoginAccount account: Account, @PathVariable("postId") postId: Int): Result<List<Draft>> {
        val query = KtQueryWrapper(Draft::class.java)
        query.eq(Draft::postId, postId).orderByDesc(Draft::createdAt)
        return Result.ok(ds.list(query))
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除文章草稿", notes = "查找文章草稿并删除")
    @ApiImplicitParam("草稿ID", name = "id", required = true, paramType = "path")
    fun delete(@LoginAccount account: Account, @PathVariable("id") id: Long): Result<Draft> {
        val draft = ds.remove(id)
        return if (draft != null) Result.ok(draft) else Result.code(CodeDef.RECORD_NOT_FOUND)
    }
}
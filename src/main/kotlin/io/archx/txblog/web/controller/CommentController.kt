package io.archx.txblog.web.controller

import io.archx.txblog.common.def.CodeDef
import io.archx.txblog.common.web.Result
import io.archx.txblog.data.domain.Comment
import io.archx.txblog.data.dto.CommentDto
import io.archx.txblog.data.service.CommentService
import io.archx.txblog.web.bean.Account
import io.archx.txblog.web.bind.annotation.LoginAccount
import io.archx.txblog.web.vo.PageVo
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
@Api(tags = ["评论控制器"])
class CommentController(val cs: CommentService) {

    @GetMapping
    @ApiOperation("分页搜索", notes = "评论分页列表", response = Comment::class, responseContainer = "Result")
    @ApiImplicitParams(
        ApiImplicitParam("当前页", name = "current", defaultValue = "1"),
        ApiImplicitParam("每页显示记录数", name = "size", defaultValue = "15"),
        ApiImplicitParam("搜索关键字", name = "keywords", required = false)
    )
    fun index(
        @RequestParam("current", defaultValue = "1") current: Int,
        @RequestParam("size", defaultValue = "15") size: Int,
        @RequestParam("keywords", required = false) keywords: String?): Result<PageVo<Comment>> {
        return Result.ok(cs.findAll(current, size, keywords))
    }

    @PostMapping("/post")
    @ApiOperation("创建评论", notes = "创建评论", response = Comment::class)
    @ApiImplicitParam("评论体", name = "dto", paramType = "body", dataTypeClass = CommentDto::class)
    fun post(@RequestBody dto: CommentDto): Result<Comment> {
        val comment = cs.create(dto)
        return if (comment != null) Result.ok(comment) else Result.code(CodeDef.FAILED_TO_SAVE_DATA)
    }


    @PutMapping("/{id}")
    @ApiOperation("通过文章评论", notes = "根据评论ID审核通过评论")
    @ApiImplicitParam("评论ID", name = "id", paramType = "path", dataType = "Int")
    fun put(@LoginAccount account: Account, @PathVariable("id") id: Int): Result<Any> {
        val passed = cs.pass(id)
        return if (passed) Result.ok() else Result.code(CodeDef.INVALID_OR_DELETED)
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除文章评论", notes = "根据评论ID删除评论")
    @ApiImplicitParam("评论ID", name = "id", paramType = "path", dataType = "Int")
    fun delete(@LoginAccount account: Account, @PathVariable("id") id: Int): Result<Any> {
        // TODO 仅管理员可删除
        cs.removeComment(id)
        return Result.ok()
    }
}
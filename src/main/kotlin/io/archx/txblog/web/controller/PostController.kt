package io.archx.txblog.web.controller

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import io.archx.txblog.common.def.CodeDef
import io.archx.txblog.common.exception.MessageCodeException
import io.archx.txblog.common.web.Result
import io.archx.txblog.data.domain.Draft
import io.archx.txblog.data.domain.Post
import io.archx.txblog.data.service.DraftService
import io.archx.txblog.data.service.PostService
import io.archx.txblog.web.vo.PageVo
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("posts")
@Api(tags = ["文章控制器"])
class PostController(val ps: PostService, val ds: DraftService) {

    @GetMapping
    @ApiOperation("分页搜索", notes = "文章分页列表", response = Post::class, responseContainer = "Result")
    @ApiImplicitParams(
        ApiImplicitParam("当前页", name = "current", defaultValue = "1"),
        ApiImplicitParam("每页显示记录数", name = "size", defaultValue = "15"),
        ApiImplicitParam("搜索关键字", name = "keywords", required = false)
    )
    fun index(
        @RequestParam("current", defaultValue = "1") current: Int,
        @RequestParam("size", defaultValue = "15") size: Int,
        @RequestParam("keywords", required = false) keywords: String?): Result<PageVo<Post>> {
        return Result.ok(ps.findAll(current, size, keywords))
    }

    @GetMapping("/category/{id}")
    @ApiOperation("分类查找", notes = "分类文章列表", response = Post::class, responseContainer = "PageVo")
    @ApiImplicitParams(
        ApiImplicitParam("当前页", name = "current", defaultValue = "1"),
        ApiImplicitParam("每页显示记录数", name = "size", defaultValue = "15"),
        ApiImplicitParam("分类ID", name = "id", required = true, paramType = "path")
    )
    fun category(
        @RequestParam("current", defaultValue = "1") current: Int,
        @RequestParam("size", defaultValue = "15") size: Int,
        @PathVariable("id") id: Int): Result<PageVo<Post>> {
        return Result.ok(ps.findAll(current, size, id))
    }

    @GetMapping("/{id}")
    @ApiOperation("指定文章", notes = "根据ID获取指定文章")
    @ApiImplicitParam("文章ID", name = "id", required = true, paramType = "path")
    fun get(@PathVariable("id") id: Int): Result<Post> {
        val post = ps.findById(id) ?: return Result.code(CodeDef.INVALID_OR_DELETED)
        return Result.ok(post)
    }

    @GetMapping("/s/{link}")
    @ApiOperation("指定文章", notes = "根据短链接获取指定文章")
    @ApiImplicitParam("短链接", name = "link", required = true, paramType = "path")
    fun get(@PathVariable("link") link: String): Result<Post> {
        val post = ps.findByShortLink(link) ?: return Result.code(CodeDef.INVALID_OR_DELETED)
        return Result.ok(post)
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除文章", notes = "根据ID删除指定文章")
    @ApiImplicitParam("文章ID", name = "id", required = true, paramType = "path")
    fun delete(@PathVariable("id") id: Int): Result<Any> {
        return if (ps.remove(id)) Result.ok() else Result.code(CodeDef.INVALID_OR_DELETED)
    }

    @GetMapping("/check", params = ["s"])
    @ApiOperation("检测短链接是否使用", notes = "检测短链接是否被使用")
    @ApiImplicitParams(
        ApiImplicitParam("文章ID", name = "id", required = true, paramType = "query"),
        ApiImplicitParam("短链接", name = "s", required = true, paramType = "query")
    )
    fun check(@RequestParam("id") id: Int, @RequestParam("s") shortLink: String): Result<Int> {
        val q = KtQueryWrapper(Post::class.java)
        q.eq(Post::shortLink, shortLink).ne(Post::id, id)
        return Result.ok(ps.count(q))
    }

    @PostMapping
    @ApiOperation("创建文章", notes = "创建草稿并返回草稿")
    fun create(): Result<Draft> {
        val post = ps.create() ?: throw MessageCodeException(CodeDef.FAILED_TO_SAVE_DATA)
        val draft = ds.create(post)
        return if (draft != null) Result.ok(draft) else Result.code(CodeDef.FAILED_TO_SAVE_DATA)
    }

    @PutMapping("/{id}")
    @ApiOperation("更新文章", notes = "查找文章最新的草稿，没有则创建返回草稿")
    @ApiImplicitParam("文章ID", name = "id", required = true, paramType = "path")
    fun update(@PathVariable("id") id: Int): Result<Draft> {
        val draft = ds.update(id)
        return if (draft != null) Result.ok(draft) else Result.code(CodeDef.FAILED_TO_SAVE_DATA)
    }
}
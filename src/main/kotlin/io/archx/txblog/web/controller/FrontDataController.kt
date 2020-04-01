package io.archx.txblog.web.controller

import io.archx.txblog.common.def.CodeDef
import io.archx.txblog.common.web.Result
import io.archx.txblog.data.domain.Comment
import io.archx.txblog.data.domain.Post
import io.archx.txblog.data.domain.Tag
import io.archx.txblog.data.dto.CommentDto
import io.archx.txblog.data.service.CategoryService
import io.archx.txblog.data.service.CommentService
import io.archx.txblog.data.service.PostService
import io.archx.txblog.data.service.TagService
import io.archx.txblog.web.vo.PageVo
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/data")
@Api(tags = ["前端使用控制器"])
class FrontDataController(
    val postService: PostService,
    val tagService: TagService,
    val categoryService: CategoryService,
    val commentService: CommentService) {


    @GetMapping("/posts")
    @ApiOperation("文章列表", notes = "文章分页列表", response = Post::class, responseContainer = "Result")
    @ApiImplicitParams(
        ApiImplicitParam("当前页", name = "current", defaultValue = "1"),
        ApiImplicitParam("每页显示记录数", name = "size", defaultValue = "15"),
        ApiImplicitParam("搜索关键字", name = "keywords", required = false)
    )
    fun posts(
        @RequestParam("current", defaultValue = "1") current: Int,
        @RequestParam("size", defaultValue = "15") size: Int,
        @RequestParam("keywords", required = false) keywords: String?): Result<PageVo<Post>> {
        return Result.ok(postService.findAll(current, size, keywords).convert { p ->
            p.content = "" // 清空内容提高响应数据
            p
        })
    }

    @GetMapping("/posts/category/{id}")
    @ApiOperation("分类查找", notes = "分类文章列表", response = Post::class, responseContainer = "PageVo")
    @ApiImplicitParams(
        ApiImplicitParam("当前页", name = "current", defaultValue = "1"),
        ApiImplicitParam("每页显示记录数", name = "size", defaultValue = "15"),
        ApiImplicitParam("分类ID", name = "id", required = true, paramType = "path")
    )
    fun posts(
        @RequestParam("current", defaultValue = "1") current: Int,
        @RequestParam("size", defaultValue = "15") size: Int,
        @PathVariable("id") id: Int): Result<PageVo<Post>> {
        return Result.ok(postService.findAll(current, size, id).convert { p ->
            p.content = "" // 清空内容提高响应数据
            p
        })
    }

    @GetMapping("/posts/tags/{id}")
    @ApiOperation("标签查找", notes = "标签文章列表", response = Post::class, responseContainer = "PageVo")
    @ApiImplicitParams(
        ApiImplicitParam("当前页", name = "current", defaultValue = "1"),
        ApiImplicitParam("每页显示记录数", name = "size", defaultValue = "15"),
        ApiImplicitParam("标签ID", name = "id", required = true, paramType = "path")
    )
    fun postsT(
        @RequestParam("current", defaultValue = "1") current: Int,
        @RequestParam("size", defaultValue = "15") size: Int,
        @PathVariable("id") id: Int): Result<PageVo<Post>> {
        return Result.ok(postService.findAllT(current, size, id).convert { p ->
            p.content = "" // 清空内容提高响应数据
            p
        })
    }

    @GetMapping("/s/{link}")
    @ApiOperation("指定文章", notes = "根据短链接获取指定文章")
    @ApiImplicitParam("短链接", name = "link", required = true, paramType = "path")
    fun posts(@PathVariable("link") link: String): Result<Post> {
        val post = postService.findByShortLink(link) ?: return Result.code(CodeDef.INVALID_OR_DELETED)
        return Result.ok(post)
    }

    @GetMapping("/tags")
    @ApiOperation("标签列表", notes = "可用标签列表")
    fun tags() = Result.ok<List<Tag>>(tagService.list().filter { it.state == 0 })

    @GetMapping("/categories")
    @ApiOperation("分类列表", notes = "返回可用的分类列表")
    fun categories() = Result.ok(categoryService.findAll())

    @GetMapping("/comments/{id}")
    @ApiOperation("获取文章评论", notes = "根据文章ID获取文章评论", response = Comment::class, responseContainer = "List")
    @ApiImplicitParam("文章ID", name = "id", paramType = "path", dataType = "Int")
    fun comments(@PathVariable("id") postId: Int) = Result.ok(commentService.findCommentsByPostId(postId))

    @PostMapping("/comments")
    @ApiOperation("创建评论", notes = "创建评论", response = Comment::class)
    @ApiImplicitParam("评论体", name = "dto", paramType = "body", dataTypeClass = CommentDto::class)
    fun post(@RequestBody dto: CommentDto): Result<Comment> {
        val comment = commentService.create(dto)
        return if (comment != null) Result.ok(comment) else Result.code(CodeDef.FAILED_TO_SAVE_DATA)
    }
}
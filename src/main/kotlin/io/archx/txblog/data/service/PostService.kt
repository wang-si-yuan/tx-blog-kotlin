package io.archx.txblog.data.service

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.IdWorker
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.archx.txblog.common.util.DateUtils
import io.archx.txblog.data.domain.Category
import io.archx.txblog.data.domain.Post
import io.archx.txblog.data.domain.PostTag
import io.archx.txblog.data.domain.Tag
import io.archx.txblog.data.mapper.PostMapper
import io.archx.txblog.web.vo.PageVo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import java.util.stream.Collectors

interface PostService : IService<Post> {

    fun create(): Post?

    fun findAll(current: Int, size: Int, keywords: String?): PageVo<Post>

    fun findAll(current: Int, size: Int, categoryId: Int): PageVo<Post>

    fun findAllT(current: Int, size: Int, tagId: Int): PageVo<Post>

    fun findById(id: Int): Post?

    fun findByShortLink(link: String): Post?

    fun remove(postId: Int): Boolean
}

@Service
class PostServiceImpl(
    val ts: TagService,
    val pts: PostTagService,
    val cs: CategoryService,
    val ds: DraftService,
    val cms: CommentService) : ServiceImpl<PostMapper, Post>(), PostService {

    override fun create(): Post? {
        val post = Post(title = "请输入标题", brief = "请输入简介", categoryId = 1, shortLink = IdWorker.getIdStr(), createdAt = DateUtils.timestamp())
        return if (save(post)) post else null
    }

    override fun findAll(current: Int, size: Int, keywords: String?): PageVo<Post> {
        val page = Page<Post>(current.toLong(), size.toLong())
        val query = KtQueryWrapper(Post::class.java)

        if (StringUtils.hasText(keywords)) {
            val q = "%$keywords%"
            query.like(Post::title, q).or().like(Post::content, q)
        }
        query.orderByDesc(Post::createdAt)
        val result = page(page, query)

        // 手动查询分类和标签，否则有N+1问题
        if (result.records.isNotEmpty()) {
            fillCategoryAndTags(result)
        }
        return PageVo(result.total.toInt(), current, size, result.records)
    }


    override fun findAll(current: Int, size: Int, categoryId: Int): PageVo<Post> {
        val page = Page<Post>(current.toLong(), size.toLong())
        val query = KtQueryWrapper(Post::class.java)

        query.eq(Post::categoryId, categoryId)

        val result = page(page, query)

        // 手动查询分类和标签，否则有N+1问题
        if (result.records.isNotEmpty()) {
            fillCategoryAndTags(result)
        }
        val category = cs.getById(categoryId)
        return PageVo(result.total.toInt(), current, size, result.records)
            .attribute("type", "c")
            .attribute("value", category)
    }

    override fun findAllT(current: Int, size: Int, tagId: Int): PageVo<Post> {
        val page = Page<Post>(current.toLong(), size.toLong())
        val result = baseMapper.findAllByTagId(page, tagId)
        // 手动查询分类和标签，否则有N+1问题
        if (result.records.isNotEmpty()) {
            fillCategoryAndTags(result)
        }
        val tag = ts.getById(tagId)
        return PageVo(result.total.toInt(), current, size, result.records)
            .attribute("type", "t")
            .attribute("value", tag)
    }

    override fun findById(id: Int): Post? {
        return baseMapper.findById(id)
    }

    override fun findByShortLink(link: String): Post? {
        return baseMapper.findByShortLink(link)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun remove(postId: Int): Boolean {
        // 清除所有相关
        if (removeById(postId)) {
            // 清除标签
            pts.removeByPostId(postId)
            // 清除草稿
            ds.removeByPostId(postId)
            // 清除评论
            cms.removeCommentByPostId(postId)
            return true
        }
        return false
    }

    private fun fillCategoryAndTags(result: IPage<Post>) {
        val categoryIds = HashSet<Int>()
        val postIds = HashSet<Int>()
        for (record in result.records) {
            categoryIds.add(record.categoryId)
            postIds.add(record.id)
        }

        // 查询标签
        val pTags = pts.findByPostIds(postIds.toList())
        val tagIds = pTags.map(PostTag::tagId)
        val tagMap = ts.findByIds(tagIds).stream().collect(Collectors.toMap(Tag::id) { it })
        // 查询分类
        val catMap = cs.findByIds(categoryIds.toList()).stream().collect(Collectors.toMap(Category::id) { it })
        // 文章TAG分类
        val ptMap = pTags.groupBy { it.postId }//.stream().collect(Collectors.groupingBy(PostTag::postId))

        result.records.forEach { it ->
            val id = it.id
            val cat = catMap[it.categoryId]
            if (cat != null) {
                it.category = cat
            }
            val ptl = ptMap[id]
            if (ptl != null) {
                val tags = ptl.filter { tm -> tagMap.containsKey(tm.tagId) }.map { tm -> tagMap[tm.tagId]!! }
                it.tags = tags
            }

        }
    }
}
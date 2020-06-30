package io.archx.txblog.data.service

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.IService
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.archx.txblog.common.def.CodeDef
import io.archx.txblog.common.exception.MessageCodeException
import io.archx.txblog.common.util.DateUtils
import io.archx.txblog.data.domain.Draft
import io.archx.txblog.data.domain.DraftTag
import io.archx.txblog.data.domain.Post
import io.archx.txblog.data.domain.PostTag
import io.archx.txblog.data.mapper.DraftMapper
import io.archx.txblog.data.mapper.DraftTagMapper
import io.archx.txblog.data.mapper.PostMapper
import io.archx.txblog.data.mapper.PostTagMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface DraftService : IService<Draft> {

    fun findByPostId(id: Int): Draft?

    fun findById(id: Long): Draft?

    fun create(post: Post): Draft?

    fun update(postId: Int): Draft?

    fun saveAsNew(draft: Draft): Boolean

    fun publish(draft: Draft): Draft?

    fun remove(id: Long): Draft?

    fun removeByPostId(id: Int)
}

@Service
class DraftServiceImpl(val pm: PostMapper, val ptm: PostTagMapper, val dtm: DraftTagMapper) : ServiceImpl<DraftMapper, Draft>(), DraftService {

    override fun findByPostId(id: Int): Draft? {
        val draft = baseMapper.findLatestByPostId(id)
        return if (draft != null) findById(draft.id) else null
    }

    override fun findById(id: Long): Draft? {
        return baseMapper.findById(id)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun create(post: Post): Draft? {
        return duplicate(post)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun update(postId: Int): Draft? {
        val draft = baseMapper.findLatestByPostId(postId)
        if (draft != null) {
            return draft
        }
        val post = pm.findById(postId) ?: throw MessageCodeException(CodeDef.RECORD_NOT_FOUND)

        return duplicate(post)
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun saveAsNew(draft: Draft): Boolean {
        if (save(draft)) {
            for (draftTag in draft.tags.map { DraftTag(draftId = draft.id, tagId = it.id) }) {
                dtm.insert(draftTag)
            }
            synchronize(draft)
            return true
        }
        return false
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun publish(draft: Draft): Draft? {
        if (saveAsNew(draft)) {
            // 修改post文章状态
            val update = KtUpdateWrapper(Post::class.java)
            update.eq(Post::id, draft.postId)
                .set(Post::title, draft.title)
                .set(Post::brief, draft.brief)
                .set(Post::content, draft.content)
                .set(Post::categoryId, draft.categoryId)
                .set(Post::shortLink, draft.shortLink)
                .set(Post::pin, draft.pin)
                .set(Post::state, 1) // 1 已发布
                .set(Post::updatedAt, DateUtils.timestamp())

            pm.update(null, update)

            // 更新标签
            // 删除旧版标签
            val query = KtQueryWrapper(PostTag::class.java)
            query.eq(PostTag::postId, draft.postId)
            ptm.delete(query)
            // 写入新标签
            draft.tags.map { PostTag(postId = draft.postId, tagId = it.id) }.forEach {
                ptm.insert(it)
            }
            return draft
        }
        return null
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun remove(id: Long): Draft? {
        val draft = getById(id)
        if (draft != null && removeById(id)) {
            val query = KtQueryWrapper(DraftTag::class.java)
            query.eq(DraftTag::draftId, id)
            dtm.delete(query)
        }
        return draft
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun removeByPostId(id: Int) {
        val query = KtQueryWrapper(Draft::class.java)
        query.eq(Draft::postId, id)
        val ids = list(query).map(Draft::id)
        // 清除草稿
        remove(query)
        // 清除草稿标签
        if (ids.isNotEmpty()) {
            val dtq = KtQueryWrapper(DraftTag::class.java)
            dtq.`in`(DraftTag::draftId, ids)
            dtm.delete(dtq)
        }
    }

    private fun duplicate(post: Post): Draft {
        val draft = Draft(
            postId = post.id,
            title = post.title,
            brief = post.brief,
            content = post.content,
            categoryId = post.categoryId,
            shortLink = post.shortLink,
            pin = post.pin,
            state = post.state,
            createdAt = DateUtils.timestamp()
        )

        save(draft)

        // save tags
        val query = KtQueryWrapper(PostTag::class.java)
        query.eq(PostTag::postId, post.id)
        val list = ptm.selectList(query);
        list.map { DraftTag(draftId = draft.id, tagId = it.tagId) }.forEach { dtm.insert(it) }

        return draft
    }

    private fun synchronize(draft: Draft) {
        // 修改未发布文章的基本信息
        val update = KtUpdateWrapper(Post::class.java)
        update.eq(Post::id, draft.postId).eq(Post::state, 0)
            .set(Post::title, draft.title)
            .set(Post::brief, draft.brief)
            .set(Post::content, draft.content)
            .set(Post::categoryId, draft.categoryId)
            .set(Post::shortLink, draft.shortLink)
            .set(Post::pin, draft.pin)
            .set(Post::updatedAt, DateUtils.timestamp())

        pm.update(null, update)
    }
}
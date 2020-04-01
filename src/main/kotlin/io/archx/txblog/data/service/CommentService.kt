package io.archx.txblog.data.service

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.baomidou.mybatisplus.extension.service.IService
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.archx.txblog.common.def.CodeDef
import io.archx.txblog.common.exception.MessageCodeException
import io.archx.txblog.common.util.DateUtils
import io.archx.txblog.data.domain.Comment
import io.archx.txblog.data.domain.Post
import io.archx.txblog.data.dto.CommentDto
import io.archx.txblog.data.mapper.CommentMapper
import io.archx.txblog.data.mapper.PostMapper
import io.archx.txblog.web.vo.PageVo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.ArrayList

interface CommentService : IService<Comment> {

    /**
     * 分页搜索，后台管理使用
     */
    fun findAll(current: Int, size: Int, keywords: String?): PageVo<Comment>

    /**
     * 创建评论
     */
    fun create(dto: CommentDto): Comment?

    /**
     * 根据文章ID获取评论
     */
    fun findCommentsByPostId(postId: Int): List<Comment>

    /**
     * 移除评论
     */
    fun removeComment(id: Int)

    /**
     * 根据文章移除评论
     */
    fun removeCommentByPostId(postId: Int)

    /**
     * 审核通过
     */
    fun pass(id: Int): Boolean
}

@Service
class CommentServiceImpl(val pm: PostMapper) : ServiceImpl<CommentMapper, Comment>(), CommentService {

    override fun findAll(current: Int, size: Int, keywords: String?): PageVo<Comment> {
        val page = Page<Comment>(current.toLong(), size.toLong())
        val result = baseMapper.findAll(page, keywords)
        return PageVo(result.total.toInt(), current, size, result.records)
    }

    override fun create(dto: CommentDto): Comment? {

        pm.selectById(dto.postId) ?: throw MessageCodeException(CodeDef.INVALID_OR_DELETED)


        val cmt = when (dto.parentId) {
            0 -> Comment(0, dto.content, dto.name, dto.email, dto.postId, Post(), dto.parentId, 0, 0, 0, DateUtils.timestamp())
            else -> {
                val parent = getById(dto.parentId) ?: throw MessageCodeException(CodeDef.RECORD_NOT_FOUND)
                val rootId = if (parent.parentId == 0) parent.id else parent.rootId

                // 性能考虑知道 level 2
                if (parent.level == 2) {
                    throw MessageCodeException(CodeDef.FAILED_TO_SAVE_DATA)
                }

                Comment(0, dto.content, dto.name, dto.email, dto.postId, Post(), dto.parentId, parent.level + 1, rootId, 0, DateUtils.timestamp())
            }
        }
        // TODO 通知作者 或 上级用户

        return if (save(cmt)) cmt else null
    }

    override fun findCommentsByPostId(postId: Int): List<Comment> {
        // 查询审核通过的
        val query = KtQueryWrapper(Comment::class.java)
        query.eq(Comment::postId, postId).eq(Comment::state, 1)
        val list = list(query)

        // 组织为正常显示结构

        // 转换为hash表
        val map = list.fold(Hashtable<Int, ArrayList<Comment>>()) { acc, c ->
            val m = acc[c.parentId] ?: ArrayList()
            m.add(c)
            acc[c.parentId] = m
            acc
        }

        // 遍历
        val nList = ArrayList<Comment>()
        traversal(nList, list.filter { it.level == 0 }.toMutableList(), map)
        return nList
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun removeComment(id: Int) {
        val cmt = getById(id) ?: throw MessageCodeException(CodeDef.RECORD_NOT_FOUND)

        val query = KtQueryWrapper(Comment::class.java)
        // 是根节点
        if (cmt.parentId == 0) {
            query.eq(Comment::rootId, cmt.id)

            remove(query)
            removeById(cmt.id)
            return
        }

        // 查找该文章下的所有评论
        query.eq(Comment::postId, cmt.postId)
        val list = list(query)
        // 转换为hash表
        val map = list.fold(Hashtable<Int, ArrayList<Int>>()) { acc, c ->
            val m = acc[c.parentId] ?: ArrayList()
            m.add(c.id)
            acc[c.parentId] = m
            acc
        }
        // 查找当前节点下的所有节点
        val ids = getChildren(map, id)
        ids.add(id)
        removeByIds(ids)
    }

    override fun removeCommentByPostId(postId: Int) {
        val query = KtQueryWrapper(Comment::class.java)
        query.eq(Comment::postId, postId)
        remove(query)
    }

    override fun pass(id: Int): Boolean {
        val update = KtUpdateWrapper(Comment::class.java)
        update.eq(Comment::id, id).set(Comment::state, 1).set(Comment::updatedAt, DateUtils.timestamp())
        return update(update)
    }

    private fun getChildren(map: Map<Int, ArrayList<Int>>, rootId: Int): ArrayList<Int> {
        val child = map[rootId] ?: return ArrayList()
        val list = ArrayList<Int>()
        for (item in child) {
            list.add(item)
            list += getChildren(map, item)
        }
        return list
    }

    private fun traversal(container: ArrayList<Comment>, list: MutableList<Comment>, map: Hashtable<Int, ArrayList<Comment>>) {
        list.sortBy { it.createdAt }
        list.forEach { it ->
            container.add(it)
            val l = map[it.id]
            if (l != null) {
                traversal(container, l, map)
            }
        }
    }
}
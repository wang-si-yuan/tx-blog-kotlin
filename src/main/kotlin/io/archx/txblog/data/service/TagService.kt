package io.archx.txblog.data.service

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.IService
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.archx.txblog.common.def.CodeDef
import io.archx.txblog.common.exception.MessageCodeException
import io.archx.txblog.common.getLogger
import io.archx.txblog.common.util.DateUtils
import io.archx.txblog.data.domain.Tag
import io.archx.txblog.data.mapper.TagMapper
import org.springframework.stereotype.Service

interface TagService : IService<Tag> {

    /**
     * 创建TAG
     */
    fun create(name: String, userId: Int): Tag?

    /**
     * 更新TAG
     */
    fun update(id: Int, name: String, userId: Int): Tag?

    /**
     * 删除TAG
     */
    fun delete(id: Int, userId: Int)

    fun findByIds(ids: List<Int>): List<Tag>
}

@Service
class TagServiceImpl : ServiceImpl<TagMapper, Tag>(), TagService {

    val logger = getLogger()

    override fun create(name: String, userId: Int): Tag? {
        // 检测重复
        checkDuplicate(name, userId)

        val tag = Tag(0, name, userId, 0, DateUtils.timestamp())
        return if (save(tag)) tag else null
    }

    override fun update(id: Int, name: String, userId: Int): Tag? {
        val db = getById(id)
        if (db == null || db.userId != userId) {
            throw MessageCodeException(CodeDef.RECORD_NOT_FOUND)
        }

        // 检测重复
        checkDuplicate(name, userId)

        val wrapper = KtUpdateWrapper(Tag::class.java)
        wrapper.eq(Tag::id, id).set(Tag::name, name).set(Tag::updatedAt, DateUtils.timestamp())
        return if (update(wrapper)) getById(id) else null
    }

    override fun delete(id: Int, userId: Int) {
        val db = getById(id)
        if (db == null || db.userId != userId) {
            throw MessageCodeException(CodeDef.RECORD_NOT_FOUND)
        }

        this.removeById(id)
    }

    override fun findByIds(ids: List<Int>): List<Tag> {
        if (ids.isEmpty()) return ArrayList()
        val query = KtQueryWrapper(Tag::class.java)
        query.`in`(Tag::id, ids)
        return list(query)
    }

    private fun checkDuplicate(name: String, userId: Int) {
        val exp = KtQueryWrapper(Tag::class.java)
        exp.eq(Tag::name, name).eq(Tag::state, 0)
        if (count(exp) > 0) {
            logger.error("duplicate record [ name = {} ], user_id = {}", name, userId)
            throw MessageCodeException(CodeDef.DUPLICATE_RECORD)
        }
    }
}
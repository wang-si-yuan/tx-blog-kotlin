package io.archx.txblog.data.service

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.baomidou.mybatisplus.extension.service.IService
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.archx.txblog.common.def.CodeDef
import io.archx.txblog.common.exception.MessageCodeException
import io.archx.txblog.common.getLogger
import io.archx.txblog.common.util.DateUtils
import io.archx.txblog.data.domain.Category
import io.archx.txblog.data.domain.Post
import io.archx.txblog.data.domain.Tag
import io.archx.txblog.data.mapper.CategoryMapper
import io.archx.txblog.data.mapper.PostMapper
import org.springframework.stereotype.Service

interface CategoryService : IService<Category> {

    /**
     * 创建分类
     */
    fun create(category: String, userId: Int): Category?

    /**
     * 更新分类
     */
    fun update(id: Int, category: String, userId: Int): Category?

    /**
     * 删除分类
     */
    fun delete(id: Int, userId: Int)

    /**
     * 根据ID查找
     */
    fun findByIds(ids: List<Int>): List<Category>

    /**
     * 前端使用，统计发布文章数
     */
    fun findAll(): List<Category>
}

@Service
class CategoryServiceImpl(val pm: PostMapper) : ServiceImpl<CategoryMapper, Category>(), CategoryService {
    val logger = getLogger()

    override fun create(category: String, userId: Int): Category? {
        // 检测重复
        checkDuplicate(category, userId)

        val cat = Category(0, category, 0, userId, 0, DateUtils.timestamp())
        return if (save(cat)) cat else null
    }

    override fun update(id: Int, category: String, userId: Int): Category? {
        val db = getById(id)
        if (db == null || db.userId != userId) {
            throw MessageCodeException(CodeDef.RECORD_NOT_FOUND)
        }

        // 检测重复
        checkDuplicate(category, userId)

        val wrapper = KtUpdateWrapper(Category::class.java)
        wrapper.eq(Category::id, id).set(Category::category, category).set(Tag::updatedAt, DateUtils.timestamp())
        return if (update(wrapper)) getById(id) else null
    }

    override fun delete(id: Int, userId: Int) {
        val db = getById(id)
        if (db == null || db.userId != userId) {
            throw MessageCodeException(CodeDef.RECORD_NOT_FOUND)
        }

        val query = KtQueryWrapper(Post::class.java)
        query.eq(Post::categoryId, id)
        if (pm.selectCount(query) > 0) {
            throw MessageCodeException(CodeDef.RECORD_HAS_REFERENCE)
        }
        this.removeById(id)
    }

    private fun checkDuplicate(name: String, userId: Int) {
        val query = KtQueryWrapper(Category::class.java)
        query.eq(Category::category, name).eq(Category::state, 0)
        if (count(query) > 0) {
            logger.error("duplicate record [ name = {} ], user_id = {}", name, userId)
            throw MessageCodeException(CodeDef.DUPLICATE_RECORD)
        }
    }

    override fun findByIds(ids: List<Int>): List<Category> {
        val query = KtQueryWrapper(Category::class.java)
        query.`in`(Category::id, ids)
        return list(query)
    }

    override fun findAll(): List<Category> {
        return baseMapper.findAll()
    }
}

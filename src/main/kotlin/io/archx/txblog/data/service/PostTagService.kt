package io.archx.txblog.data.service

import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import com.baomidou.mybatisplus.extension.service.IService
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import io.archx.txblog.data.domain.PostTag
import io.archx.txblog.data.mapper.PostTagMapper
import org.springframework.stereotype.Service

interface PostTagService : IService<PostTag> {

    fun findByPostIds(ids: List<Int>): List<PostTag>

    fun removeByPostId(id: Int)
}

@Service
class PostTagServiceImpl : ServiceImpl<PostTagMapper, PostTag>(), PostTagService {

    override fun findByPostIds(ids: List<Int>): List<PostTag> {
        val query = KtQueryWrapper(PostTag::class.java)
        query.`in`(PostTag::postId, ids)
        return list(query)
    }

    override fun removeByPostId(id: Int) {
        val query = KtQueryWrapper(PostTag::class.java)
        query.eq(PostTag::postId, id)
        remove(query)
    }
}
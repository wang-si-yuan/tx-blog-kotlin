package io.archx.txblog.data.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.metadata.IPage
import io.archx.txblog.data.domain.Post
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface PostMapper : BaseMapper<Post> {

    fun findById(@Param("id") id: Int): Post?

    fun findByShortLink(@Param("s") shortLink: String): Post?

    fun findAllByTagId(page: IPage<Post>, @Param("tagId") tagId: Int): IPage<Post>
}
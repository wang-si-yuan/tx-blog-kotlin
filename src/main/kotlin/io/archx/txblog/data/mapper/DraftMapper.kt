package io.archx.txblog.data.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import io.archx.txblog.data.domain.Draft
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface DraftMapper : BaseMapper<Draft> {

    fun findById(@Param("id") id: Long): Draft?

    @Select("select id, post_id from tx_drafts where post_id = #{postId} order by created_at desc limit 1")
    fun findLatestByPostId(@Param("postId") postId: Int): Draft?
}
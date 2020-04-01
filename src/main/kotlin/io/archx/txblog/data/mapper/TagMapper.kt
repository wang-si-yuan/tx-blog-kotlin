package io.archx.txblog.data.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import io.archx.txblog.data.domain.Tag
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface TagMapper : BaseMapper<Tag> {

    fun findForPost(@Param("postId") postId: Int): List<Tag>
}
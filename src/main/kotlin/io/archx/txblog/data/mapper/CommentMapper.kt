package io.archx.txblog.data.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.core.metadata.IPage
import io.archx.txblog.data.domain.Comment
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface CommentMapper : BaseMapper<Comment> {

    fun findAll(page: IPage<Comment>, @Param("searchText") text: String?): IPage<Comment>
}
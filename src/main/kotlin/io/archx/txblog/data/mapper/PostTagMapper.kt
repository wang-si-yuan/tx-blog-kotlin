package io.archx.txblog.data.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import io.archx.txblog.data.domain.PostTag
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface PostTagMapper : BaseMapper<PostTag> {
}
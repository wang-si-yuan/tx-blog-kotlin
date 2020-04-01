package io.archx.txblog.data.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import io.archx.txblog.data.domain.DraftTag
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface DraftTagMapper : BaseMapper<DraftTag> {
}
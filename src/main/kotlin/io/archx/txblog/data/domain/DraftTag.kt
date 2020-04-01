package io.archx.txblog.data.domain

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

@TableName("tx_draft_tags")
data class DraftTag(
    @TableId(type = IdType.AUTO)
    var id: Long = 0,
    var draftId: Long = 0,
    var tagId: Int = 0,
    var createdAt: Long = 0,
    var updatedAt: Long = 0
)
package io.archx.txblog.data.domain

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

/**
 * 标签实体
 */
@TableName("tx_tag")
data class Tag(
    @TableId(type = IdType.AUTO)
    var id: Int = 0,
    var name: String = "",
    var userId: Int = 0,
    var state: Int = 0,
    var createdAt: Long = 0,
    var updatedAt: Long = 0
)
package io.archx.txblog.data.domain

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

/**
 * 分类实体
 */
@TableName("tx_category")
data class Category(
    @TableId(type = IdType.AUTO)
    var id: Int = 0,
    var category: String = "",

    // 已发布文章数量
    @TableField(exist = false)
    var posts: Int = 0,

    var userId: Int = 0,
    var state: Int = 0,
    var createdAt: Long = 0,
    var updatedAt: Long = 0
)
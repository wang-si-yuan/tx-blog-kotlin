package io.archx.txblog.data.domain

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

@TableName("tx_drafts")
data class Draft(
    @TableId(type = IdType.AUTO)
    var id: Long = 0,
    var postId: Int = 0,
    var title: String = "",
    var brief: String = "",
    var content: String = "",
    var categoryId: Int = 0,
    // 非数据库存在字段
    @TableField(exist = false)
    var category: Category = Category(), // 分类
    @TableField(exist = false)
    var tags: List<Tag> = ArrayList(), // 标签名称
    var shortLink: String = "",
    var pin: Int = 0,
    var state: Int = 0,
    var createdAt: Long = 0,
    var updatedAt: Long = 0
)
package io.archx.txblog.data.domain

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

/**
 * 评论实体，未审核不呈现
 * <p/>
 * 性能考虑只到 level 2
 *
 * <pre>
 * - 这是一条评论 level 0
 * - - 评论回复 1 level 1
 * - - - 评论回复 1 的回复 level 2
 * - - 评论回复 2 level 2
 * - 这是另一条 level 0
 * - - 评论回复 1 level 1
 * </pre>
 */
@TableName("tx_comment")
data class Comment(
    @TableId(type = IdType.AUTO)
    var id: Int = 0,
    var content: String = "",
    var name: String = "",
    var email: String = "",
    var postId: Int = 0,

    // 文章关联查询
    @TableField(exist = false)
    val post: Post = Post(),

    var parentId: Int = 0,
    val level: Int = 0,
    var rootId: Int = 0,
    var state: Int = 0, // 0 未审核, 1 已审核
    var createdAt: Long = 0,
    var updatedAt: Long = 0
)
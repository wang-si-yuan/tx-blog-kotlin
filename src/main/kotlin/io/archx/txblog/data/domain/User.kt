package io.archx.txblog.data.domain

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName

/**
 * 用户实体
 */
@TableName("tx_user")
data class User(
    @TableId(type = IdType.AUTO)
    var id: Int = 0,
    var username: String = "",
    var password: String = "",
    var salt: String = "",
    var email: String = "",
    var state: Int = 0,
    var createdAt: Long = 0,
    var updatedAt: Long = 0
)
/*{
    // mybatis 需要无参构造函数
    // 否则可能会出现 ResultMapException: Error attempting to get column 'xxx' from result set.
    // 主构造函数全部添加默认值也可
    constructor() : this(0, "", "", "", "")
}*/
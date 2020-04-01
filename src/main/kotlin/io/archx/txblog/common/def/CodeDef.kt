package io.archx.txblog.common.def

enum class CodeDef(override val code: Int, override val message: String) : MessageCode {

    Success(0, "ok"),
    Failure(1, "fail"),

    /**
     * 未知账户
     */
    UNKNOWN_ACCOUNT(1001, "UNKNOWN ACCOUNT"),
    /**
     * 无效凭证
     */
    INVALID_CERTIFICATE(1002, "INVALID CERTIFICATE"),

    /**
     * 未授权
     */
    UNAUTHORIZED(1003, "UNAUTHORIZED"),

    /**
     * 缺失参数
     */
    MISSING_PARAMETER(4000, "MISSING PARAMETER"),


    /**
     * 保存数据失败
     */
    FAILED_TO_SAVE_DATA(4001, "FAILED TO SAVE DATA"),

    /**
     * 重复记录
     */
    DUPLICATE_RECORD(4002, "DUPLICATE RECORD"),

    /**
     * 无效或已删除
     */
    INVALID_OR_DELETED(4003, "INVALID OR DELETED"),

    /**
     * 未找到记录
     */
    RECORD_NOT_FOUND(4004, "RECORD_NOT_FOUND"),

    /**
     * 不支持的文件格式
     */
    UNSUPPORTED_FILE_FORMAT(4005, "UNSUPPORTED FILE FORMAT"),

    /**
     * 无效内容
     */
    INVALID_CONTENT(4006, "INVALID CONTENT"),

    /**
     * 记录存在引用
     */
    RECORD_HAS_REFERENCE(4007, "RECORD HAS REFERENCE"),
}
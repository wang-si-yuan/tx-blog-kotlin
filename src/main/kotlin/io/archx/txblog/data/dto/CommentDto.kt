package io.archx.txblog.data.dto

data class CommentDto(val postId: Int, val parentId: Int, val name: String, val email: String, val content: String)
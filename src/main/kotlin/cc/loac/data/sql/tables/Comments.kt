package cc.loac.data.sql.tables

import org.jetbrains.exposed.sql.Table

/**
 * 评论表
 */
object Comments: Table("comment") {
    /** 评论 ID **/
    val commentId = long("comment_id").autoIncrement()
    /** 文章 ID **/
    val postId = long("post_id").references(Posts.postId)
    /** 父评论 ID **/
    val parentCommentId = long("parent_comment_id").nullable()
    /** 回复评论 ID **/
    val replyCommentId = long("reply_comment_id").nullable()
    /** 回复用户名 **/
    val replyDisplayName = varchar("reply_display_name", 128).nullable()
    /** 评论内容 **/
    val content = text("content")
    /** 评论人站点 **/
    val site = varchar("site", 512).nullable()
    /** 评论人名称 **/
    val displayName = varchar("display_name", 128)
    /** 评论人邮箱 **/
    val email = varchar("email", 128)
    /** 评论时间  **/
    val createTime = long("create_time")
    /** 是否通过审核 **/
    val isPass = bool("is_pass")

    override val primaryKey = PrimaryKey(commentId)
}
package uz.otamurod.socialmediaktor.dao.follows

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object FollowsTable : Table(name = "follows") {
    val followerId = long("follower_id")
    val followingId = long("following_id")
    val followDate = datetime("follow_date").defaultExpression(CurrentDateTime)
}
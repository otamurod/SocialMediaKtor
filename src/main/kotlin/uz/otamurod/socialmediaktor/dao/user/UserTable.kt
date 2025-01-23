package uz.otamurod.socialmediaktor.dao.user

import org.jetbrains.exposed.sql.Table

object UserTable : Table(name = "users") {
    val id = long("user_id").autoIncrement()
    val username = varchar("user_name", length = 250)
    val email = varchar("user_email", length = 250)
    val bio = text("user_bio").default(
        defaultValue = "Hello! Welcome to my Social App page"
    )
    val password = varchar("user_password", length = 100)
    val imageUrl = text("image_url").nullable()
    val followersCount = integer("followers_count").default(defaultValue = 0)
    val followingCount = integer("following_count").default(defaultValue = 0)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}
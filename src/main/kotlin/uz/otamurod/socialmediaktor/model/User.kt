package uz.otamurod.socialmediaktor.model

import org.jetbrains.exposed.sql.Table

object UserRow : Table(name = "users") {
    val id = integer("user_id").autoIncrement()
    val username = varchar("user_name", length = 250)
    val email = varchar("user_email", length = 250)
    val bio = text("user_bio").default(
        defaultValue = "Hello! Welcome to my Social App page"
    )
    val password = varchar("user_password", length = 100)
    val avatar = text("user_avatar").nullable()

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}

data class User(
    val id: Int,
    val username: String,
    val bio: String,
    val password: String,
    val avatar: String?
)
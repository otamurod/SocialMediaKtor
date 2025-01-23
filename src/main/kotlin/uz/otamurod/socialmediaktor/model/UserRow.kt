package uz.otamurod.socialmediaktor.model

data class UserRow(
    val id: Int,
    val username: String,
    val bio: String,
    val password: String,
    val imageUrl: String?,
    val followersCount: Int,
    val followingCount: Int,
)
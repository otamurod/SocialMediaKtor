package uz.otamurod.socialmediaktor.dao.user

import uz.otamurod.socialmediaktor.model.SignUpParams
import uz.otamurod.socialmediaktor.model.User

interface UserDao {
    suspend fun insert(params: SignUpParams): User?
    suspend fun findByEmail(email: String): User?
}
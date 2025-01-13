package uz.otamurod.socialmediaktor.repository.user

import uz.otamurod.socialmediaktor.model.AuthResponse
import uz.otamurod.socialmediaktor.model.SignInParams
import uz.otamurod.socialmediaktor.model.SignUpParams
import uz.otamurod.socialmediaktor.util.Response

interface UserRepository {
    suspend fun signUp(params: SignUpParams): Response<AuthResponse>
    suspend fun signIn(params: SignInParams): Response<AuthResponse>
}
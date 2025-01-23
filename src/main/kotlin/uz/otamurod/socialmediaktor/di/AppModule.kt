package uz.otamurod.socialmediaktor.di

import org.koin.dsl.module
import uz.otamurod.socialmediaktor.dao.user.UserDao
import uz.otamurod.socialmediaktor.dao.user.UserDaoImpl
import uz.otamurod.socialmediaktor.repository.auth.AuthRepository
import uz.otamurod.socialmediaktor.repository.auth.AuthRepositoryImpl

val appModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<UserDao> { UserDaoImpl() }
}
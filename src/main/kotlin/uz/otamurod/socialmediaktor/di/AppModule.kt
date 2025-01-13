package uz.otamurod.socialmediaktor.di

import org.koin.dsl.module
import uz.otamurod.socialmediaktor.dao.user.UserDao
import uz.otamurod.socialmediaktor.dao.user.UserDaoImpl
import uz.otamurod.socialmediaktor.repository.user.UserRepository
import uz.otamurod.socialmediaktor.repository.user.UserRepositoryImpl

val appModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<UserDao> { UserDaoImpl() }
}
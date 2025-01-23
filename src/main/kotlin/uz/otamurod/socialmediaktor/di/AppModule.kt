package uz.otamurod.socialmediaktor.di

import org.koin.dsl.module
import uz.otamurod.socialmediaktor.dao.follows.FollowsDao
import uz.otamurod.socialmediaktor.dao.follows.FollowsDaoImpl
import uz.otamurod.socialmediaktor.dao.user.UserDao
import uz.otamurod.socialmediaktor.dao.user.UserDaoImpl
import uz.otamurod.socialmediaktor.repository.auth.AuthRepository
import uz.otamurod.socialmediaktor.repository.auth.AuthRepositoryImpl
import uz.otamurod.socialmediaktor.repository.follows.FollowsRepository
import uz.otamurod.socialmediaktor.repository.follows.FollowsRepositoryImpl

val appModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<UserDao> { UserDaoImpl() }
    single<FollowsDao> { FollowsDaoImpl() }
    single<FollowsRepository> { FollowsRepositoryImpl(get(), get()) }
}
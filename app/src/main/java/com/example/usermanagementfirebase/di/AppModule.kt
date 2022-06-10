package com.example.usermanagementfirebase.di

import com.example.usermanagementfirebase.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAuthenticator() : BaseAuthenticator {
        return  FirebaseAuthenticator()
    }

    @Singleton
    @Provides
    fun provideRepository(authenticator : BaseAuthenticator) : BaseAuthRepository {
        return AuthRepository(authenticator)
    }

    @Singleton
    @Provides
    fun provideDbRepository(): RealTimeDatabaseRepository{
        return RealTimeDatabaseImpl()
    }
}
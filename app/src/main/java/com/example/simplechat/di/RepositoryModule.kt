package com.example.simplechat.di

import com.example.core.data.MessageLocalDataSource
import com.example.simplechat.framework.db.RoomMessageDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMessageLocalDataSource(roomMessageDataSource: RoomMessageDataSource): MessageLocalDataSource
}

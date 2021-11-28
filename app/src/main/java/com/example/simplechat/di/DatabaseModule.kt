package com.example.simplechat.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.core.data.DummyData
import com.example.simplechat.framework.db.AppDatabase
import com.example.simplechat.framework.db.ChatMessageDao
import com.example.simplechat.framework.db.toEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        Log.d("DatabaseModule", "providing appDb")
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.APP_DATABASE_NAME)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newSingleThreadExecutor().execute {
                        Log.d(this.javaClass.name, "prepopulating DB")
                        provideAppDatabase(context).chatMessageDao().insertMessages(DummyData().createChatMessagesDummy().map { it.toEntity() })
                        Log.d(this.javaClass.name, "populated done")
                    }
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    Log.d(this.javaClass.name, "oNopenDb")
                    super.onOpen(db)
                }
            })
            .build()
    }

    @Provides
    fun provideChatMessageDao(appDatabase: AppDatabase): ChatMessageDao {
        Log.d(this.javaClass.name, "providing dbg to dao")
        return appDatabase.chatMessageDao()
    }
}

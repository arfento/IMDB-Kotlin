package com.ims.imdb_kotlin.di

import android.content.Context
import androidx.room.Room
import com.ims.imdb_kotlin.db.DatabaseService
import com.ims.imdb_kotlin.network.ApiService
import com.ims.imdb_kotlin.utils.Helper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun getApiService() = ApiService.getInstance()

    @Provides
    fun localDb(@ApplicationContext context: Context): DatabaseService {
        return Room.databaseBuilder(context, DatabaseService::class.java, "imdb-clone-db")
            .build()
    }

    @Provides
    fun getHelper(@ApplicationContext context: Context): Helper = Helper(context)

}
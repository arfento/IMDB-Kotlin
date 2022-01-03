package com.ims.imdb_kotlin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ims.imdb_kotlin.models.Result


@Database(entities = [Result::class], exportSchema = false, version = 1)
@TypeConverters(
    IdsToStringConverter::class
)
abstract class DatabaseService : RoomDatabase(){
    abstract fun resultDao(): ResultDao
}
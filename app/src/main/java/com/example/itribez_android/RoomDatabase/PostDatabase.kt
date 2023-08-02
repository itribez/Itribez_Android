package com.example.itribez_android.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.itribez_android.RoomDatabase.entities.PostEntity
import retrofit2.http.POST

@Database(entities = [PostEntity::class], version = 1)
abstract class PostDatabase() : RoomDatabase() {
    abstract fun postDao(): PostDao
    companion object {
        private const val DATABASE_NAME = "posts"
        @Volatile
        private var INSTANCE: PostDatabase? = null
        fun getInstance(context: Context): PostDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
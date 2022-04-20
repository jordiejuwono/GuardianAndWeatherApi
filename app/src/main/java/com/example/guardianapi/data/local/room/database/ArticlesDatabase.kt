package com.example.guardianapi.data.local.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.guardianapi.data.local.room.dao.ArticlesDao
import com.example.guardianapi.data.local.room.entity.Articles

@Database(entities = [Articles::class], version = 1, exportSchema = true)
abstract class ArticlesDatabase : RoomDatabase() {

    abstract fun articlesDao() : ArticlesDao
//
//    companion object {
//        private const val DB_NAME = "app_db"
//
//        @Volatile
//        private var INSTANCE: ArticlesDatabase? = null
//        fun getInstance(context: Context): ArticlesDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    ArticlesDatabase::class.java,
//                    DB_NAME
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }

}
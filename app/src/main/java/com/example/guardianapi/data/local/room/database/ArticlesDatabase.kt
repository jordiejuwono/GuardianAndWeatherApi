package com.example.guardianapi.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.guardianapi.data.local.room.dao.ArticlesDao
import com.example.guardianapi.data.local.room.entity.Articles

@Database(entities = [Articles::class], version = 1, exportSchema = true)
abstract class ArticlesDatabase : RoomDatabase() {

    abstract fun articlesDao() : ArticlesDao

}
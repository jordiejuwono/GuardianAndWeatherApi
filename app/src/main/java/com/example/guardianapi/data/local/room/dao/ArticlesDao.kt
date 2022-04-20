package com.example.guardianapi.data.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.guardianapi.data.local.room.entity.Articles

@Dao
interface ArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticle(article: Articles): Long

    @Delete
    suspend fun deleteArticle(article: Articles): Int

    @Query("SELECT * FROM saved_articles ORDER BY id DESC")
    fun getAllSavedArticles(): LiveData<List<Articles>>
}
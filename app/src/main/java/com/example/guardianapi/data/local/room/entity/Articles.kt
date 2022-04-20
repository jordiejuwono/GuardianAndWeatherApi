package com.example.guardianapi.data.local.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(indices = [Index(value = ["webTitle"], unique = true)],tableName = "saved_articles")
data class Articles(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "webTitle")
    var webTitle: String?,
    @ColumnInfo(name = "webUrl")
    var webUrl: String?,
    @ColumnInfo(name = "articleDate")
    var articleDate: String?,
    @ColumnInfo(name = "sectionId")
    var sectionId: String?,
    @ColumnInfo(name = "sectionName")
    var sectionName: String?,
    @ColumnInfo(name = "image")
    var image: String?
) : Parcelable
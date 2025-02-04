package com.plcoding.goldchart.data.database.news

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class NewsEntity(
    @PrimaryKey
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "iconUrl")
    val iconUrl: String,
    @ColumnInfo(name = "dateStr")
    val dateStr: String,
)
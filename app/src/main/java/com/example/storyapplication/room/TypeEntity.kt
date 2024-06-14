package com.example.storyapplication.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "type_table")
data class TypeEntity(
    @PrimaryKey
    @ColumnInfo(name = "index")
    val index: Int,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
)


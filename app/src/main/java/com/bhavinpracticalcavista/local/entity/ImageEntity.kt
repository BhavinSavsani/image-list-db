package com.bhavinpracticalcavista.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey
    @ColumnInfo(name = "image_id")
    val imageId: String,
    @ColumnInfo(name = "comment")
    val comment: String
)
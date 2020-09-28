package com.bhavinpracticalcavista.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bhavinpracticalcavista.local.dao.ImagesDao
import com.bhavinpracticalcavista.local.entity.ImageEntity

@Database(entities = [ImageEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imagesDao(): ImagesDao
}
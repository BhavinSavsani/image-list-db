package com.bhavinpracticalcavista.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.bhavinpracticalcavista.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule() {

    private lateinit var appDatabase: AppDatabase

    @Singleton
    @Provides
    fun providesRoomDatabase(context: Context): AppDatabase {
        appDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "images"
        )
            .fallbackToDestructiveMigration()
            .build()
        return appDatabase
    }

    @Singleton
    @Provides
    fun providesImagesDao(appDatabase: AppDatabase) = appDatabase.imagesDao()
}
package com.bhavinpracticalcavista.di.modules

import android.app.Application
import androidx.room.Room
import com.bhavinpracticalcavista.local.AppDatabase
import com.bhavinpracticalcavista.local.repository.ImageRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImageModule {

    @Singleton
    @Provides
    fun providesImageRepository(appDatabase: AppDatabase): ImageRepository {
        return ImageRepository(appDatabase)
    }
}
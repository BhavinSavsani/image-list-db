package com.bhavinpracticalcavista.local.repository

import androidx.lifecycle.LiveData
import com.bhavinpracticalcavista.local.AppDatabase
import com.bhavinpracticalcavista.local.dao.ImagesDao
import com.bhavinpracticalcavista.local.entity.ImageEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageRepository(appDatabase: AppDatabase) {
    private var imagesDao: ImagesDao = appDatabase.imagesDao()

    fun insertComment(imageEntity: ImageEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            imagesDao.addComment(imageEntity)
        }
    }

    fun getComment(imageId: String): LiveData<ImageEntity?> {
        return imagesDao.getComment(imageId)
    }

}
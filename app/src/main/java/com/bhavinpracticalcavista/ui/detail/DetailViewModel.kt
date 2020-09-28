package com.bhavinpracticalcavista.ui.detail

import androidx.lifecycle.LiveData
import com.bhavinpracticalcavista.CavistaApp
import com.bhavinpracticalcavista.local.entity.ImageEntity
import com.bhavinpracticalcavista.local.repository.ImageRepository
import com.bhavinpracticalcavista.ui.base.BaseViewModel
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    app: CavistaApp,
    private val imageRepository: ImageRepository
) : BaseViewModel(app) {

    fun getAddedComment(imageId: String): LiveData<ImageEntity?> {
        return imageRepository.getComment(imageId)
    }

    fun addComment(imageEntity: ImageEntity) {
        return imageRepository.insertComment(imageEntity)
    }

}
package com.bhavinpracticalcavista.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bhavinpracticalcavista.local.entity.ImageEntity

@Dao
interface ImagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addComment(imageEntity: ImageEntity): Long

    @Query("SELECT * FROM images WHERE image_id == :imageId")
    fun getComment(imageId: String): LiveData<ImageEntity?>

}
package com.bhavinpracticalcavista.api

import com.bhavinpracticalcavista.repository.model.SearchResult
import com.bhavinpracticalcavista.repository.repo.BaseResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("/3/gallery/search/1")
    fun getImages(
        @Query("q") query: String,
        @Query("page") page: Int
    ): Flowable<BaseResponse<List<SearchResult>>>

}


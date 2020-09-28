package com.bhavinpracticalcavista.repository.repo

import androidx.lifecycle.MutableLiveData
import com.bhavinpracticalcavista.api.RequestState
import com.bhavinpracticalcavista.api.RetrofitService
import com.bhavinpracticalcavista.repository.model.SearchResult
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestRepository @Inject constructor(
    apiService: RetrofitService
) : BaseRepo(apiService) {

    fun getImages(
        query: String,
        page: Int,
        liveData: MutableLiveData<RequestState<List<SearchResult>>>,
        compositeDisposable: CompositeDisposable
    ) {
        apiService.getImages(query, page).callApi(liveData, compositeDisposable)
    }

}
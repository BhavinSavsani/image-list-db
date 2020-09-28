package com.bhavinpracticalcavista.ui.main

import androidx.lifecycle.MutableLiveData
import com.bhavinpracticalcavista.CavistaApp
import com.bhavinpracticalcavista.api.RequestState
import com.bhavinpracticalcavista.repository.model.SearchResult
import com.bhavinpracticalcavista.repository.repo.RequestRepository
import com.bhavinpracticalcavista.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    app: CavistaApp,
    private val requestRepository: RequestRepository
) : BaseViewModel(app) {

    val images = ArrayList<SearchResult.Image>()
    val mRequestData = MutableLiveData<RequestState<List<SearchResult>>>()
    private val compositeDisposable = CompositeDisposable()
    var setLoaded: Boolean = false
    var query: String? = null
    var pageNo: Int = 1

    fun getImages() {
        requestRepository.getImages(query!!, pageNo, mRequestData, compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
package com.bhavinpracticalcavista.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bhavinpracticalcavista.CavistaApp
import com.bhavinpracticalcavista.api.RequestState
import com.bhavinpracticalcavista.api.RetrofitService
import com.bhavinpracticalcavista.repository.model.SearchResult
import com.bhavinpracticalcavista.repository.repo.BaseResponse
import com.bhavinpracticalcavista.repository.repo.RequestRepository
import com.bhavinpracticalcavista.ui.base.BaseViewModel
import com.bhavinpracticalcavista.ui.paging.ImageDataSourceFactory
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    app: CavistaApp,
    private val retrofitService: RetrofitService
) : BaseViewModel(app) {

    val mRequestData = MutableLiveData<RequestState<List<SearchResult>>>()
    lateinit var imageData: LiveData<PagedList<SearchResult.Image>>
    private val compositeDisposable = CompositeDisposable()
    var query: String? = null
    private lateinit var imageDataSourceFactory: ImageDataSourceFactory
    private val pageSize = 25
    private val config = PagedList.Config.Builder()
        .setPageSize(pageSize)
        .setInitialLoadSizeHint(pageSize * 2)
        .setEnablePlaceholders(false)
        .build()

    fun initDataSource() {
        imageDataSourceFactory =
            ImageDataSourceFactory(retrofitService, compositeDisposable, query!!, mRequestData)
        imageData =
            LivePagedListBuilder<Int, SearchResult.Image>(imageDataSourceFactory, config).build()
    }

    fun retry() {
        if (::imageDataSourceFactory.isInitialized)
            imageDataSourceFactory.imageDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        if (::imageData.isInitialized)
            return imageData.value?.isEmpty() ?: true
        else
            return true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
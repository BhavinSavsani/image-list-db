package com.bhavinpracticalcavista.ui.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.bhavinpracticalcavista.api.InternetException
import com.bhavinpracticalcavista.api.RequestState
import com.bhavinpracticalcavista.api.RetrofitService
import com.bhavinpracticalcavista.repository.model.SearchResult
import com.bhavinpracticalcavista.repository.repo.BaseResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ImageDataSourceFactory(
    private val retrofitService: RetrofitService,
    private val compositeDisposable: CompositeDisposable,
    private val query: String,
    private val liveData: MutableLiveData<RequestState<List<SearchResult>>>
) : DataSource.Factory<Int, SearchResult.Image>() {

    val imageDataSourceLiveData = MutableLiveData<ImageDataSource>()

    override fun create(): DataSource<Int, SearchResult.Image> {
        val imageDataSource = ImageDataSource(retrofitService, compositeDisposable, query, liveData)
        imageDataSourceLiveData.postValue(imageDataSource)
        return imageDataSource
    }


}
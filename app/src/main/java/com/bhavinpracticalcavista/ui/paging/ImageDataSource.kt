package com.bhavinpracticalcavista.ui.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.bhavinpracticalcavista.api.*
import com.bhavinpracticalcavista.repository.model.SearchResult
import com.bhavinpracticalcavista.repository.repo.BaseResponse
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class ImageDataSource(
    private val retrofitService: RetrofitService,
    private val compositeDisposable: CompositeDisposable,
    private val query: String,
    private val liveData: MutableLiveData<RequestState<List<SearchResult>>>
) : PageKeyedDataSource<Int, SearchResult.Image>() {

    private var retryCompletable: Completable? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, SearchResult.Image>
    ) {
        liveData.postValue(RequestState.progress())
        compositeDisposable.add(
            retrofitService.getImages(query, 1).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        val arr = ArrayList<SearchResult.Image>()
                        it.data?.forEach {
                            it.images?.let { it1 -> arr.addAll(it1) }
                        }
                        callback.onResult(
                            arr,
                            null,
                            2
                        )
                        liveData.postValue(RequestState.success(it))
                    } else {
                        liveData.postValue(RequestState.error("Something went wrong"))
                    }
                }, {
                    if (it is InternetException) {
                        liveData.postValue(RequestState.internetError())
                    } else {
                        liveData.postValue(RequestState.error(it.localizedMessage))
                        setRetry(Action { loadInitial(params, callback) })
                    }
                })
        )

    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, SearchResult.Image>
    ) {
        liveData.postValue(RequestState.progress())
        compositeDisposable.add(
            retrofitService.getImages(query, params.key).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        val arr = ArrayList<SearchResult.Image>()
                        it.data?.forEach {
                            it.images?.let { it1 -> arr.addAll(it1) }
                        }
                        callback.onResult(
                            arr,
                            params.key + 1
                        )
                        liveData.postValue(RequestState.success(it))
                    } else {
                        liveData.postValue(RequestState.error("Something went wrong"))
                    }
                }, {
                    if (it is InternetException) {
                        liveData.postValue(RequestState.internetError())
                    } else {
                        liveData.postValue(RequestState.error(it.localizedMessage))
                        setRetry(Action { loadAfter(params, callback) })
                    }
                })
        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, SearchResult.Image>
    ) {
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

}
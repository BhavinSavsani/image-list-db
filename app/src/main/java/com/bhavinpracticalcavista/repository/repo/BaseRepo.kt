package com.bhavinpracticalcavista.repository.repo

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bhavinpracticalcavista.api.ErrorResponse
import com.bhavinpracticalcavista.api.InternetException
import com.bhavinpracticalcavista.api.RequestState
import com.bhavinpracticalcavista.api.RetrofitService
import com.bhavinpracticalcavista.repository.model.SearchResult
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject


abstract class BaseRepo constructor(
    protected val apiService: RetrofitService
) {
    //after adding paging library this class will not called
    @SuppressLint("CheckResult")
    protected fun <T> Flowable<BaseResponse<T>>.callApi(
        liveData: MutableLiveData<RequestState<T>>,
        compositeDisposable: CompositeDisposable
    ) {
        liveData.value = RequestState.progress()
        compositeDisposable.add(
            this.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.success) {
                        liveData.postValue(RequestState.success(it))
                    } else {
                        liveData.postValue(RequestState.error("something went wrong"))
                    }
                }, {
                    if (it is InternetException)
                        liveData.postValue(RequestState.internetError())
                    else
                        liveData.postValue(RequestState.error(it.localizedMessage))
                })
        )
    }

}
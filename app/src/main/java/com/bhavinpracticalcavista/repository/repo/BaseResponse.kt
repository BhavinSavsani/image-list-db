package com.bhavinpracticalcavista.repository.repo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BaseResponse<T> {
    @SerializedName("status")
    @Expose
    var status = 0
    @SerializedName("success")
    @Expose
    var success = false
    @SerializedName("data")
    @Expose
    var data: T? = null
}
package com.bhavinpracticalcavista.api

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    val error: String?,
    @SerializedName("request")
    val request: String?,
    @SerializedName("method")
    val method: String?
)
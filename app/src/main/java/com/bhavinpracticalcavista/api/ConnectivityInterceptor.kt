package com.bhavinpracticalcavista.api

import android.content.Context
import com.bhavinpracticalcavista.utils.Utility
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityInterceptor @Inject constructor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!Utility.isNetworkConnected(context)) {
            throw InternetException("No connectivity")
        } else {
            try {
                val request = chain.request()
                return chain.proceed(request)
            } catch (e: Exception) {
                e.printStackTrace()
                throw IOException("Failed to Connect")
            }
        }
    }

}
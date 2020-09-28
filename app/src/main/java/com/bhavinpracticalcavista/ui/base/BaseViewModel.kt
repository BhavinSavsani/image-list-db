package com.bhavinpracticalcavista.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bhavinpracticalcavista.CavistaApp
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(app: CavistaApp) : AndroidViewModel(app) {
}
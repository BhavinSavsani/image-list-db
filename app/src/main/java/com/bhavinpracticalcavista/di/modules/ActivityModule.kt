package com.bhavinpracticalcavista.di.modules

import com.bhavinpracticalcavista.ui.detail.DetailActivity
import com.bhavinpracticalcavista.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * All your Activities participating in DI would be listed here.
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailActivity(): DetailActivity

}

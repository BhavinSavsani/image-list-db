package com.bhavinpracticalcavista.di.components

import com.bhavinpracticalcavista.CavistaApp
import com.bhavinpracticalcavista.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityModule::class, ViewModelModule::class, RoomModule::class, ImageModule::class])
interface AppComponent : AndroidInjector<CavistaApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: CavistaApp): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: CavistaApp)

}
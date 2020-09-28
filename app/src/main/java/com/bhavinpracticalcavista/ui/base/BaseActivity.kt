package com.bhavinpracticalcavista.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.bhavinpracticalcavista.R
import com.bhavinpracticalcavista.di.base.ViewModelFactory
import com.google.android.material.appbar.AppBarLayout
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    fun initializeToolbar(title: String) {
        val toolbarView = findViewById<Toolbar>(R.id.toolbar)
        findViewById<AppBarLayout>(R.id.appbar).bringToFront()
        setSupportActionBar(toolbarView)
        val toolbar = supportActionBar
        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true)
            toolbar.title = title
            toolbarView.setNavigationOnClickListener { v -> onBackPressed() }
        }
    }
}
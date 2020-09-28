package com.bhavinpracticalcavista.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bhavinpracticalcavista.R
import com.bhavinpracticalcavista.api.RequestState
import com.bhavinpracticalcavista.repository.model.SearchResult
import com.bhavinpracticalcavista.ui.adapter.ImagePageListAdapter
import com.bhavinpracticalcavista.ui.base.BaseActivity
import com.bhavinpracticalcavista.ui.detail.DetailActivity
import com.bhavinpracticalcavista.utils.SnackBarHelper
import com.bhavinpracticalcavista.utils.extensions.getViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : BaseActivity(), ImagePageListAdapter.OnImageClickListener {

    private lateinit var adapter: ImagePageListAdapter
    private val viewModel by lazy {
        getViewModel<MainViewModel>()
    }

    //to show 5 column in landscape mode and 3 columns in portrait mode
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rv_images.layoutManager = GridLayoutManager(this, 5)
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            rv_images.layoutManager = GridLayoutManager(this, 3)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeData()
    }

    @SuppressLint("CheckResult")
    private fun observeData() {
        RxTextView.textChanges(edt_search)
            .debounce(250, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .skip(1)
            .subscribe {
                viewModel.query = it.toString()
                setRecyclerView()
            }

        txt_error.setOnClickListener { viewModel.retry() }

        viewModel.mRequestData.observe(this, Observer {
            txt_error.visibility =
                if (viewModel.listIsEmpty() && it.status == RequestState.State.FAILURE) View.VISIBLE else View.GONE
            progress.visibility =
                if (adapter.itemCount == 0 && it.status == RequestState.State.PROGRESS) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                adapter.setState(it.status)
            }
            if (it.status == RequestState.State.INTERNET_ERROR) {
                SnackBarHelper.showError(
                    findViewById(android.R.id.content),
                    "Internet not available"
                )
            } else if (it.status == RequestState.State.FAILURE) {
                SnackBarHelper.showError(
                    findViewById(android.R.id.content),
                    it.error
                )
            }
        })
    }

    private fun setRecyclerView() {
        rv_images.layoutManager = GridLayoutManager(this, 3)
        viewModel.initDataSource()
        adapter = ImagePageListAdapter(this)
        rv_images.adapter = adapter
        viewModel.imageData.observe(this, Observer {
            adapter.submitList(it)
        })

    }

    override fun onItemClick(pos: Int, item: SearchResult.Image?) {
        startActivity(Intent(this, DetailActivity::class.java).apply {
            putExtra("image", item)
        })
    }
}
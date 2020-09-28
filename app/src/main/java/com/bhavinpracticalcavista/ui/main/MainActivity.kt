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
import com.bhavinpracticalcavista.ui.adapter.ImageListAdapter
import com.bhavinpracticalcavista.ui.base.BaseActivity
import com.bhavinpracticalcavista.ui.detail.DetailActivity
import com.bhavinpracticalcavista.utils.EndlessRecyclerViewScrollListener
import com.bhavinpracticalcavista.utils.SnackBarHelper
import com.bhavinpracticalcavista.utils.extensions.getViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : BaseActivity(), ImageListAdapter.OnImageClickListener {

    private lateinit var adapter: ImageListAdapter
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private val imageListType = 1
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
        setRecyclerView()
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
                viewModel.setLoaded = false
                viewModel.pageNo = 1
                viewModel.images.clear()
                adapter.addItems(viewModel.images)
                searchImages()
            }
        viewModel.mRequestData.observe(this, Observer {
            if (it.status == RequestState.State.PROGRESS) {
                if (adapter.itemCount == 0)
                    progress.visibility = View.VISIBLE
            } else {
                progress.visibility = View.GONE
                scrollListener.setLoaded()
                if (it.status == RequestState.State.INTERNET_ERROR) {
                    SnackBarHelper.showError(
                        findViewById(android.R.id.content),
                        "Internet not available"
                    )
                } else if (it.status == RequestState.State.API_ERROR) {
                    SnackBarHelper.showError(
                        findViewById(android.R.id.content),
                        "Something went wrong in api"
                    )
                } else if (it.status == RequestState.State.FAILURE) {
                    SnackBarHelper.showError(
                        findViewById(android.R.id.content),
                        it.error
                    )
                } else if (it.status == RequestState.State.SUCCESS) {
                    it.data?.data?.apply {
                        if (isNotEmpty()) {
                            this.forEach {
                                it.images?.let { it1 -> viewModel.images.addAll(it1) }
                            }
                            adapter.addItems(viewModel.images)
                        } else {
                            viewModel.setLoaded = true
                            if (adapter.itemCount > 0) {
                                adapter.removeProgress()
                            }
                        }
                    }
                }
            }
        })
    }

    private fun setRecyclerView() {
        rv_images.layoutManager = GridLayoutManager(this, 3)
        adapter = ImageListAdapter(imageListType, this)
        rv_images.adapter = adapter
        scrollListener = object : EndlessRecyclerViewScrollListener() {
            override fun onLoadMore() {
                viewModel.pageNo++
                searchImages()
            }
        }
        rv_images.addOnScrollListener(scrollListener)
    }

    private fun searchImages() {
        if (!viewModel.setLoaded) {
            viewModel.getImages()
        }
    }

    override fun onItemClick(pos: Int, item: SearchResult.Image?) {
        startActivity(Intent(this, DetailActivity::class.java).apply {
            putExtra("image", item)
        })
    }
}
package com.bhavinpracticalcavista.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bhavinpracticalcavista.R


abstract class BasePageAdapter<T>(diffUtil: DiffUtil.ItemCallback<PageItem<T>>) :
    ListAdapter<BasePageAdapter.PageItem<T>, RecyclerView.ViewHolder>(diffUtil) {

    private val VIEW_TYPE_LOADING = -2
    protected val VIEW_TYPE_DATA = -1
    protected var currentList = ArrayList<PageItem<T>>()

    fun addItems(mItems: ArrayList<T>) {
        val wrapperList = ArrayList<T>()
        wrapperList.addAll(mItems)
        val pageList = ArrayList<PageItem<T>>()
        wrapperList.mapTo(pageList, {
            PageItem(VIEW_TYPE_DATA, it)
        })
        if (wrapperList.size > 0)
            pageList.add(PageItem(VIEW_TYPE_LOADING, null))
        submitList(pageList)
        currentList = pageList
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).type) {
            VIEW_TYPE_LOADING -> VIEW_TYPE_LOADING
            else -> getDataItemViewType(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LOADING -> ProgressHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_progress, parent, false)
            )
            else -> onCreateDataViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is ProgressHolder) {
            onBindDataViewHolder(holder, position)
        }
    }

    abstract fun getDataItemViewType(position: Int): Int

    abstract fun onCreateDataViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun onBindDataViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    fun removeProgress() {
        if (currentList.last().type == VIEW_TYPE_LOADING) {
            val tempList = ArrayList<PageItem<T>>().apply {
                this.addAll(currentList)
                remove(currentList.last())
            }
            submitList(tempList)
            currentList = tempList
        }
    }

    data class PageItem<T>(val type: Int, val data: T?)
    class ProgressHolder(view: View) : RecyclerView.ViewHolder(view)

}
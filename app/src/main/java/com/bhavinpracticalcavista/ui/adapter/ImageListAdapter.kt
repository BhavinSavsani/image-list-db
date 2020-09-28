package com.bhavinpracticalcavista.ui.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bhavinpracticalcavista.R
import com.bhavinpracticalcavista.repository.model.SearchResult
import com.bhavinpracticalcavista.ui.base.BasePageAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_image.view.*

class ImageListAdapter(private var type: Int, private val mListener: OnImageClickListener) :
    BasePageAdapter<SearchResult.Image>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<PageItem<SearchResult.Image>>() {
            override fun areItemsTheSame(
                oldItem: PageItem<SearchResult.Image>,
                newItem: PageItem<SearchResult.Image>
            ): Boolean {
                return oldItem.type == newItem.type && oldItem.data?.id == newItem.data?.id
            }

            override fun areContentsTheSame(
                oldItem: PageItem<SearchResult.Image>,
                newItem: PageItem<SearchResult.Image>
            ): Boolean {
                return oldItem.type == newItem.type
                        && oldItem.data == newItem.data
            }

        }
    }


    override fun onCreateDataViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image, parent, false)
        )
    }


    inner class ImageHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                mListener.onItemClick(
                    adapterPosition,
                    currentList[adapterPosition].data
                )
            }
        }

        fun bindTo(image: SearchResult.Image) {
            view.context.apply {
                Glide.with(this)
                    .load(image.link)
                    .placeholder(R.drawable.gallery_place)
                    .transform(CenterCrop(), RoundedCorners(15))
                    .error(R.drawable.gallery_place)
                    .into(view.image)
            }

        }
    }


    override fun onBindDataViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ImageHolder) {
            getItem(position).data?.let { it -> holder.bindTo(it) }
        }
    }


    interface OnImageClickListener {
        fun onItemClick(pos: Int, item: SearchResult.Image?)
    }

    override fun getDataItemViewType(position: Int): Int {
        return type
    }
}
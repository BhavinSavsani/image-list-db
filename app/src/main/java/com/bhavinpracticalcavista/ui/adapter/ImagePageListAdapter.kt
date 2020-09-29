package com.bhavinpracticalcavista.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bhavinpracticalcavista.R
import com.bhavinpracticalcavista.api.RequestState
import com.bhavinpracticalcavista.repository.model.SearchResult
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.item_image.view.*
import kotlinx.android.synthetic.main.item_progress.view.*

class ImagePageListAdapter(private val mListener: OnImageClickListener) :
    PagedListAdapter<SearchResult.Image, RecyclerView.ViewHolder>(DiffCallback) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2
    private var state = RequestState.State.PROGRESS

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) {
            ImageViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_image, parent, false)
            )
        } else {
            ListFooterViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_progress, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE)
            getItem(position)?.let { (holder as ImageViewHolder).bind(it) }
        else (holder as ListFooterViewHolder).bind(state)
    }


    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<SearchResult.Image>() {
            override fun areItemsTheSame(
                oldItem: SearchResult.Image,
                newItem: SearchResult.Image
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: SearchResult.Image,
                newItem: SearchResult.Image
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == RequestState.State.PROGRESS || state == RequestState.State.FAILURE)
    }


    interface OnImageClickListener {
        fun onItemClick(pos: Int, item: SearchResult.Image?)
    }


    inner class ImageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                mListener.onItemClick(
                    adapterPosition,
                    currentList?.get(adapterPosition)
                )
            }
        }

        fun bind(image: SearchResult.Image) {
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

    inner class ListFooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(status: RequestState.State) {
            itemView.progress.visibility =
                if (status == RequestState.State.PROGRESS) VISIBLE else View.INVISIBLE
        }

    }

    fun setState(state: RequestState.State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

}
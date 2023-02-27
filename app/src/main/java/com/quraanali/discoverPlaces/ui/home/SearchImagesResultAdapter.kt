package com.quraanali.discoverPlaces.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.quraanali.discoverPlaces.data.home.models.SearchImagesResponseModel
import com.quraanali.discoverPlaces.databinding.ViewItemSearchImageBinding
import com.quraanali.discoverPlaces.ui.base.BaseAdapter
import com.quraanali.discoverPlaces.ui.base.BaseViewHolder

class SearchImagesResultAdapter :
    BaseAdapter<SearchImagesResponseModel, BaseViewHolder<SearchImagesResponseModel>>() {

    override fun getViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<SearchImagesResponseModel> {
        return ViewHolder(
            ViewItemSearchImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class ViewHolder(binding: ViewItemSearchImageBinding) :
        BaseViewHolder<SearchImagesResponseModel>(binding) {
        override fun bind(position: Int, item: SearchImagesResponseModel?) =
            bind<ViewItemSearchImageBinding> {
                item?.let {
                    this.imageUrl = it.urls.thumb
                }
            }
    }
}
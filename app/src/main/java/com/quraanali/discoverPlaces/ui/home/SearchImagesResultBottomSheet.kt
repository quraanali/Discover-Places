package com.quraanali.discoverPlaces.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.quraanali.discoverPlaces.R
import com.quraanali.discoverPlaces.data.home.models.SearchImagesResponseModel
import com.quraanali.discoverPlaces.databinding.BottomSheetSearchImagesResultBinding
import com.quraanali.discoverPlaces.ui.base.BaseAdapter
import com.quraanali.discoverPlaces.ui.base.BaseBottomSheet
import com.stfalcon.imageviewer.StfalconImageViewer


class SearchImagesResultBottomSheet(
    private val searchImagesResultBottomSheetUiModel: SearchImagesResultBottomSheetUiModel,
) : BaseBottomSheet() {
    private lateinit var binding: BottomSheetSearchImagesResultBinding
    private val adapter = SearchImagesResultAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetSearchImagesResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeImg.setOnClickListener {
            this@SearchImagesResultBottomSheet.dismiss()
        }

        binding.recyclerView.adapter = adapter
        searchImagesResultBottomSheetUiModel.searchImagesResponseModel?.let { adapter.setItems(it) }

        binding.title1 = searchImagesResultBottomSheetUiModel.title1
        binding.title2 = searchImagesResultBottomSheetUiModel.title2
        binding.executePendingBindings()


        adapter.setOnItemClickListener(object :
            BaseAdapter.OnItemClickListener<SearchImagesResponseModel> {
            override fun onItemClicked(
                view: View,
                item: SearchImagesResponseModel?,
                position: Int
            ) {
                showImageViewer(item?.urls?.regular)
            }
        })
    }

    companion object {
        const val TAG = "SearchImagesResultBottomSheet"

        @JvmStatic
        fun newInstance(
            searchImagesResultBottomSheetUiModel: SearchImagesResultBottomSheetUiModel,
        ) =
            SearchImagesResultBottomSheet(searchImagesResultBottomSheetUiModel)
    }


    override fun getViewBinding(): View? {
        return binding.root
    }


    override fun getProgressView(): View? {
        return binding.progress.root
    }

    fun showImageViewer(imageUrl: String?) {
        if (imageUrl == null) return

        StfalconImageViewer.Builder(context, listOf(imageUrl)) { view, image ->
            Glide.with(requireContext()).load(image).placeholder(R.mipmap.ic_wait).into(view)
        }.show()
    }
}
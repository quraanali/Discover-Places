package com.quraanali.discoverPlaces.ui.home

import com.quraanali.discoverPlaces.data.home.models.SearchImagesResponseModel

data class HomeUiState(
    val toastMessage: String? = null,
    val isLoading: Boolean = false,
    val searchImagesResultBottomSheetUiModel: SearchImagesResultBottomSheetUiModel? = null

)

data class SearchImagesResultBottomSheetUiModel(
    val title1: String = "",
    val title2: String = "",
    val searchImagesResponseModel: MutableList<SearchImagesResponseModel>? = null,
)
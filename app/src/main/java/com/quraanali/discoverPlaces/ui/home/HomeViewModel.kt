package com.quraanali.discoverPlaces.ui.home

import android.app.Application
import android.location.Address
import androidx.lifecycle.viewModelScope
import com.quraanali.discoverPlaces.data.source.remote.DefaultResponse
import com.quraanali.discoverPlaces.domain.home.GetImagesByAreaNameUseCase
import com.quraanali.discoverPlaces.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    val getImagesByAreaNameUseCase: GetImagesByAreaNameUseCase
) : BaseViewModel(application) {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun init() {
        viewModelScope.launch {

        }
    }

    fun getImageForLocation(address: Address?) {
        if (address == null) return
        _uiState.update {
            it.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch {
            when (val result = getImagesByAreaNameUseCase(address.adminArea)) {
                is DefaultResponse.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            searchImagesResultBottomSheetUiModel = SearchImagesResultBottomSheetUiModel(
                                searchImagesResponseModel = result.body.data,
                                title1 = address.countryName,
                                title2 = address.adminArea
                            )
                        )
                    }
                }

                is DefaultResponse.Fail -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            toastMessage = result.error.message
                        )
                    }
                }
            }
        }

    }

    fun clearShowSearchImagesResultBottomSheet() {
        _uiState.update {
            it.copy(
                searchImagesResultBottomSheetUiModel = null
            )
        }
    }
}

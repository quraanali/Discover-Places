package com.quraanali.discoverPlaces.domain.home

import com.quraanali.discoverPlaces.data.source.remote.DefaultResponse
import com.quraanali.discoverPlaces.data.source.remote.GenericResponse
import com.quraanali.discoverPlaces.data.home.HomeDataSource
import com.quraanali.discoverPlaces.data.home.models.SearchImagesResponseModel
import javax.inject.Inject

class GetImagesByAreaNameUseCase @Inject constructor(
    private val homerDataSource: HomeDataSource,
) {
    suspend operator fun invoke(area: String): DefaultResponse<GenericResponse<MutableList<SearchImagesResponseModel>>> {
        return homerDataSource.getSearchImages(area)
    }
}
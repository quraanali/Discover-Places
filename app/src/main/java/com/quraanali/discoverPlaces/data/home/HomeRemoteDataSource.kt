package com.quraanali.discoverPlaces.data.home

import com.quraanali.discoverPlaces.data.source.remote.DefaultResponse
import com.quraanali.discoverPlaces.data.source.remote.GenericResponse
import com.quraanali.discoverPlaces.data.home.models.SearchImagesResponseModel


interface HomeRemoteDataSource {
    suspend fun getSearchImages(query: String): DefaultResponse<GenericResponse<MutableList<SearchImagesResponseModel>>>
}
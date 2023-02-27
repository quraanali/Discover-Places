package com.quraanali.discoverPlaces.data.home

import com.quraanali.discoverPlaces.data.source.remote.ApiEndPoints
import com.quraanali.discoverPlaces.data.source.remote.DefaultResponse
import com.quraanali.discoverPlaces.data.source.remote.GenericResponse
import com.quraanali.discoverPlaces.data.home.models.SearchImagesResponseModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRemoteDataSourceImpl @Inject constructor(
    private val apiEndPoints: ApiEndPoints
) : HomeRemoteDataSource {
    override suspend fun getSearchImages(query: String): DefaultResponse<GenericResponse<MutableList<SearchImagesResponseModel>>> {
        return try {
            DefaultResponse.create(apiEndPoints.getSearchImages(query))
        } catch (e: Exception) {
            DefaultResponse.create(e)
        }
    }
}
package com.quraanali.discoverPlaces.data.home

import com.quraanali.discoverPlaces.data.source.remote.DefaultResponse
import com.quraanali.discoverPlaces.data.source.remote.GenericResponse
import com.quraanali.discoverPlaces.data.home.models.SearchImagesResponseModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface HomeDataSource : HomeRemoteDataSource, HomeLocalDataSource

@Singleton
class HomeRepository @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val homeLocalDataSource: HomeLocalDataSource,
    private val homeRemoteDataSource: HomeRemoteDataSource,
) : HomeDataSource {
    override suspend fun getSearchImages(query: String): DefaultResponse<GenericResponse<MutableList<SearchImagesResponseModel>>> {
        return withContext(dispatcher) {
            val response = homeRemoteDataSource.getSearchImages(query)
            response
        }
    }
}
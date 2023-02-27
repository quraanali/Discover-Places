package com.quraanali.discoverPlaces.data.source.remote

import com.quraanali.discoverPlaces.data.home.models.SearchImagesResponseModel
import retrofit2.Response
import retrofit2.http.*

interface ApiEndPoints {

    @GET("search/photos")
    suspend fun getSearchImages(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 50,
    ): Response<GenericResponse<MutableList<SearchImagesResponseModel>>>

}
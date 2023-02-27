package com.quraanali.discoverPlaces.data.home.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchImagesResponseModel(
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "urls")
    val urls: Urls,
)

@JsonClass(generateAdapter = true)
data class Urls(
    @Json(name = "full")
    val full: String,
    @Json(name = "raw")
    val raw: String,
    @Json(name = "regular")
    val regular: String,
    @Json(name = "small")
    val small: String,
    @Json(name = "small_s3")
    val smallS3: String,
    @Json(name = "thumb")
    val thumb: String
)
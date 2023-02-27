package com.quraanali.discoverPlaces.data.source.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MetaCode(
    @Json(name = "code")
    val code: Int?,
    @Json(name = "error_message")
    val message: String?,
    @Json(name = "errors") val errors: Any?
)

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "meta") val meta: MetaCode?
)


@JsonClass(generateAdapter = true)
data class GenericResponse<T>(
    @Json(name = "results")
    val data: T,
    @Json(name = "total_pages")
    val totalPages : Int,
    @Json(name = "total")
    val total : Int,
){

}

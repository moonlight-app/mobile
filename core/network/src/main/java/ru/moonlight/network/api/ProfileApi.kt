package ru.moonlight.network.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT
import ru.moonlight.network.model.ProfileResponse

internal interface ProfileApi {

    @GET("/api/user")
    suspend fun getProfile(): Response<ProfileResponse>

    @FormUrlEncoded
    @PATCH("/api/user")
    suspend fun updateProfile(
        @Field("name") name: String?,
        @Field("sex") sex: String?,
        @Field("birthDate") birthDate: String?,
    ): Response<ResponseBody>

    @FormUrlEncoded
    @PUT("/api/user/password")
    suspend fun changePassword(
        @Field("currentPassword") currentPassword: String,
        @Field("newPassword") newPassword: String,
    ): Response<ResponseBody>

    @DELETE("/api/user")
    suspend fun deleteProfile(): Response<ResponseBody>

}
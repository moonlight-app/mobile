package ru.moonlight.network.impl.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import ru.moonlight.network.api.model.auth.LoginResponse

internal interface AuthApi {

    @FormUrlEncoded
    @POST("/auth/sign-in")
    suspend fun signIn(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST("/auth/sign-up/email-code")
    suspend fun requestCode(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("renew") renew: Boolean? = null,
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("/auth/sign-up/confirm-email")
    suspend fun confirmCode(
        @Header("X-Proof-Key") key: String,
        @Field("email") email: String,
        @Field("code") code: String,
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("/auth/sign-up/complete")
    suspend fun complete(
        @Header("X-Proof-Key") key: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("name") name: String,
        @Field("birthDate") birthDate: String,
        @Field("sex") sex: String,
    ): Response<LoginResponse>


    @POST("/auth/token/refresh")
    suspend fun refreshTokens(
        @Header("Authorization") accessToken: String,
        @Query("refresh_token") refreshToken: String,
    ): Response<LoginResponse>

}
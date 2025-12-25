package com.example.mess_test.network

import com.example.mess_test.model.User
import retrofit2.http.*

data class RegisterRequest(
    val username: String,
    val password: String
)

data class RegisterResponse(
    val userId: String,
    val token: String
)

interface ApiService {

    @POST("register")
    suspend fun register(@Body body: RegisterRequest): RegisterResponse

    @GET("users")
    suspend fun getUsers(): List<User>
}

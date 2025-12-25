package com.example.mess_test.network

import com.example.mess_test.model.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

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
    suspend fun register(
        @Body body: RegisterRequest
    ): RegisterResponse

    @GET("users")
    suspend fun getUsers(): List<User>
}

object ApiProvider {

    private const val BASE_URL = "https://to-do-0bm7.onrender.com/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

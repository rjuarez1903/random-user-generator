package app.rodrigojuarez.dev.retrofitpractice.models

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RandomUserApiService {
    @GET("api")
    suspend fun getUser(): UserResponse
}

object RetrofitClient {
    val randomUserService: RandomUserApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RandomUserApiService::class.java)
    }
}
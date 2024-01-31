package app.rodrigojuarez.dev.retrofitpractice.repositories

import android.util.Log
import app.rodrigojuarez.dev.retrofitpractice.models.RetrofitClient
import app.rodrigojuarez.dev.retrofitpractice.models.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {
    suspend fun fetchUser(): Result<UserResponse> {
        return withContext(Dispatchers.IO) {
            try {
                Result.success(RetrofitClient.randomUserService.getUser())
            } catch (e: Exception) {
                Log.d("Retrofit", e.message ?: "An error occurred")
                Result.failure(e)
            }
        }
    }
}

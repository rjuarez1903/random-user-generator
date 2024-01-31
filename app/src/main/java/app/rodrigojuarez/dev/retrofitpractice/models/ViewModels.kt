package app.rodrigojuarez.dev.retrofitpractice.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigojuarez.dev.retrofitpractice.repositories.UserRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository()

    val state = MutableLiveData<UserState>()

    fun getUser() {
        state.value = UserState(isLoading = true)
        viewModelScope.launch {
            val result = userRepository.fetchUser()
            state.value = if (result.isSuccess) {
                UserState(user = result.getOrNull())
            } else {
                UserState(errorMessage = "An error occurred, sorry! Please try again!")
            }
        }
    }

    data class UserState(
        val isLoading: Boolean = false,
        val user: UserResponse? = null,
        val errorMessage: String = ""
    )
}
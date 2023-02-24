package dev.outup.coffeeapp.viewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.outup.coffeeapp.domain.usecase.UserService
import dev.outup.coffeeapp.infrastructure.repository.UserRepositoryImpl
import kotlinx.coroutines.*

class LoginViewModel : ViewModel() {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)
    private val auth = Firebase.auth

    private val userService = UserService(UserRepositoryImpl)

    var email = ""
    var password = ""

    fun login(): Boolean {
        runBlocking {
            userService.login(email, password)
        }
        val result = auth.currentUser != null
        Log.d(TAG, "Authentication result: $result")
        return result
    }

}
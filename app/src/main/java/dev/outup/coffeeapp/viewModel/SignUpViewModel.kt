package dev.outup.coffeeapp.viewModel

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.outup.coffeeapp.domain.usecase.UserService
import dev.outup.coffeeapp.infrastructure.repository.UserRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async

class SignUpViewModel {
    private val auth = Firebase.auth
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    private val userService = UserService(UserRepositoryImpl)

    var userName = ""
    var email = ""
    var password = ""

    fun signUp(): Boolean {
        var result: Boolean = false
        scope.async {
            result = userService.signUp(userName, email, password)
        }
        return result
    }
}
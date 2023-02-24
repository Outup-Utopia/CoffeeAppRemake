package dev.outup.coffeeapp.domain.usecase

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.outup.coffeeapp.domain.model.User
import dev.outup.coffeeapp.domain.repository.UserRepository
import kotlinx.coroutines.tasks.await

class UserService(private val userRepository: UserRepository) {
    // Initialize Firebase Auth
    private val auth = Firebase.auth
    suspend fun login(email: String, password: String): Unit {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success")
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
            }
        }.await()
    }

    suspend fun signUp(userName: String, email: String, password: String): Boolean {
        val user = auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success")
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
            }
        }.await().user
        return if (user == null) {
            false
        } else {
            userRepository.save(User(user.uid, userName))
            true
        }
    }

    suspend fun getCurrentUserName(): String? {
        Log.d(TAG, "UserService::getCurrentUserName start.")
        val uid: String? = auth.currentUser?.uid
        return if (uid == null) {
            null
        } else {
            userRepository.loadById(uid)?.userName
        }
    }
}
package dev.outup.coffeeapp.viewModel

import android.util.Log
import android.content.ContentValues.TAG
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dev.outup.coffeeapp.domain.model.Content
import dev.outup.coffeeapp.domain.usecase.ContentService
import dev.outup.coffeeapp.infrastructure.CloseableCoroutineScope
import dev.outup.coffeeapp.infrastructure.repository.CoffeeRepositoryImpl
import dev.outup.coffeeapp.infrastructure.repository.ContentRepositoryImpl
import kotlinx.coroutines.*

class ContentViewModel(val viewModelScope: CloseableCoroutineScope = CloseableCoroutineScope()) :
    ViewModel(viewModelScope) {
    val contentsLiveData by lazy { MutableLiveData(listOf<Content>()) }
    val auth = Firebase.auth
    val storage = Firebase.storage

    private val contentService = ContentService(ContentRepositoryImpl, CoffeeRepositoryImpl)

    fun getTimeline(): Unit {
        viewModelScope.async {
            val contents = contentService.getTimeline(auth.currentUser!!.uid)
            Log.d(TAG, "Timeline loaded: ")
            contents.forEach { content ->
                Log.d(TAG, content.toString())
            }
            contentsLiveData.postValue(contents)
        }
    }
}
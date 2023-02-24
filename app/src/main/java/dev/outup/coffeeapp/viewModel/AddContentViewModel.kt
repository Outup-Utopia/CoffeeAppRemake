package dev.outup.coffeeapp.viewModel

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dev.outup.coffeeapp.domain.enums.CoffeeSize
import dev.outup.coffeeapp.domain.model.Content
import dev.outup.coffeeapp.domain.usecase.ContentService
import dev.outup.coffeeapp.infrastructure.CloseableCoroutineScope
import dev.outup.coffeeapp.infrastructure.repository.CoffeeRepositoryImpl
import dev.outup.coffeeapp.infrastructure.repository.ContentRepositoryImpl
import kotlinx.coroutines.async
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class AddContentViewModel(val viewModelScope: CloseableCoroutineScope = CloseableCoroutineScope()) :
    ViewModel(viewModelScope) {
    val contentsLiveData by lazy { MutableLiveData(listOf<Content>()) }
    val auth = Firebase.auth
    val storage = Firebase.storage

    var title: String? = null
    var itemName: String? = null
    var coffeeSize: CoffeeSize? = null
    val coffeeOption: HashMap<String, String> = hashMapOf()

    private val contentService = ContentService(ContentRepositoryImpl, CoffeeRepositoryImpl)

    fun addContent(): Unit {
        viewModelScope.async {
            val contents = contentService.getTimeline(auth.currentUser!!.uid)
            Log.d(ContentValues.TAG, "Timeline loaded: ")
            contents.forEach { content ->
                Log.d(ContentValues.TAG, content.toString())
            }
            contentsLiveData.postValue(contents)
        }
    }

    fun getImageUri(context: Context): Uri {
        val imagePath = File(context.cacheDir, "images")
        imagePath.mkdirs()

        val date = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val file = File(imagePath, "$date.jpg")
        return FileProvider.getUriForFile(
            context,
            context.packageName + ".fileprovider",
            file
        )
    }
}
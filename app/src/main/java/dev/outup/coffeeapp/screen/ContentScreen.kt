package dev.outup.coffeeapp.screen

import android.annotation.SuppressLint
import android.util.Log
import android.content.ContentValues.TAG
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.outup.coffeeapp.domain.model.Content
import dev.outup.coffeeapp.ui.theme.Purple120
import dev.outup.coffeeapp.viewModel.ContentViewModel
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentScreen(handleOnCreate: () -> Unit, handleAddButton: () -> Unit) {
    val viewModel = ContentViewModel()
    // ログインできていなかった時の処理
    if (viewModel.auth.currentUser == null) {
        handleOnCreate()
    }
    Scaffold(
        floatingActionButton = { AddContentButton(handleAddButton) }
    ) {
        ContentList(viewModel)
    }
}


@Composable
fun ContentList(viewModel: ContentViewModel) {
    Log.d(TAG, "ContentScreen::ContentList render start.")
    val contents = viewModel.contentsLiveData.observeAsState()
    viewModel.getTimeline()
    Column(
        Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        contents.value?.map { content ->
            ContentCard(viewModel, content)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentCard(viewModel: ContentViewModel, content: Content) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { }
            .border(1.dp, Color.Black, RoundedCornerShape(10.dp)),
    ) {
        Column(
        ) {
            content.imageLocation?.let {
                AsyncImage(
                    model = content.imageLocation,
                    contentDescription = "Picture that was added to content <${content.title}> by user <${content.userId}>.",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Text(
            modifier = Modifier.fillMaxWidth().background(Color.White).padding(15.dp),
            fontWeight = FontWeight.W900,
            text = content.title ?: "",
        )
        Text(
            modifier = Modifier.fillMaxWidth().background(Color.White).padding(15.dp),
            fontWeight = FontWeight.W400,
            text = content.coffee?.description() ?: ""
        )

        Box(
            modifier = Modifier.fillMaxWidth().background(Color.White),
        ) {
            val date = content.createdAt?.toDate()
            if (date != null) {
                Text(
                    text = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.JAPANESE).format(date),
                    textAlign = TextAlign.Right,
                    modifier = Modifier.align(Alignment.BottomEnd).padding(5.dp)
                )
            }
        }
    }
}

@Composable
fun AddContentButton(handleAddButton: () -> Unit) {
    FloatingActionButton(
        onClick = handleAddButton,
        containerColor = Purple120
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "add_new_contents",
        )
    }
}


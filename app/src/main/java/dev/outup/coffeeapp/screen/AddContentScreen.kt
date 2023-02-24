package dev.outup.coffeeapp.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.core.content.FileProvider
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dev.outup.coffeeapp.domain.enums.CoffeeSize
import dev.outup.coffeeapp.domain.model.option.Amount
import dev.outup.coffeeapp.domain.model.option.AmountMutableOption
import dev.outup.coffeeapp.domain.model.option.AppendableOption
import dev.outup.coffeeapp.ui.theme.Purple80
import dev.outup.coffeeapp.viewModel.AddContentViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddContentScreen() {
    val viewModel = AddContentViewModel()
    Box {
        Surface(
            color = Color.White,
            modifier = Modifier.height(600.dp).fillMaxWidth(),
        ) {
            Column(
                Modifier.fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                UploadImageArea()
                Spacer(modifier = Modifier.padding(10.dp))
                TitleInputField(viewModel)
                Spacer(modifier = Modifier.padding(10.dp))
                ItemNameInputField(viewModel)
                Spacer(modifier = Modifier.padding(10.dp))
                CoffeeSizeDropDown(viewModel)
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "<- OPTION ->",
                    textAlign = TextAlign.Center
                )
                CoffeeOptionArea(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun UploadImageArea() {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    val context = LocalContext.current

    /**
     * カメラで撮影した写真を保存する一時的なファイルのURI
     * 注意：アクティビティが破棄されるとuriの値を失う。対策が必要。
     */
    var uri: Uri? = null

    /**
     * 表示する画像ファイルのURI
     */
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    /**
     * 写真を撮影するか画像を選択する
     */
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                imageUri = result.data?.data ?: uri
            }
        }
    )

    Column(
    ) {
        Box(
            modifier = Modifier.border(
                width = 2.dp,
                color = Color.DarkGray,
                shape = RoundedCornerShape(20.dp)
            )
        ) {
            if (imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Upload Image",
                    modifier = Modifier.fillMaxWidth().height(300.dp)
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            Button(
                modifier = Modifier.width(300.dp).align(Alignment.Center),
                onClick = {
                    if (cameraPermissionState.status.isGranted) {
                        // カメラの権限が付与されているとき
                        val tmpUri = getImageUri(context = context)
                        uri = tmpUri
                        launcher.launch(createChooser(tmpUri))
                    } else {
                        // カメラの権限を要求する
                        cameraPermissionState.launchPermissionRequest()
                    }
                }) {
                Text(text = "写真を撮影もしくは選択")
            }
        }
    }
}

@Composable
fun TitleInputField(viewModel: AddContentViewModel) {
    val title = remember { mutableStateOf("") }
    OutlinedTextField(
        value = title.value,
        onValueChange = {
            title.value = it
            viewModel.title = it
        },
        label = { Text("Title") },
        leadingIcon = { Icon(Icons.Filled.Title, contentDescription = "Input your title of post") },
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    )
}

@Composable
fun ItemNameInputField(viewModel: AddContentViewModel) {
    val itemName = remember { mutableStateOf("") }
    OutlinedTextField(
        value = itemName.value,
        onValueChange = {
            itemName.value = it
            viewModel.itemName = it
        },
        label = { Text("Item Name") },
        leadingIcon = { Icon(Icons.Filled.Coffee, contentDescription = "Input your coffee item name") },
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    )
}

@Composable
fun CoffeeSizeDropDown(viewModel: AddContentViewModel) {
    val coffeeSize: MutableState<CoffeeSize?> = remember { mutableStateOf(null) }
    DropdownMenu(
        expanded = false,
        onDismissRequest = {},
        modifier = Modifier.fillMaxWidth().padding(10.dp)
    ) {
        CoffeeSize.values().map {
            DropdownMenuItem(
                onClick = {
                    viewModel.coffeeSize = it
                    coffeeSize.value = it
                },
                text = { Text(text = it.displayValue) }
            )
        }
    }
}

@Composable
fun CoffeeOptionArea(viewModel: AddContentViewModel) {
    Column {
        AppendableOption.values().map {
            CoffeeOptionDropDown(viewModel, it)
        }
        AmountMutableOption.values().map {
            CoffeeOptionDropDown(viewModel, it)
        }
    }
}

@Composable
fun CoffeeOptionDropDown(viewModel: AddContentViewModel, option: dev.outup.coffeeapp.domain.model.option.Option) {
    var expanded by remember { mutableStateOf(false) }

    val values = if (option is AppendableOption) {
        listOf("追加")
    } else {
        Amount.values().map { it.description }
    }

    var selectedText by remember { mutableStateOf("") }

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(5.dp)) {

        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    textFieldSize = coordinates.size.toSize()
                },
            label = { Text(option.itemName) },
            trailingIcon = {
                Icon(icon, "Expand State Icon",
                    Modifier.clickable { expanded = !expanded })
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            values.forEach { value ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = value
                        viewModel.coffeeOption[option.itemName] = value
                        expanded = false
                    },
                    text = { Text(text = value) })
            }
        }
    }
}

/**
 * カメラで撮影した写真ファイルを保存するファイルのURIを取得する
 */
private fun getImageUri(context: Context): Uri {
    val imagePath = File(context.cacheDir, "images")
    imagePath.mkdirs()

    val date = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.JAPANESE).format(Date())
    val file = File(imagePath, "$date.jpg")
    return FileProvider.getUriForFile(
        context,
        context.packageName + ".fileprovider",
        file
    )
}

/**
 * 写真を撮影するか画像を選択するインテントを作成する
 */
private fun createChooser(uri: Uri): Intent {
    // ファイルの選択
    val getContentIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
        type = "image/*"
    }
    // カメラ撮影
    val imageCaptureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

    val chooserIntent = Intent.createChooser(getContentIntent, "写真の選択")
    chooserIntent.putExtra(
        Intent.EXTRA_INITIAL_INTENTS,
        arrayOf<Parcelable>(imageCaptureIntent)
    )
    return chooserIntent
}

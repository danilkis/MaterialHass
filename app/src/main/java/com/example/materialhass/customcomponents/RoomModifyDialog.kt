package com.example.materialhass.customcomponents

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.materialhass.model.Room
import com.example.materialhass.viewmodel.RoomsViewmodel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

@Composable
fun RoomModifyDialog(
    open: Boolean,
    old_room: Room?,
    onDismiss: () -> Unit,
    viewModel: RoomsViewmodel
) {
    val coroutineScope = rememberCoroutineScope()
    val contentResolver = LocalContext.current.contentResolver
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var filePath by remember { mutableStateOf("") }
    if (open) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.saveRoom(
                                Room(old_room!!.id, old_room!!.name, filePath, name, amount) //TODO: Обработка пустых полей
                            )
                        }
                        onDismiss()
                    }
                ) {
                    Text(text = "Готово")
                }
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = { Text("Название комнаты") }
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    OutlinedTextField(
                        value = amount,
                        onValueChange = { amount = it },
                        placeholder = { Text("Иконка") } //TODO: Сдеать выбор иконки с помощью deviceIcon
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    val ctx = LocalContext.current
                    val filePickerLauncher =
                        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                            filePath = getFilePathFromContentUri(ctx, uri!!).toString()
                            Log.e("File", filePath.toString())
                        }

                    OutlinedButton(onClick = {
                        filePickerLauncher.launch("image/*")
                    }) {
                        Text("Выберите фото")
                    }
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(text = "Отмена")
                }
            },
            title = { Text("Изменение комнаты") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
            }
        )
    }
}

fun getFilePathFromContentUri(context: Context, contentUri: Uri): String? {
    val fileName = "IMG_${System.currentTimeMillis()}.jpg"
    val directory =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera")
    val file = File(directory, fileName)

    var inputStream: InputStream? = null
    var outputStream: OutputStream? = null

    try {
        inputStream = context.contentResolver.openInputStream(contentUri)
        outputStream = FileOutputStream(file)

        inputStream?.copyTo(outputStream)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        inputStream?.close()
        outputStream?.close()
    }

    return file.absolutePath
}
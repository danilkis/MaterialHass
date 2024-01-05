package com.example.materialhass.customcomponents

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.materialhass.model.Room
import com.example.materialhass.viewmodel.RoomsViewmodel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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
                        Log.e("SHIT", old_room.toString())
                        coroutineScope.launch {
                            viewModel.saveRoom(Room(old_room!!.id, old_room!!.name, filePath!!, name, amount))
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

                    val filePickerLauncher =
                        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                            filePath = uri.toString()
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
package com.example.remoconmouse.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remoconmouse.ui.components.HeaderComponents
import com.example.remoconmouse.utils.MouseActionUtils

@Preview
@Composable
fun HomeScreen(
    onNavigateMouse: () -> Unit = {},
    viewModel: MouseViewModel = viewModel()
) {
    val context = LocalContext.current

    var ipAddress by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HeaderComponents()

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = ipAddress,
            onValueChange = { ipAddress = it },
            label = { Text("接続先のIPを入力してください") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.connect(ipAddress)
                if (viewModel.isConnected) {
                    onNavigateMouse()
                } else {
                    Toast.makeText(context, "接続に失敗しました", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
        ) {
            Text(MouseActionUtils.connect(), fontSize = 20.sp)
        }
    }
}
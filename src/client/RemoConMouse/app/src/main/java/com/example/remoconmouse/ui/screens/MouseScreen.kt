package com.example.remoconmouse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.remoconmouse.ui.components.HeaderComponents
import com.example.remoconmouse.ui.components.MouseComponents
import com.example.remoconmouse.viewmodel.MouseViewModel

@Preview
@Composable
fun MouseScreen(
    onNavigateHome: () -> Unit,
    viewModel: MouseViewModel = viewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HeaderComponents()

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.disconnect()
                onNavigateHome()
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
        ) {
            Text("Disconnect", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        var isEnabled by remember { mutableStateOf(false) }
        Button(
            onClick = {
                viewModel.toggleMouseEnable()
                isEnabled = !isEnabled
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
        ) {
            Text(
                text = if (isEnabled) "Enable" else "Disenable",
                fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        MouseComponents(viewModel)
    }
}


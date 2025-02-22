package com.example.remoconmouse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderComponents() {
    Text(
        text = "RemoConMouse",
        fontSize = 40.sp,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue)
            .padding(16.dp)
    )
}
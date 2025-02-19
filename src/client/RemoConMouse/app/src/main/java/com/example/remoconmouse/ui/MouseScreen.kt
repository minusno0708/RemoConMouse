package com.example.remoconmouse.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun MouseScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "RemoConMouse",
            fontSize = 40.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
        ) {
            Text("Disconnect", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
        ) {
            Text("Enable", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        MouseLayout()
    }
}

@Composable
fun MouseLayout() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.5f)
            .padding(16.dp)
            .border(2.dp, Color.White, RoundedCornerShape(24.dp))
            .background(Color.LightGray, RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ClickButton(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                WheelButton(modifier = Modifier.weight(1f))
                WheelButton(modifier = Modifier.weight(1f))
                WheelButton(modifier = Modifier.weight(1f))
            }

            ClickButton(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun ClickButton(modifier: Modifier) {
    Box(
        modifier
            .fillMaxHeight(1f)
            .border(1.dp, Color.White)
    )
}

@Composable
fun WheelButton(modifier: Modifier) {
    Box(
        modifier
            .fillMaxWidth()
            .border(1.dp, Color.White)
    )
}
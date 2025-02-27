package com.example.remoconmouse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.remoconmouse.viewmodel.MouseViewModel

@Composable
fun MouseComponents(viewModel: MouseViewModel) {
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
            ClickButton(
                modifier = Modifier.weight(1f),
                onPush = { viewModel.mouseClick("left-down") },
                onRemove = { viewModel.mouseClick("left-up") }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                WheelButton(
                    modifier = Modifier.weight(1f),
                    onPush = { viewModel.onMouseScroll("up") },
                    onRemove = { viewModel.offMouseScroll() }
                )
                WheelButton(
                    modifier = Modifier.weight(1f),
                    onPush = { viewModel.mouseClick("middle-down") },
                    onRemove = { viewModel.mouseClick("middle-up") }
                )
                WheelButton(
                    modifier = Modifier.weight(1f),
                    onPush = { viewModel.onMouseScroll("down") },
                    onRemove = { viewModel.offMouseScroll() }
                )
            }

            ClickButton(
                modifier = Modifier.weight(1f),
                onPush = { viewModel.mouseClick("right-down") },
                onRemove = { viewModel.mouseClick("right-up") }
            )
        }
    }
}

@Composable
fun ClickButton(
    modifier: Modifier,
    onPush: () -> Unit,
    onRemove: () -> Unit
) {
    Box(
        modifier
            .fillMaxHeight(1f)
            .border(1.dp, Color.White)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        try {
                            onPush()
                            awaitRelease()
                            onRemove()
                        } catch (e: Exception) {
                            onRemove()
                        }
                    }
                )
            }
    )
}

@Composable
fun WheelButton(
    modifier: Modifier,
    onPush: () -> Unit,
    onRemove: () -> Unit
) {
    Box(
        modifier
            .fillMaxWidth()
            .border(1.dp, Color.White)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        try {
                            onPush()
                            awaitRelease()
                            onRemove()
                        } catch (e: Exception) {
                            onRemove()
                        }
                    }
                )
            }
    )
}
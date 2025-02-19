package com.example.remoconmouse.ui

import android.view.MotionEvent
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
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.remoconmouse.MouseActivity

@Preview
@Composable
fun MouseScreen() {
    val context = LocalContext.current
    val activity = requireNotNull(context as? MouseActivity)

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
            onClick = { activity.toHome() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray)
        ) {
            Text("Disconnect", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        var isEnabled by remember { mutableStateOf(false) }
        Button(
            onClick = {
                activity.switchMouseOnOf()
                isEnabled = !isEnabled
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
        ) {
            Text(
                text = if (isEnabled) "Enable" else "Disenable",
                fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        MouseLayout(activity)
    }
}

@Composable
fun MouseLayout(activity: MouseActivity) {
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
                onPush = { activity.mouseClick("left-down") },
                onRemove = { activity.mouseClick("left-up") }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                WheelButton(
                    modifier = Modifier.weight(1f),
                    onPush = { activity.onMouseScroll("up") },
                    onRemove = { activity.offMouseScroll() }
                )
                WheelButton(
                    modifier = Modifier.weight(1f),
                    onPush = { activity.mouseClick("middle-down") },
                    onRemove = { activity.mouseClick("middle-up") }
                )
                WheelButton(
                    modifier = Modifier.weight(1f),
                    onPush = { activity.onMouseScroll("down") },
                    onRemove = { activity.offMouseScroll() }
                )
            }

            ClickButton(
                modifier = Modifier.weight(1f),
                onPush = { activity.mouseClick("right-down") },
                onRemove = { activity.mouseClick("right-up") }
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
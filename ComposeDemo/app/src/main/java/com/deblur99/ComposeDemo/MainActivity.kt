package com.deblur99.ComposeDemo

import android.os.Bundle
import androidx.compose.runtime.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderPositions
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.deblur99.ComposeDemo.ui.theme.ComposeDemoTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DemoScreen()
                }
            }
        }
    }
}

@Composable
fun DemoText(message: String, size: Float) {
    Text(message)

    Text(
        message,
        // 값이 온 다음에는 어떤 단위가 오는지 정해 주어야 한다. (px, dp, sp)
        fontSize = size.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun DemoScreen() {
    // State 변수
    var sliderPosition by remember { mutableStateOf(20F) }  // 초기값 20F로 설정

    // 콜백 함수. 새 값을 입력받으면 State 변수에 저장한다.
    val handlePositionChange = { position: Float ->
        sliderPosition = position
    }

    // 뷰
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        DemoText(message = "Welcome to Compose", size = sliderPosition)

        Spacer(modifier = Modifier.height(150.dp))

        DemoSlider(sliderPosition = sliderPosition, onPositionChange = handlePositionChange)

        Text(
            "${sliderPosition.toInt()}sp",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
fun DemoSlider(sliderPosition: Float, onPositionChange: (Float) -> Unit) {
    Slider(
        modifier = Modifier.padding(10.dp),
        valueRange = 20F..40F,
        value = sliderPosition,
        onValueChange = {
            onPositionChange(it)    // Slider 값이 바뀌면 자기 자신의 상태 변경
        }
    )
}

@Composable
fun CustomList(items: List<String>) {
    Column {
        for (item in items) {
            Text(item)
            Divider(color = Color.Gray)
        }
    }
}

//@Preview
@Composable
fun DemoSwitch() {
    val checked = remember { mutableStateOf(true) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Switch(checked = checked.value, onCheckedChange = {
            checked.value = it
        })

        if (checked.value) {
            Text("Switch on!!")
        } else {
            Text("Switch off!!")
        }
    }
}

@Preview
@Composable
fun DemoListPreview() {
    CustomList(items = listOf("One", "Two", "Three", "Four", "Five", "Six", "Seven"))
}
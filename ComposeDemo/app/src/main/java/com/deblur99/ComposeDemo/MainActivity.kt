@file:OptIn(ExperimentalMaterial3Api::class)

package com.deblur99.ComposeDemo

import android.os.Bundle
import androidx.compose.runtime.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
                    Column {
//                        SwitchDemo()
                        DemoTextField()
                    }

                }
            }
        }
    }
}

@Composable
fun DemoText(message: String, size: Float) {
    // 스유에서의 @State가 컴포즈에서의 by remember { mutableStateOf("Hello Gayoung!") }
    var text by remember { mutableStateOf("Hello Gayoung!") }

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

@Preview
@Composable
fun SwitchDemo() {
    // 1) 필요한 프로퍼티와 함수를 선언 및 정의한다.
    var isOn by remember { mutableStateOf(false) }
    val onClicked = { value: Boolean ->
        isOn = value
    }

    // 2) Composable 위젯을 생성한다.
    Row(modifier = Modifier.padding(start=24.dp, end=60.dp)) {
        if (isOn) Text("Switch is now ON state.") else Text("Switch is now OFF state.")
        Spacer(modifier = Modifier.fillMaxSize())
        Switch(checked = isOn, onCheckedChange = onClicked)
    }
}

@Composable
fun DemoTextField() {
    var text by rememberSaveable { mutableStateOf("") }

    val onEditted = { s: String ->
        text = s
    }

    // 바깥에서 변수와 함수 넣어줘서 실제 TextField는 여기서 생성된다.
    DemoTextFieldWithHoisting(text = text, onValueChanged = onEditted)
}

@Composable
fun DemoTextFieldWithHoisting(text: String, onValueChanged: (String) -> Unit) {
    TextField(value = text, onValueChange = onValueChanged)
}

@Preview
@Composable
fun DemoListPreview() {
    CustomList(items = listOf("One", "Two", "Three", "Four", "Five", "Six", "Seven"))
}
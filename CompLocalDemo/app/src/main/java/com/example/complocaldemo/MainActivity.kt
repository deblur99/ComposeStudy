package com.example.complocaldemo

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.complocaldemo.ui.theme.CompLocalDemoTheme

// CompositionLocal : 상위 뷰에서 하위 뷰에 상태값을 주입할 때 사용한다.
// compositionLocalOf - 주기적으로 상태가 바뀔 때 사용
// staticCompositionLocalOf - 상태가 잘 바뀌지 않을 때 사용

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompLocalDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Composable1()
                }
            }
        }
    }
}

val LocalColor = staticCompositionLocalOf { Color(0xFFFFDBCF) }

@Composable
fun Composable1() {
    // 다크모드 여부에 따라 색상 변경
    var color = if (isSystemInDarkTheme()) {
        Color(0xFFA08D87)
    } else {
        Color(0xFFFFDBCF)
    }

    Column {
        Composable2()

        // static composition local인 LocalColor의 값이 Composable1의 color에 따라 변경된다.
        CompositionLocalProvider(LocalColor provides color) {
            Composable3()
        }
    }
}

@Composable
fun Composable2() {
    Composable4()
}

@Composable
fun Composable3() {
    Text("Composable 3", modifier = Modifier.background(LocalColor.current))

    CompositionLocalProvider(LocalColor provides Color.Red) {
        Composable5()
    }
}

@Composable
fun Composable4() {
    Composable6()
}

@Composable
fun Composable5() {
    Text("Composable 5", modifier = Modifier.background(LocalColor.current))

    // 상태값 주입하고 하위 뷰 생성
    // -> 하위 뷰에서는 주입된 값을 가져다 사용할 수 있다.
    CompositionLocalProvider(LocalColor provides Color.Green) {
        Composable7()
    }

    CompositionLocalProvider(LocalColor provides Color.Yellow) {
        Composable8()
    }
}

@Composable
fun Composable6() {
    // 상위 뷰에서 LocalColor 값이 변경되지 않아 초기값 그대로 배경색에 적용한다.
    Text("Composable 6", modifier = Modifier.background(LocalColor.current))
}

@Composable
fun Composable7() {
    Text("Composable 7", modifier = Modifier.background(LocalColor.current))
}

@Composable
fun Composable8() {
    // static composition local의 현재 값을 Text의 배경색으로 사용
    Text("Composable 8", modifier = Modifier.background(LocalColor.current))
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DarkPreview() {
    CompLocalDemoTheme {
        Composable1()
    }
}
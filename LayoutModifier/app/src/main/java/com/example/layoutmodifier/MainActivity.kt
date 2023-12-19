package com.example.layoutmodifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.layoutmodifier.ui.theme.LayoutModifierTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutModifierTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(width = 120.dp, height = 80.dp)
    ) {
        ColorBox(
            modifier = Modifier
                .exampleLayout(90, 100)  // x = 90, y = 50만큼 레이아웃의 좌표값 이동
                .background(Color.Blue)
        )

        Column {
            ColorBox(
                modifier = Modifier.exampleLayout2(0f).background(Color.Blue)
            )

            ColorBox(
                modifier = Modifier.exampleLayout2(0.25f).background(Color.Green)
            )

            ColorBox(
                modifier = Modifier.exampleLayout2(0.5f).background(Color.Yellow)
            )

            ColorBox(
                modifier = Modifier.exampleLayout2(0.25f).background(Color.Red)
            )

            ColorBox(
                modifier = Modifier.exampleLayout2(0f).background(Color.Magenta)
            )
        }
    }
}

@Composable
fun ColorBox(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .padding(1.dp)
            .size(width = 50.dp, height = 10.dp)
            .then(modifier)
    )
}

// Custom modifier
fun Modifier.exampleLayout(
    x: Int,
    y: Int
) = layout { measurable, constraints ->
    // 부모 뷰를 기준으로 한 레이아웃의 위치값 계산하여 Placeable 객체 반환
    val placeable = measurable.measure(constraints)

    // 새로 계산된 좌표계가 들어있는 Placeable 객체로 다시 layout 구성
    layout(placeable.width, placeable.height) {
        placeable.placeRelative(x, y)
    }
}

fun Modifier.exampleLayout2(
    fraction: Float
) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    // placeable 객체로부터 자식의 폭을 받아서 fraction 파라미터값을 곱한다.
    // 정렬선을 오른쪽으로 옮기는 것은 자식을 왼쪽으로 옮기는 것과 같으므로 음수 처리한다.
    val x = -(placeable.width * fraction).roundToInt()

    // placeable 객체로부터 텍스트 정렬선 접근
//    val firstBaseline = placeable[FirstBaseline]
//    val lastBaseline = placeable[LastBaseline]

    // 텍스트 정렬선이 지원하는 정렬선인지 확인
//    if placeable[FirstBaseline] == AlignmentLine.Unspecified {
//
//    }

    // 수평 위치만 변경되었으므로 수직 위치는 0으로 둔다.
    layout(placeable.width, placeable.height) {
        placeable.placeRelative(x = x, y = 0)
    }
}
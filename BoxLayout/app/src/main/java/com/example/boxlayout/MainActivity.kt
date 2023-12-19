package com.example.boxlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.boxlayout.ui.theme.BoxLayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoxLayoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@Preview
@Composable
fun Clipped() {
    // Box - SwiftUI의 ZStack, XML 레이아웃에서의 FrameLayout처럼
    // 뷰들을 같은 위치에 겹쳐 표시하는 레이아웃
    Box(modifier = Modifier.size(size = 200.dp)
        // .clip()을 적용하여 뷰를 일정한 모양으로 자른다.
        // RoundedCornerShape - 둥근 모서리의 직사각형
        // CutCornerShape - 각진 모서리의 다각형
        .clip(CutCornerShape(30.dp))
        .background(Color.Blue)
    ) {

    }
}


@Preview
@Composable
fun HomeScreen() {
    Box(
//        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier.size(width = 290.dp, height = 90.dp)
    ) {
        Text("TopStart", Modifier.align(Alignment.TopStart))
        Text("TopCenter", Modifier.align(Alignment.TopCenter))
        Text("TopEnd", Modifier.align(Alignment.TopEnd))

        Text("CenterStart", Modifier.align(Alignment.CenterStart))
        Text("Center", Modifier.align(Alignment.Center))
        Text("CenterEnd", Modifier.align(Alignment.CenterEnd))

        Text("BottomStart", Modifier.align(Alignment.BottomStart))
        Text("BottomCenter", Modifier.align(Alignment.BottomCenter))
        Text("BottomEnd", Modifier.align(Alignment.BottomEnd))

        // Modifier.matchParentSize()
        // -> 자식 뷰의 크기를 부모 뷰에 맞춘다

//        val height = 200.dp
//        val width = 200.dp

//        TextCell(text = "1", modifier = Modifier.size(width = width, height = height))
//        TextCell(text = "2", modifier = Modifier.size(width = width, height = height))
//        TextCell(text = "3", modifier = Modifier.size(width = width, height = height))
    }
}

@Composable
fun TextCell(text: String, modifier: Modifier = Modifier, fontSize: Int = 150) {
    val cellModifier = Modifier
        .padding(4.dp)
        .border(width = 5.dp, color = Color.Black)

    // 뷰를 불투명하게 하기 위해 Surface 뷰 적용
    Surface {
        Text(
            text = text,
            modifier = cellModifier.then(modifier),
            fontSize = fontSize.sp, // 매개변수에서 바로 .sp 찍어서 변환할 수 있다.
            textAlign = TextAlign.Center
        )
    }
}
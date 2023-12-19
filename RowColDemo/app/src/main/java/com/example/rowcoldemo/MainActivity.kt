package com.example.rowcoldemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rowcoldemo.ui.theme.RowColDemoTheme

/*
* Alignment, Arrangement
* .align()
* .alignByBaseline (alignBy())
* .paddingFrom()
* .weight()
* */


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RowColDemoTheme {
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

@Preview(showSystemUi = true)
@Composable
fun MainScreen() {
    Row {
        TextCell(text = "1", modifier = Modifier.weight(weight = 0.2f, fill = true))
        TextCell(text = "2", modifier = Modifier.weight(weight = 0.4f, fill = true))
        TextCell(text = "3", modifier = Modifier.weight(weight = 0.3f, fill = true))
    }

//    Row(
//        // SpaceBetween -> SpaceAround -> SpaceEvenly 순으로 가운데에 몰려 있다.
//    ) {
//        Text(
//            text = "Large Text\nMore Text",
//            fontSize = 40.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.alignBy(LastBaseline)   // 기준선을 어디로 잡을 것인지
//        )
//
//        Text(
//            text = "Small Text",
//            fontSize = 32.sp,
//            fontWeight = FontWeight.Bold,
//            // 뷰 자체를 align할 때에는 .align(), .alignByBaseline()과 같은
//            // Modifier 속성을 적용할 수 있다.
//
//            // .paddingFrom()으로 기준선의 위치를 수정할 수 있다.
//            modifier = Modifier.paddingFrom(
//                alignmentLine = FirstBaseline,
//                before = 80.dp, after = 0.dp
//            )
//        )
//    }
}

@Composable
fun TextCell(text: String, modifier: Modifier = Modifier) {
    val cellModifier = Modifier
        .padding(4.dp)
        .size(width = 100.dp, height = 100.dp)
        .border(width = 4.dp, color = Color.Black)

    Text(
        text = text,
        modifier = cellModifier.then(modifier),
        fontSize = 70.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}
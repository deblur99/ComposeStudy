package com.example.constraintlayoutdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.constraintlayoutdemo.ui.theme.ConstraintLayoutDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConstraintLayoutDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // ConstraintLayout 생성자에서 제약집합 받고 자식 뷰 받기
                    ConstraintLayout(constraintSet = myConstraintSet(margin = 8.dp), modifier = Modifier.size(width = 200.dp, height = 200.dp)) {
                        MyButton(
                            text = "Button1",
                            Modifier
                                .size(200.dp)
                                .layoutId("button1")
                        )
                    }
                }
            }
        }
    }

    // 참조 변수를 만들고 참조 변수에 대해 제약을 건다.
    // 그렇게 설정된 참조 변수들의 집합을 반환한다. (ConstraintSet 타입)
    private fun myConstraintSet(margin: Dp): ConstraintSet {
        return ConstraintSet {
            val button1 = createRefFor("button1")

            constrain(button1) {
                linkTo(parent.top, parent.bottom, topMargin = margin, bottomMargin = margin)
                linkTo(parent.start, parent.end, startMargin = margin, endMargin = margin)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        }
    }
}

@Preview
@Composable
fun MyMainScreen() {
    ConstraintLayout(modifier = Modifier.size(200.dp)) {
        val button1 = createRef()

        MyButton(text = "Button1", modifier = Modifier
            .size(200.dp)
            .constrainAs(button1) {
                linkTo(parent.top, parent.bottom, topMargin = 8.dp, bottomMargin = 8.dp)
                linkTo(parent.start, parent.end, startMargin = 8.dp, endMargin = 8.dp)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            })
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreen() {
    ConstraintLayout(
        modifier = Modifier
            .size(width = 200.dp, height = 300.dp)
            .background(Color.White)
    ) {
        // createRefs() : 참조 생성
        // -> constraint를 적용하기 위해서 참조를 생성해야 한다.
        // ConstraintLayout 안에서 써야 한다.
        val (button1, button2, button3) = createRefs()

        // 뷰의 modifier 부분에서 constraint 속성 적용한다.
        MyButton(text = "Button1", modifier = Modifier.constrainAs(button1) {
            // top, start 등 - constraint 내부의 요소
            top.linkTo(parent.top, margin = 60.dp)  // 상단으로부터 60dp만큼 떨어지기

            // 1) 따로따로 제약 걸기
//            start.linkTo(parent.start)
//            end.linkTo(parent.end)
            // -> 한 번에 제약조건을 여러 개 거는 것도 가능하다.

            // 2) 한 번에 제약 걸기 1
            // 제약 편향: 한쪽 방향으로 치우친 위치에 뷰를 배치한다.
            // bias를 1.0으로 주면 margin의 방향을 역전시킬 수 있다.
//            linkTo(parent.start, parent.end, endMargin = 30.dp, bias = 1.0f)
            linkTo(parent.start, parent.end, startMargin = 30.dp, endMargin = 50.dp)

            // 3) 한 번에 제약 걸기 2
//            centerHorizontallyTo(parent)    // 부모 뷰의 가로축 중앙에 배치

            // 1~3번의 실행 결과는 동일하다.
            // 유킷 코드구현방식이랑 상당히 비슷하다.
        })

        MyButton(text = "Button2", modifier = Modifier.constrainAs(button2) {
            centerHorizontallyTo(parent)
            top.linkTo(button1.bottom)
            bottom.linkTo(parent.bottom)
        })
    }
}

@Preview
@Composable
fun ChainLayout() {
    ConstraintLayout(
        modifier = Modifier
            .size(width = 400.dp, height = 100.dp)
            .background(Color.White)
    ) {
        val (button1, button2, button3) = createRefs()

        // 위에서 만든 참조 변수들을 가지고 정렬 방향을 수평 방향으로 설정한다.
        createHorizontalChain(button1, button2, button3, chainStyle = ChainStyle.SpreadInside)

        MyButton(text = "Button1", modifier = Modifier.constrainAs(button1) {
            centerVerticallyTo(parent)
        })

        MyButton(text = "Button2", modifier = Modifier.constrainAs(button2) {
            centerVerticallyTo(parent)
        })

        MyButton(text = "Button3", modifier = Modifier.constrainAs(button3) {
            centerVerticallyTo(parent)
        })
    }
}

@Preview
@Composable
fun GuidelineLayout() {
    ConstraintLayout(
        modifier = Modifier
            .size(width = 400.dp, height = 320.dp)
            .background(Color.White)
    ) {
        val (button1, button2, button3) = createRefs()

        // 부모뷰의 폭과 높이를 기준으로 0.6배 만큼의 위치에서 정렬축 정하기
        // 새로 정해진 정렬축을 기준으로 자식 뷰들을 정렬한다.
        val guide = createGuidelineFromStart(fraction = 0.6f)

        MyButton(text = "Button1", modifier = Modifier.constrainAs(button1) {
            top.linkTo(parent.top, margin = 30.dp)
            end.linkTo(guide, margin = 30.dp)
        })

        MyButton(text = "Button2", modifier = Modifier.constrainAs(button2) {
            top.linkTo(button1.bottom, margin = 20.dp)
            start.linkTo(guide, margin = 40.dp)
        })

        MyButton(text = "Button3", modifier = Modifier.constrainAs(button3) {
            top.linkTo(button2.bottom, margin = 40.dp)
            end.linkTo(guide, margin = 20.dp)
        })
    }
}

@Preview
@Composable
fun BarrierLayout() {
    ConstraintLayout(
        modifier = Modifier
            .size(width = 350.dp, height = 220.dp)
            .background(Color.White)
    ) {
        val (button1, button2, button3) = createRefs()

        val barrier = createEndBarrier(button1, button2)

        MyButton(text = "Button1",
            Modifier
                .width(100.dp)
                .constrainAs(button1) {
                    top.linkTo(parent.top, margin = 30.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                })

        MyButton(text = "Button2",
            Modifier
                .width(150.dp)
                .constrainAs(button2) {
                    top.linkTo(button1.bottom, margin = 20.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                })

        MyButton(text = "Button3",
            Modifier.constrainAs(button3) {
                linkTo(parent.top, parent.bottom, topMargin = 8.dp, bottomMargin = 8.dp)
                linkTo(button1.end, parent.end, startMargin = 30.dp, endMargin = 8.dp)

                // barrier의 오른쪽과 Button3 뷰의 왼쪽 사이의 간격을 30dp로 설정
                start.linkTo(barrier, margin = 30.dp)
                // -> button2의 크기도 고려하여 button2가 button3의 영역과 겹치지 않는다.

                // ConstraintLayout이 자리잡은 크기에 자식뷰의 크기를 맞춘다.
                // (match_parent와 동일)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            })
    }
}


@Composable
fun MyButton(text: String, modifier: Modifier = Modifier) {
    Button(onClick = { /*TODO*/ }, shape = RoundedCornerShape(10.dp), modifier = modifier) {
        Text(text)
    }
}
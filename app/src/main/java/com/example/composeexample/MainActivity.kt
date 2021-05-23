package com.example.composeexample

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                MessageList(listOf("1","2"))
                ShowButton()
            }
        }
    }
}

@Preview
@Composable
fun MessageList(
    @PreviewParameter(ListProvider::class)
    messages: List<String>
) {

       Column(
           modifier = Modifier.padding(10.dp)
       ) {
           Image(
               painter = painterResource(id = R.drawable.header),
               contentDescription = null,
               modifier = Modifier
                   .height(180.dp)
                   .fillMaxWidth()
                   .clip(RoundedCornerShape(4.dp)),
               contentScale = ContentScale.Crop
           )

           Spacer(modifier = Modifier.height(16.dp))

          Column {
              messages.forEach { message ->
                  MessageRow(message)
                  Divider(color = Color.Black)
              }
          }
       }

}

@Composable
fun MessageRow(message: String) {
    val text = "Message is $message"
    val context = LocalContext.current
    var isSelected by remember { mutableStateOf(false) }
    val typography = MaterialTheme.typography
    val backgroundColor by animateColorAsState(if(isSelected) Color.Red else Color.Yellow)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                modifier = Modifier
                    .clickable(
                        onClick = {
                            handleClick(context, text)
                            isSelected = !isSelected
                        }
                    )
                    .align(Alignment.CenterHorizontally)
                    .background(color = backgroundColor),
                style = typography.h5,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
}

@Composable
fun ShowButton() {
    val count = remember { mutableStateOf(0) }

    Button(
        onClick = {
        count.value++
    },
    colors = ButtonDefaults.buttonColors(
        backgroundColor = if(count.value % 5 == 0) Color.Green else Color.Yellow
    ),
    modifier = Modifier
        .wrapContentWidth(unbounded = true)
    ) {
        Text(text = "I have been clicked ${count.value} times",
        modifier = Modifier
            .wrapContentWidth(align = Alignment.CenterHorizontally)
        )
    }
}

private fun handleClick(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

class ListProvider: PreviewParameterProvider<List<String>> {
    override val values: Sequence<List<String>>
        get() = sequenceOf(listOf("t1", "t2"))
    override val count: Int
        get() = values.count()
}
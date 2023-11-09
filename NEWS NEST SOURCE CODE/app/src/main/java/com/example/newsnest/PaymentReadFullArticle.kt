package com.example.newsnest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsnest.ui.theme.NewsNestTheme

class PaymentReadFullArticle : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsNestTheme {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                        .background(Color.White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Text(
                        text = "Wanna read the complete news article ?",
                        fontFamily = FontFamily(Font(R.font.arialbold)),
                        color = Color(0xFF3064FC),
                        textAlign = TextAlign.Center,
                        fontSize = 28.sp,
                        lineHeight = 32.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Get the complete picture with \nour in-depth news article!",
                        fontFamily = FontFamily(Font(R.font.arialnormal)),
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFFFA500)),
                        modifier = Modifier
                            .padding(16.dp)
                    )
                    {
                        Text(
                            text = "ONLY ₹ 10",
                            fontFamily = FontFamily(Font(R.font.arialbold)),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 28.sp,
                            lineHeight = 32.sp,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
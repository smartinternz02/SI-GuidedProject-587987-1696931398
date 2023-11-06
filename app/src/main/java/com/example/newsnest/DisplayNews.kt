package com.example.newsnest

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.example.newsnest.ui.theme.NewsNestTheme

class DisplayNews : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val desk = intent.getStringExtra("desk") ?: ""
        val title = intent.getStringExtra("title") ?: ""
        val uriImage = intent.getStringExtra("urlToImage") ?: ""
        setContent {
            DisplayNewsContent(
                desk = desk,
                title = title,
                uriImage = uriImage
            )
        }
    }
}

@Composable
fun DisplayNewsContent(
    desk: String,
    title: String,
    uriImage: String
) {
    NewsNestTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                DisplayNewsCard(
                    desk = desk,
                    title = title,
                    uriImage = uriImage
                )
            }
        }
    }
}

@Composable
fun DisplayNewsCard(
    desk: String,
    title: String,
    uriImage: String
)
{
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        Column(
            modifier = Modifier
                .border(8.dp, Color(0xFF3064FC), shape = RoundedCornerShape(8.dp))
                .background(Color.White)
        )
        {
            Card (
                modifier = Modifier
                    .padding(20.dp)
            )
            {
                Image(
                    modifier = Modifier
                        .height(200.dp)
                        .border(8.dp, Color.Transparent, shape = RoundedCornerShape(8.dp)),
                    painter = rememberImagePainter(
                        data = uriImage,
                        builder = {
                            scale(Scale.FILL)
                            placeholder(R.drawable.placeholder_loading)
                            error(R.drawable.placeholder_news)
                        }
                    ),
                    contentDescription = "News Image",
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .padding(
                        start = 20.dp,
                        end = 20.dp
                    )
                    .fillMaxSize()
            )
            {
                Text(
                    text = title,
                    fontFamily = FontFamily(Font(R.font.timesnewroman)),
                    color = Color(0xFFB01E3A),
                    fontSize = 24.sp,
                    lineHeight = 28.sp,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = desk,
                    fontFamily = FontFamily(Font(R.font.arialnormal)),
                    color = Color.Black,
                    textAlign = TextAlign.Justify,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick =
                    {
                        val intent = Intent(context, PaymentReadFullArticle :: class.java)
                        context.startActivity(intent)
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFA500)),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                {
                    Text(
                        text = "READ FULL ARTICLE",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context -> TextView(context) },
        update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) }
    )
}

package com.example.newsnest

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color.parseColor
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.example.news.Articles
import com.example.newsnest.ui.theme.NewsNestTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsNestTheme {
                Column(
                    modifier = Modifier.background(Color.White)
                )
                {
                    var isExpanded by remember { mutableStateOf(false) }

                    Row(Modifier.fillMaxWidth())
                    {
                        Spacer(modifier = Modifier.padding(9.dp,0.dp))
                        ExpandableIcon(
                            modifier = Modifier
                                .align(Alignment.CenterVertically),
                            expanded = isExpanded,
                            onExpand = { isExpanded = !isExpanded },
                            onAboutClicked = { showDialog(this@MainActivity) },
                            onSignOutClicked = { signOut() }
                        )
                        Text(
                            text = "News Nest 📰",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.arialbold)),
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically),
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                    }
                    ArticleList(applicationContext, articleList = mainViewModel.articleListResponse)
                    mainViewModel.getArticleList()
                }
            }
        }
        scheduleNotificationWorker()
    }

    private fun scheduleNotificationWorker() {
        val notificationWorkRequest: WorkRequest = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(this).enqueue(notificationWorkRequest)
    }

    private fun signOut() {
        val sharedPreferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}

class NotificationWorker(private val context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        showNotification()

        return Result.success()
    }

    private fun showNotification() {
        val notificationChannelId = "default"
        val notificationId = 1

        val notificationManager = NotificationManagerCompat.from(context)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channelName = "Default"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(notificationChannelId, channelName, importance)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(context, notificationChannelId)
            .setSmallIcon(R.drawable.notification_logo)
            .setContentTitle("Latest & Trending headlines!")
            .setContentText("Explore and discover the most talked-about news stories.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}

@Composable
fun ExpandableIcon(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onExpand: () -> Unit,
    onAboutClicked: () -> Unit,
    onSignOutClicked: () -> Unit
) {
    val context = LocalContext.current
    Box(modifier = modifier)
    {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(30.dp)
                .background(
                    Color(android.graphics.Color.parseColor("#3064FC")),
                    RoundedCornerShape(5.dp)
                )
        )
        {
            Icon(
                imageVector = Icons.Rounded.Menu,
                contentDescription = "Expandable Icon",
                tint = Color(0xFFFFA500),
                modifier = Modifier
                    .size(30.dp)
                    .clickable(onClick = onExpand)
            )
        }
        if (expanded) {
            Column(
                modifier = Modifier
                    .padding(top = 48.dp, start = 8.dp)
//                    .border(1.dp, Color(0xFF3064FC), shape = RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .clip(RoundedCornerShape(8.dp))
                    .width(120.dp)
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, Color(0xFF3064FC), shape = RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(4.dp)
                )
                {
                    Text(
                        text = "About",
                        color = Color(0xFFFFA500),
                        modifier = Modifier
                            .clickable(onClick = onAboutClicked)
                            .padding(8.dp)
                            .fillMaxWidth(),
                        fontFamily = FontFamily(Font(R.font.arialbold)),
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, Color(0xFF3064FC), shape = RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(4.dp)
                )
                {
                    Text(
                        text = "Sign Out",
                        color = Color(0xFFFFA500),
                        modifier = Modifier
                            .clickable(onClick = onSignOutClicked)
                            .padding(8.dp)
                            .fillMaxWidth(),
                        fontFamily = FontFamily(Font(R.font.arialbold)),
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, Color(0xFF3064FC), shape = RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(4.dp)
                )
                {
                    Text(
                        text = "Premium Plans",
                        color = Color(0xFFFFA500),
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    val intent = Intent(context, PremiumPlans::class.java)
                                    context.startActivity(intent)
                                }
                            )
                            .padding(8.dp)
                            .fillMaxWidth(),
                        fontFamily = FontFamily(Font(R.font.arialbold)),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ArticleList(context: Context, articleList: List<Articles>) {
    var selectedIndex by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = "TOP HEADLINES",
            fontFamily = FontFamily(Font(R.font.arialbold)),
            color = Color.Red,
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            lineHeight = 32.sp
        )
        LazyColumn {
            itemsIndexed(items = articleList) { index, item ->
                ArticleItem(
                    context = context,
                    article = item,
                    index = index,
                    selectedIndex = selectedIndex
                ) { i ->
                    selectedIndex = i
                }
            }
        }
    }
}

@Composable
fun ArticleItem(
    context: Context,
    article: Articles,
    index: Int,
    selectedIndex: Int,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(20.dp, 8.7.dp)
            .fillMaxWidth()
            .height(250.dp)
            .selectable(true, true, null,
                onClick = {
                    Log.i("test123abc", "ArticleItem: $index/n$selectedIndex")
                })
            .clickable { onClick(index) },
        shape = RoundedCornerShape(8.dp)
    )
    {
        Surface(color = Color(parseColor("#3064FC")))
        {
            Row(
                Modifier
                    .padding(4.dp)
                    .fillMaxSize()
                    .background(Color.White), //brush = gradientBrush
                horizontalArrangement = Arrangement.Center
            )
            {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .selectable(true, true, null,
                            onClick = {
                                Log.i("test123abc", "ArticleItem: $index/n${article.description}")
                                context.startActivity(
                                    Intent(context, DisplayNews::class.java)
                                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        .putExtra("desk", article.description.toString())
                                        .putExtra("urlToImage", article.urlToImage)
                                        .putExtra("title", article.title)
                                )
                            }
                        )
                )
                {
                    Box (
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    )
                    {
                        Image(
                            painter = rememberImagePainter(
                                data = article.urlToImage,
                                builder = {
                                    scale(Scale.FILL)
                                    placeholder(R.drawable.placeholder_loading)
                                    fallback(R.drawable.placeholder_news)
                                }
                            ),
                            contentScale = ContentScale.Crop,
                            contentDescription = article.description,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(
                            text = article.title.toString(),
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.titleSmall,
                            textAlign = TextAlign.Justify,
                            fontFamily = FontFamily(Font(R.font.arialbold)),
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}


fun showDialog(context: Context) {
    val dialog = AlertDialog.Builder(context)
        .setTitle("ⓘAbout")
        .setMessage("Version 1.0\n" +
                "We will be releasing more features.\n" +
                "Stay Tuned")
        .setPositiveButton("OK")

        { _, _ ->
            // Handle OK button click if needed
        }
        .create()


    dialog.show()
}

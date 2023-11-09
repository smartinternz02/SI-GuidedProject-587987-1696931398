package com.example.newsnest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsnest.ui.theme.NewsNestTheme

class PremiumPlans : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsNestTheme {
                Surface(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxSize()
                )
                {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .verticalScroll(rememberScrollState())
                    )
                    {
                        PremiumPlansCard()
                        TypeOfPlans(title = "Recommended Plans")
                        SubscriptionCard(
                            title1 = "Quarterly Subscription",
                            desc1 = "Validity",
                            desc2 = "3 Months",
                            desc3 = "Get your daily dose of news with our 3 month subscription, offering a reliable source of information and analysis to keep you well-informed and engaged.",
                            desc4 = "Pay ₹ 299"
                        )
                        TypeOfPlans(title = "Popular Plans")
                        SubscriptionCard(
                            title1 = "Monthly Subscription",
                            desc1 = "Validity",
                            desc2 = "1 Month",
                            desc3 = "Get your daily dose of news with our 1 month subscription, offering a reliable source of information and analysis to keep you well-informed and engaged.",
                            desc4 = "Pay ₹ 149"
                        )
                        SubscriptionCard(
                            title1 = "Semi-annual Subscription",
                            desc1 = "Validity",
                            desc2 = "6 Months",
                            desc3 = "Get your daily dose of news with our 6 month subscription, offering a reliable source of information and analysis to keep you well-informed and engaged.",
                            desc4 = "Pay ₹ 449"
                        )
                        SubscriptionCard(
                            title1 = "Annual Subscription",
                            desc1 = "Validity",
                            desc2 = "12 Months",
                            desc3 = "Get your daily dose of news with our yearly subscription, offering a reliable source of information and analysis to keep you well-informed and engaged.",
                            desc4 = "Pay ₹ 799"
                        )
                        TypeOfPlans(title = "Trail Plan")
                        SubscriptionCard(
                            title1 = "Free Trail",
                            desc1 = "Validity",
                            desc2 = "1 Week",
                            desc3 = "Get your daily dose of news with our 1 week free subscription, offering a reliable source of information and analysis to keep you well-informed and engaged.",
                            desc4 = "Pay ₹ 0"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PremiumPlansCard(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(Color(0xFFFFA500), shape = RoundedCornerShape(100.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )
    {
        Spacer(modifier = Modifier.width(4.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(45.dp)
                .background(
                    Color(android.graphics.Color.parseColor("#3064FC")),
                    CircleShape
                )
        )
        {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Premium Plans",
                tint = androidx.compose.ui.graphics.Color.White,
                modifier = Modifier
                    .size(25.dp)
            )
        }
        Text(
            text = "Premium Plans",
            fontStyle = FontStyle.Normal,
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.arialbold)),
            fontWeight = FontWeight.Bold,
            color = androidx.compose.ui.graphics.Color.White,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun TypeOfPlans(
    title: String,
)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Text(
            text = title,
            fontStyle = FontStyle.Normal,
            fontSize = 28.sp,
            fontFamily = FontFamily(Font(R.font.arialbold)),
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun SubscriptionCard(
    title1: String,
    desc1 : String,
    desc2 : String,
    desc3 : String,
    desc4 : String
)
{
    Column (
        modifier = Modifier
            .fillMaxWidth()
    )
    {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .border(4.dp, Color(0xFFFFA500), shape = RoundedCornerShape(8.dp)),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(5.dp)
        )
        {
            Column(
                modifier = Modifier
            )
            {
                Text(
                    text = title1,
                    fontStyle = FontStyle.Normal,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.arialbold)),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
                Divider(
                    color = Color(0xFFFFA500),
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Row {
                    Text(
                        text = desc1,
                        fontStyle = FontStyle.Normal,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.arialbold)),
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                    )
                    Text(
                        text = desc2,
                        fontStyle = FontStyle.Normal,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.arialbold)),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row (
                    modifier = Modifier
                        .padding(
                            start = 30.dp,
                            end = 30.dp,
                            bottom = 10.dp
                        )
                        .border(2.dp, Color(0xFFFFA500), shape = RoundedCornerShape(8.dp))
                )
                {
                    Text(
                        text = desc3,
                        fontStyle = FontStyle.Normal,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.arialnormal)),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 8.dp
                        ),
                    colors = ButtonDefaults.buttonColors(Color(0xFF3064FC))
                )
                {
                    Text(
                        text = desc4,
                        fontStyle = FontStyle.Normal,
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.arialbold)),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PremiumPlansPreview(){
    NewsNestTheme {
        Surface(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        )
        {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            )
            {
                PremiumPlansCard()
                TypeOfPlans(title = "Recommended Plans")
                SubscriptionCard(
                    title1 = "Quarterly Subscription",
                    desc1 = "Validity",
                    desc2 = "3 Months",
                    desc3 = "Get your daily dose of news with our 3 month subscription, offering a reliable source of information and analysis to keep you well-informed and engaged.",
                    desc4 = "Pay ₹ 299"
                )
                TypeOfPlans(title = "Popular Plans")
                SubscriptionCard(
                    title1 = "Monthly Subscription",
                    desc1 = "Validity",
                    desc2 = "1 Month",
                    desc3 = "Get your daily dose of news with our 1 month subscription, offering a reliable source of information and analysis to keep you well-informed and engaged.",
                    desc4 = "Pay ₹ 149"
                )
                SubscriptionCard(
                    title1 = "Semi-annual Subscription",
                    desc1 = "Validity",
                    desc2 = "6 Months",
                    desc3 = "Get your daily dose of news with our 6 month subscription, offering a reliable source of information and analysis to keep you well-informed and engaged.",
                    desc4 = "Pay ₹ 449"
                )
                SubscriptionCard(
                    title1 = "Annual Subscription",
                    desc1 = "Validity",
                    desc2 = "12 Months",
                    desc3 = "Get your daily dose of news with our yearly subscription, offering a reliable source of information and analysis to keep you well-informed and engaged.",
                    desc4 = "Pay ₹ 799"
                )
                TypeOfPlans(title = "Trail Plan")
                SubscriptionCard(
                    title1 = "Free Trail",
                    desc1 = "Validity",
                    desc2 = "1 Week",
                    desc3 = "Get your daily dose of news with our 1 week free subscription, offering a reliable source of information and analysis to keep you well-informed and engaged.",
                    desc4 = "Pay ₹ 0"
                )
            }
        }
    }
}
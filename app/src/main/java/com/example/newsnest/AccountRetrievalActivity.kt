package com.example.newsnest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AccountRetrievalActivity : ComponentActivity() {
    private lateinit var databaseHelper: UserDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = UserDatabaseHelper(this)
        setContent {
            AccountRetrievalScreen(this, databaseHelper)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountRetrievalScreen(context: Context, databaseHelper: UserDatabaseHelper) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text(
            text = "ACCOUNT RETRIEVAL",
            color = Color(0xFF3064FC),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 24.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "USERNAME",
                    tint = Color(0xFFFFA500)
                )
            },
            label = { Text(text = "USERNAME", color = Color.Black) },
            placeholder = { Text(text = "ENTER USERNAME", color = Color.DarkGray) },
            textStyle = TextStyle(color = Color.Black)
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "E-MAIL",
                    tint = Color(0xFFFFA500)
                )
            },
            label = { Text(text = "E-MAIL", color = Color.Black) },
            placeholder = { Text(text = "ENTER E-MAIL", color = Color.DarkGray) },
            textStyle = TextStyle(color = Color.Black)
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (error.isNotEmpty()) {
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }

        Button(
            onClick = {
                if (username.isNotEmpty() && email.isNotEmpty()) {
                    val user = databaseHelper.getUserByUsername(username)
                    if (user != null && user.email == email) {
                        error = "Account found! Password: ${user.password}"
                    } else {
                        error = "Invalid username or email"
                    }
                } else {
                    error = "Please fill all fields"
                }
            },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFFA500)),
            modifier = Modifier
                .width(200.dp)
                .padding(top = 16.dp)
        )
        {
            Text(
                text = "RETRIEVE ACCOUNT",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "STILL UNABLE TO RECOVER ?", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            TextButton( onClick = {context.startActivity(Intent(context,ResetPwdByEmailActivity::class.java))} )
            {
                Text(
                    text = "RESET PASSWORD WITH E-MAIL",
                    fontSize = 14.sp,
                    color = Color(0xFF3064FC)
                )
            }
            TextButton( onClick = {context.startActivity(Intent(context,ResetPwdByUNActivity::class.java))} )
            {
                Text(
                    text = "RESET PASSWORD WITH USERNAME",
                    fontSize = 14.sp,
                    color = Color(0xFF3064FC)
                )
            }
        }
    }
}
@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.newsnest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat


class LoginActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            startMainPage(this)
            finish()
            return
        }
        var databaseHelper = UserDatabaseHelper(this)
        setContent {
            LoginScreen(this, databaseHelper)
        }
    }

    private fun startMainPage(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        ContextCompat.startActivity(context, intent, null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(context: Context, databaseHelper: UserDatabaseHelper) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    val sharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    )
    {
        Image(
            painter = painterResource(id = R.drawable.newsnest_login),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .weight(1f)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Divider(
                color = Color(0xFFFFA500),
                thickness = 2.dp,
                modifier = Modifier
                    .width(110.dp)
                    .padding(top = 20.dp, end = 20.dp)
            )
            Text(
                text = "LOGIN",
                color = Color(0xFF3064FC),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,style = MaterialTheme.typography.headlineMedium
            )
            Divider(
                color = Color(0xFFFFA500),
                thickness = 2.dp,
                modifier = Modifier
                    .width(110.dp)
                    .padding(top = 20.dp, start = 20.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = username,
            onValueChange ={username=it},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "USERNAME",
                    tint = Color(0xFFFFA500)
                )
            },
            placeholder = { Text(text = "ENTER USERNAME", color = Color.DarkGray) },
            label = { Text(text = "USERNAME", color = Color.Black)},
            textStyle = TextStyle(color = Color.Black)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange ={password=it},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "PASSWORD",
                    tint = Color(0xFFFFA500)
                )
            },
            placeholder = { Text(text = "ENTER PASSWORD", color = Color.DarkGray) },
            label = { Text(text = "PASSWORD", color = Color.Black)},
            textStyle = TextStyle(color = Color.Black),
            visualTransformation = PasswordVisualTransformation(),
            //colors = TextFieldDefaults.textFieldColors(Color.Black)
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (error.isNotEmpty())
        {
            Toast.makeText(context,error,Toast.LENGTH_SHORT).show()
        }
        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty())
                {
                    val user = databaseHelper.getUserByUsername(username)
                    if (user != null && user.password == password)
                    {
                        error = "Successfully login"
                        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                        context.startActivity(Intent(context,MainActivity::class.java))
                        (context as? Activity)?.finish()
                    }
                    else
                    {
                        error = "Invalid username OR password"
                    }
                }
                else
                {
                    error = "Please fill all fields"
                }
            },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFFFA500)),
            modifier = Modifier
                .width(200.dp)
        )
        {
            Text(text = "LOGIN", fontWeight = FontWeight.Bold, color = Color.White)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "JOIN THE NEWS COMMUNITY",
                color = Color.Black,
                fontSize = 12.sp
            )
            TextButton( onClick = {context.startActivity(Intent(context,RegistrationActivity::class.java))} )
            {
                Text(
                text = "REGISTER NOW",
                color = Color(0xFF3064FC),
                fontSize = 12.sp
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "FORGOT YOUR PASSWORD ?",
                color = Color.Black,
                fontSize = 12.sp
            )
            TextButton( onClick = {context.startActivity(Intent(context,AccountRetrievalActivity::class.java))} )
            {
                Text(
                    text = "RETRIEVE ACCOUNT",
                    color = Color(0xFF3064FC),
                    fontSize = 12.sp
                )
            }
        }
    }
}
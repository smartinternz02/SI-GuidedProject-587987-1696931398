package com.example.newsnest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat

class RegistrationActivity : ComponentActivity() {
    private lateinit var databaseHelper: UserDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = UserDatabaseHelper(this)
        setContent {
            RegistrationScreen(this,databaseHelper)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(context: Context, databaseHelper: UserDatabaseHelper) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Spacer(modifier = Modifier.height(4.dp))
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(20.dp),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(Color(0xFF080404))
        )
        {
            Image(
                painter = painterResource(id = R.drawable.newsnest_register),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .clip(RoundedCornerShape(18.dp))
            )
        }
        Text(
            text = "REGISTER NOW",
            color = Color(0xFF3064FC),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = FontFamily(Font(R.font.arialbold)),
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Divider(
            color = Color(0xFFFFA500),
            thickness = 2.dp,
            modifier = Modifier
                .width(250.dp)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        OutlinedTextField(
            value = username,
            onValueChange ={ username = it },
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
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = password,
            onValueChange ={ password = it },
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
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(4.dp))
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
            placeholder = { Text(text = "ENTER E-MAIL", color = Color.DarkGray) },
            label = { Text(text = "E-MAIL", color = Color.Black)},
            textStyle = TextStyle(color = Color.Black)
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (error.isNotEmpty())
        {
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
        Button(
            onClick =
            {
                if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty())
                {
                    if (isPasswordValid(password))
                    {
                        val user = User(
                            id = null,
                            firstName = username,
                            lastName = null,
                            email = email,
                            password = password
                        )
                        databaseHelper.insertUser(user)
                        error = "User registered successfully"
                        context.startActivity(Intent(context,LoginActivity::class.java))
                    }
                    else
                    {
                        error = "Password should contain at least 5 alphabets and 1 number"
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
                .padding(top = 16.dp)
        )
        {
            Text(text = "REGISTER", fontFamily = FontFamily(Font(R.font.arialbold)), fontSize = 20.sp )
        }
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            Text(text = "HAVE AN ACCOUNT ?", fontSize = 16.sp, color = Color.Black)
            TextButton( onClick = {context.startActivity(Intent(context,LoginActivity::class.java))} )
            {
                Text(
                    text = "LOGIN",
                    fontSize = 16.sp,
                    color = Color(0xFF3064FC)
                )
            }
        }
    }
}

private fun startLoginActivity(context: Context) {
    val intent = Intent(context, LoginActivity::class.java)
    ContextCompat.startActivity(context, intent, null)
}

fun isPasswordValid(password: String): Boolean
{
    val alphabetCount = password.count { it.isLetter() }
    val digitCount = password.count { it.isDigit() }
    return alphabetCount >= 5 && digitCount >= 1
}
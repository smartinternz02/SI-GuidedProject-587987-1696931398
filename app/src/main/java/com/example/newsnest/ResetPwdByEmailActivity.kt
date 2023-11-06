package com.example.newsnest

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ResetPwdByEmailActivity : ComponentActivity() {
    private lateinit var databaseHelper: UserDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = UserDatabaseHelper(this)
        setContent {
            ResetPasswordByEmailScreen(
                context = this,
                databaseHelper = databaseHelper
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordByEmailScreen(
    context: Context,
    databaseHelper: UserDatabaseHelper
)
{
    var email by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text(
            text = "RESET PASSWORD",
            color = Color(0xFF3064FC),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "WITH E-MAIL",
            color = Color(0xFF3064FC),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
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
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = newPassword,
            onValueChange ={ newPassword = it},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "NEW PASSWORD",
                    tint = Color(0xFFFFA500)
                )
            },
            placeholder = { Text(text = "ENTER NEW PASSWORD", color = Color.DarkGray) },
            label = { Text(text = "NEW PASSWORD", color = Color.Black)},
            textStyle = TextStyle(color = Color.Black),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = confirmNewPassword,
            onValueChange ={ confirmNewPassword = it},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "CONFIRM NEW PASSWORD",
                    tint = Color(0xFFFFA500)
                )
            },
            placeholder = { Text(text = "ENTER SAME PASSWORD", color = Color.DarkGray) },
            label = { Text(text = "CONFIRM NEW PASSWORD", color = Color.Black)},
            textStyle = TextStyle(color = Color.Black),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        Button(
            onClick = {
                if (email.isNotEmpty() && newPassword.isNotEmpty() && confirmNewPassword.isNotEmpty()) {
                    if (newPassword == confirmNewPassword) {
                        // Update password in the database
                        val updated = databaseHelper.updatePasswordByEmail(email, newPassword)
                        if (updated != null) {
                            error = "Password updated successfully"
                        } else {
                            error = "Failed to update password"
                        }
                    } else {
                        error = "Passwords do not match"
                    }
                } else {
                    error = "Please fill all fields"
                }
            },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF3064FC)),
            modifier = Modifier
                .width(200.dp)
                .padding(top = 16.dp)
        )
        {
            Text(text = "RESET PASSWORD", fontWeight = FontWeight.Bold)
        }
    }


}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun ResetPasswordByEmailPreview(){
    ResetPasswordByEmailScreen(
        context = LocalContext.current, // Use LocalContext.current to get the context
        databaseHelper = UserDatabaseHelper(LocalContext.current) // Use LocalContext.current to get the context
    )
}

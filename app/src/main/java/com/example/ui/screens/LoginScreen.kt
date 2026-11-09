package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.RoadResQTopBar
import com.example.ui.theme.RoadResQBackground
import com.example.ui.theme.RoadResQCard
import com.example.ui.theme.RoadResQLine
import com.example.ui.theme.RoadResQMuted
import com.example.ui.theme.RoadResQNavy
import com.example.ui.theme.RoadResQRed
import com.example.ui.theme.RoadResQTeal
import com.example.ui.theme.RoadResQTealTint

@Composable
fun LoginScreen(
  onBack: () -> Unit,
  onLoginSuccess: (phone: String) -> Unit,
  onGoToSignup: () -> Unit,
  onSosClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var mobile by remember { mutableStateOf("09171234567") }
  var password by remember { mutableStateOf("demo123") }
  var errorMessage by remember { mutableStateOf("") }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RoadResQBackground)
      .padding(horizontal = 20.dp, vertical = 12.dp)
      .verticalScroll(rememberScrollState())
  ) {
    RoadResQTopBar(
      title = "Log In",
      showBack = true,
      showLogo = false,
      showSos = true,
      onBackClick = onBack,
      onSosClick = onSosClick
    )

    Spacer(modifier = Modifier.height(20.dp))

    Text(
      text = "Welcome Back",
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold,
      color = RoadResQNavy
    )
    Text(
      text = "Log in with your mobile number to access roadside services.",
      fontSize = 14.sp,
      color = RoadResQMuted,
      modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
    )

    Text("Mobile number", fontSize = 14.sp, color = RoadResQMuted, fontWeight = FontWeight.Medium)
    Spacer(modifier = Modifier.height(4.dp))
    OutlinedTextField(
      value = mobile,
      onValueChange = {
        mobile = it
        errorMessage = ""
      },
      placeholder = { Text("09XX XXX XXXX") },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
      shape = RoundedCornerShape(16.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = RoadResQCard,
        unfocusedContainerColor = RoadResQCard,
        focusedBorderColor = RoadResQTeal,
        unfocusedBorderColor = RoadResQLine
      ),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("login_mobile_input")
    )

    Spacer(modifier = Modifier.height(14.dp))

    Text("Password", fontSize = 14.sp, color = RoadResQMuted, fontWeight = FontWeight.Medium)
    Spacer(modifier = Modifier.height(4.dp))
    OutlinedTextField(
      value = password,
      onValueChange = {
        password = it
        errorMessage = ""
      },
      placeholder = { Text("Enter password") },
      visualTransformation = PasswordVisualTransformation(),
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
      shape = RoundedCornerShape(16.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = RoadResQCard,
        unfocusedContainerColor = RoadResQCard,
        focusedBorderColor = RoadResQTeal,
        unfocusedBorderColor = RoadResQLine
      ),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("login_password_input")
    )

    if (errorMessage.isNotBlank()) {
      Text(
        text = errorMessage,
        color = RoadResQRed,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(top = 8.dp)
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Demo credentials chip
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(RoadResQTealTint)
        .border(1.dp, RoadResQTeal.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
        .clickable {
          mobile = "09171234567"
          password = "demo123"
        }
        .padding(12.dp)
    ) {
      Text(
        text = "💡 Demo login credentials (tap to autofill):",
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = RoadResQTeal
      )
      Text(
        text = "Mobile: 09171234567  |  Password: demo123",
        fontSize = 13.sp,
        color = RoadResQNavy,
        modifier = Modifier.padding(top = 2.dp)
      )
    }

    Spacer(modifier = Modifier.height(24.dp))

    Button(
      onClick = {
        val cleanPhone = mobile.replace("\\s".toRegex(), "")
        if (cleanPhone.isBlank() || password.isBlank()) {
          errorMessage = "Please enter your mobile number and password."
        } else if (!cleanPhone.startsWith("09") || cleanPhone.length != 11) {
          errorMessage = "Enter an 11-digit mobile number starting with 09."
        } else {
          onLoginSuccess(cleanPhone)
        }
      },
      colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
      shape = RoundedCornerShape(18.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(58.dp)
        .testTag("login_submit_button")
    ) {
      Text("Log In", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = RoadResQCard)
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = "No account? Create Account",
      color = RoadResQTeal,
      fontSize = 15.sp,
      fontWeight = FontWeight.SemiBold,
      modifier = Modifier
        .clickable { onGoToSignup() }
        .padding(vertical = 8.dp)
        .testTag("login_goto_signup")
    )
  }
}

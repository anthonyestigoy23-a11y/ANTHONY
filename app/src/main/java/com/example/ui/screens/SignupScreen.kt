package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
fun SignupScreen(
  onBack: () -> Unit,
  onSignupSuccess: (name: String, phone: String) -> Unit,
  onSosClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var fullName by remember { mutableStateOf("") }
  var mobile by remember { mutableStateOf("") }
  var password by remember { mutableStateOf("") }
  var errorMessage by remember { mutableStateOf("") }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RoadResQBackground)
      .padding(horizontal = 20.dp, vertical = 12.dp)
      .verticalScroll(rememberScrollState())
  ) {
    RoadResQTopBar(
      title = "Create Account",
      showBack = true,
      showLogo = false,
      showSos = true,
      onBackClick = onBack,
      onSosClick = onSosClick
    )

    Spacer(modifier = Modifier.height(10.dp))

    // Step indicators (3 steps)
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp)
    ) {
      Box(
        modifier = Modifier
          .weight(1f)
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp))
          .background(RoadResQTeal)
      )
      Spacer(modifier = Modifier.padding(horizontal = 3.dp))
      Box(
        modifier = Modifier
          .weight(1f)
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp))
          .background(RoadResQLine)
      )
      Spacer(modifier = Modifier.padding(horizontal = 3.dp))
      Box(
        modifier = Modifier
          .weight(1f)
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp))
          .background(RoadResQLine)
      )
    }

    Text(
      text = "Step 1 of 3 · Your details",
      fontSize = 13.sp,
      fontWeight = FontWeight.Medium,
      color = RoadResQMuted,
      modifier = Modifier.padding(bottom = 16.dp)
    )

    Text("Full name", fontSize = 14.sp, color = RoadResQMuted, fontWeight = FontWeight.Medium)
    Spacer(modifier = Modifier.height(4.dp))
    OutlinedTextField(
      value = fullName,
      onValueChange = {
        fullName = it
        errorMessage = ""
      },
      placeholder = { Text("e.g. Joel Dela Cruz") },
      shape = RoundedCornerShape(16.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = RoadResQCard,
        unfocusedContainerColor = RoadResQCard,
        focusedBorderColor = RoadResQTeal,
        unfocusedBorderColor = RoadResQLine
      ),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("signup_name_input")
    )

    Spacer(modifier = Modifier.height(14.dp))

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
        .testTag("signup_mobile_input")
    )

    Spacer(modifier = Modifier.height(14.dp))

    Text("Password (6 or more characters)", fontSize = 14.sp, color = RoadResQMuted, fontWeight = FontWeight.Medium)
    Spacer(modifier = Modifier.height(4.dp))
    OutlinedTextField(
      value = password,
      onValueChange = {
        password = it
        errorMessage = ""
      },
      placeholder = { Text("Create password") },
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
        .testTag("signup_password_input")
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

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(RoadResQTealTint)
        .padding(12.dp)
    ) {
      Text(
        text = "Next: OTP check (simulated), then add your motorcycle details.",
        fontSize = 13.sp,
        color = RoadResQNavy,
        lineHeight = 18.sp
      )
    }

    Spacer(modifier = Modifier.height(24.dp))

    Button(
      onClick = {
        val cleanPhone = mobile.replace("\\s".toRegex(), "")
        if (fullName.trim().length < 2) {
          errorMessage = "Please enter your full name."
        } else if (!cleanPhone.startsWith("09") || cleanPhone.length != 11) {
          errorMessage = "Enter an 11-digit mobile number starting with 09."
        } else if (password.length < 6) {
          errorMessage = "Password needs at least 6 characters."
        } else {
          onSignupSuccess(fullName.trim(), cleanPhone)
        }
      },
      colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
      shape = RoundedCornerShape(18.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(58.dp)
        .testTag("signup_submit_button")
    ) {
      Text("Continue", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = RoadResQCard)
    }
  }
}

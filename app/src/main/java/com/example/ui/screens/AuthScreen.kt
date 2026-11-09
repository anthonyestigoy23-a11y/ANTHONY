package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.RoadResQTopBar
import com.example.ui.theme.RoadResQBackground
import com.example.ui.theme.RoadResQCard
import com.example.ui.theme.RoadResQMuted
import com.example.ui.theme.RoadResQNavy
import com.example.ui.theme.RoadResQTeal

@Composable
fun AuthScreen(
  onBack: () -> Unit,
  onCreateAccount: () -> Unit,
  onLogin: () -> Unit,
  onSosClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RoadResQBackground)
      .padding(horizontal = 20.dp, vertical = 12.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      RoadResQTopBar(
        showBack = true,
        showLogo = false,
        showSos = true,
        onBackClick = onBack,
        onSosClick = onSosClick
      )

      Spacer(modifier = Modifier.height(40.dp))

      Image(
        painter = painterResource(id = R.drawable.ic_roadresq_logo),
        contentDescription = null,
        modifier = Modifier.size(90.dp)
      )

      Spacer(modifier = Modifier.height(20.dp))

      Text(
        text = "Welcome to RoadResQ",
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        color = RoadResQNavy,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "Create an account or log in to request nearby motorcycle assistance, vulcanizing, and towing.",
        fontSize = 15.sp,
        color = RoadResQMuted,
        textAlign = TextAlign.Center,
        lineHeight = 22.sp
      )
    }

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 24.dp)
    ) {
      Button(
        onClick = onCreateAccount,
        colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(60.dp)
          .testTag("auth_create_account_button")
      ) {
        Text("Create Account", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = RoadResQCard)
      }

      Spacer(modifier = Modifier.height(14.dp))

      OutlinedButton(
        onClick = onLogin,
        border = androidx.compose.foundation.BorderStroke(2.dp, RoadResQTeal),
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(54.dp)
          .testTag("auth_login_button")
      ) {
        Text("Log In", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = RoadResQTeal)
      }
    }
  }
}

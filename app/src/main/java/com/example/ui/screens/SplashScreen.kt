package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.RoadResQBackground
import com.example.ui.theme.RoadResQCard
import com.example.ui.theme.RoadResQLine
import com.example.ui.theme.RoadResQMuted
import com.example.ui.theme.RoadResQNavy
import com.example.ui.theme.RoadResQTeal
import com.example.ui.theme.RoadResQTealTint

@Composable
fun SplashScreen(
  onGetStarted: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RoadResQBackground)
      .verticalScroll(rememberScrollState())
      .padding(horizontal = 20.dp, vertical = 24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Spacer(modifier = Modifier.height(16.dp))

      // Logo
      Image(
        painter = painterResource(id = R.drawable.ic_roadresq_logo),
        contentDescription = "RoadResQ Logo",
        modifier = Modifier
          .size(110.dp)
          .padding(bottom = 12.dp)
      )

      Text(
        text = "Vehicle Trouble?\nWe're Here to Help.",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = RoadResQNavy,
        textAlign = TextAlign.Center,
        lineHeight = 34.sp
      )

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "Connect with nearby repair and roadside assistance providers.",
        fontSize = 16.sp,
        color = RoadResQMuted,
        textAlign = TextAlign.Center,
        lineHeight = 22.sp
      )

      Spacer(modifier = Modifier.height(28.dp))

      // 3 Feature highlights
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
      ) {
        FeatureBadge(
          icon = Icons.Default.LocationOn,
          label = "Find nearby\nprovider"
        )
        FeatureBadge(
          icon = Icons.Default.AccessTime,
          label = "Reliable\nassistance"
        )
        FeatureBadge(
          icon = Icons.Default.VerifiedUser,
          label = "Safe &\nconvenient"
        )
      }

      Spacer(modifier = Modifier.height(30.dp))

      // Disclaimer calm card
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(16.dp))
          .background(RoadResQCard)
          .border(1.5.dp, RoadResQLine, RoundedCornerShape(16.dp))
          .padding(14.dp)
      ) {
        Text(
          text = "RoadResQ matches you with registered providers. It does not repair vehicles itself.",
          fontSize = 13.sp,
          color = RoadResQMuted,
          textAlign = TextAlign.Center,
          lineHeight = 18.sp,
          modifier = Modifier.fillMaxWidth()
        )
      }
    }

    // Get Started Button
    Button(
      onClick = onGetStarted,
      colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
      shape = RoundedCornerShape(18.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(64.dp)
        .padding(top = 24.dp)
        .testTag("splash_get_started_button")
    ) {
      Text(
        text = "Get Started",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = RoadResQCard
      )
    }
  }
}

@Composable
private fun FeatureBadge(
  icon: ImageVector,
  label: String
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.width(96.dp)
  ) {
    Box(
      modifier = Modifier
        .size(54.dp)
        .clip(CircleShape)
        .background(RoadResQTealTint),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = RoadResQTeal,
        modifier = Modifier.size(28.dp)
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = label,
      fontSize = 13.sp,
      fontWeight = FontWeight.Medium,
      color = RoadResQNavy,
      textAlign = TextAlign.Center,
      lineHeight = 16.sp
    )
  }
}

package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.RoadResQNavy
import com.example.ui.theme.RoadResQRed
import com.example.ui.theme.RoadResQRedTint
import com.example.ui.theme.RoadResQTeal

@Composable
fun RoadResQTopBar(
  title: String? = null,
  showBack: Boolean = false,
  showLogo: Boolean = true,
  showSos: Boolean = true,
  onBackClick: () -> Unit = {},
  onSosClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .height(60.dp)
      .padding(horizontal = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.weight(1f)
    ) {
      if (showBack) {
        IconButton(
          onClick = onBackClick,
          modifier = Modifier
            .size(48.dp)
            .testTag("top_bar_back_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = RoadResQNavy
          )
        }
      }

      if (showLogo) {
        Image(
          painter = painterResource(id = R.drawable.ic_roadresq_logo),
          contentDescription = "RoadResQ Logo",
          modifier = Modifier
            .size(34.dp)
            .padding(end = 8.dp)
        )
      }

      Text(
        text = title ?: "RoadResQ",
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          color = RoadResQNavy,
          fontSize = 20.sp
        )
      )
    }

    if (showSos) {
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(14.dp))
          .background(RoadResQRedTint)
          .border(2.dp, RoadResQRed, RoundedCornerShape(14.dp))
          .clickable { onSosClick() }
          .padding(horizontal = 14.dp, vertical = 8.dp)
          .testTag("top_bar_sos_button"),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "SOS",
          color = RoadResQRed,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 14.sp,
          letterSpacing = 1.sp
        )
      }
    }
  }
}

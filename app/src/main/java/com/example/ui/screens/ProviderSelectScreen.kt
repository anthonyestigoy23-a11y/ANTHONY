package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Provider
import com.example.ui.components.RoadResQTopBar
import com.example.ui.theme.RoadResQAmber
import com.example.ui.theme.RoadResQAmberTint
import com.example.ui.theme.RoadResQBackground
import com.example.ui.theme.RoadResQCard
import com.example.ui.theme.RoadResQLine
import com.example.ui.theme.RoadResQMuted
import com.example.ui.theme.RoadResQNavy
import com.example.ui.theme.RoadResQStar
import com.example.ui.theme.RoadResQTeal
import com.example.ui.theme.RoadResQTealTint

@Composable
fun ProviderSelectScreen(
  providers: List<Provider>,
  selectedProviderIndex: Int,
  userLocation: String,
  onSelectProvider: (Int) -> Unit,
  onReviewRequest: () -> Unit,
  onBack: () -> Unit,
  onSosClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RoadResQBackground)
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .verticalScroll(rememberScrollState())
  ) {
    RoadResQTopBar(
      title = "Nearby Providers",
      showBack = true,
      showLogo = false,
      showSos = true,
      onBackClick = onBack,
      onSosClick = onSosClick
    )

    // 4-step progress indicator
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
    ) {
      Box(
        modifier = Modifier
          .weight(1f)
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp))
          .background(RoadResQTeal)
      )
      Spacer(modifier = Modifier.padding(horizontal = 2.dp))
      Box(
        modifier = Modifier
          .weight(1f)
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp))
          .background(RoadResQTeal)
      )
      Spacer(modifier = Modifier.padding(horizontal = 2.dp))
      Box(
        modifier = Modifier
          .weight(1f)
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp))
          .background(RoadResQTeal)
      )
      Spacer(modifier = Modifier.padding(horizontal = 2.dp))
      Box(
        modifier = Modifier
          .weight(1f)
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp))
          .background(RoadResQLine)
      )
    }

    Text(
      text = "Step 3 of 4 · Choose assistance partner",
      fontSize = 13.sp,
      fontWeight = FontWeight.Medium,
      color = RoadResQMuted,
      modifier = Modifier.padding(bottom = 10.dp)
    )

    // Calm demo note
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(RoadResQTealTint)
        .padding(12.dp)
    ) {
      Text(
        text = "Demo data: these are verified sample providers active near your area.",
        fontSize = 13.sp,
        color = RoadResQNavy,
        lineHeight = 18.sp
      )
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Location badge card
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(RoadResQCard)
        .border(1.dp, RoadResQLine, RoundedCornerShape(14.dp))
        .padding(12.dp)
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.LocationOn,
          contentDescription = null,
          tint = RoadResQTeal,
          modifier = Modifier.padding(end = 8.dp)
        )
        Text(
          text = "Your location: ",
          fontSize = 13.sp,
          color = RoadResQMuted
        )
        Text(
          text = userLocation.ifBlank { "Not set" },
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          color = RoadResQNavy
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Providers list
    providers.forEachIndexed { index, provider ->
      val isSelected = index == selectedProviderIndex
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 6.dp)
          .clip(RoundedCornerShape(18.dp))
          .background(if (isSelected) RoadResQTealTint else RoadResQCard)
          .border(
            width = if (isSelected) 2.5.dp else 1.5.dp,
            color = if (isSelected) RoadResQTeal else RoadResQLine,
            shape = RoundedCornerShape(18.dp)
          )
          .clickable { onSelectProvider(index) }
          .padding(16.dp)
          .testTag("provider_item_$index")
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = provider.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = RoadResQNavy
              )
              if (provider.isPriority) {
                Box(
                  modifier = Modifier
                    .padding(start = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(RoadResQAmberTint)
                    .border(1.dp, RoadResQAmber, RoundedCornerShape(8.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = "PRIORITY",
                    color = RoadResQAmber,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = RoadResQStar,
                modifier = Modifier.padding(end = 2.dp)
              )
              Text(
                text = "${provider.rating}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = RoadResQTeal
              )
            }
          }

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = "${provider.distanceEta} · ${provider.specialty}",
            fontSize = 13.sp,
            color = RoadResQMuted
          )

          Spacer(modifier = Modifier.height(6.dp))

          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Call,
              contentDescription = null,
              tint = RoadResQTeal,
              modifier = Modifier.padding(end = 6.dp)
            )
            Text(
              text = provider.phone,
              fontSize = 14.sp,
              fontWeight = FontWeight.SemiBold,
              color = RoadResQTeal
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Button(
      onClick = onReviewRequest,
      colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
      shape = RoundedCornerShape(18.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .testTag("provider_review_button")
    ) {
      Text("Review Request", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = RoadResQCard)
    }
  }
}

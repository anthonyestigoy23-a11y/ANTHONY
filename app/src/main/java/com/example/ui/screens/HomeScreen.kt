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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryAlert
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Motorcycle
import com.example.ui.components.RoadResQTopBar
import com.example.ui.theme.RoadResQAmber
import com.example.ui.theme.RoadResQAmberTint
import com.example.ui.theme.RoadResQBackground
import com.example.ui.theme.RoadResQCard
import com.example.ui.theme.RoadResQLine
import com.example.ui.theme.RoadResQMuted
import com.example.ui.theme.RoadResQNavy
import com.example.ui.theme.RoadResQTeal
import com.example.ui.theme.RoadResQTealTint
import com.example.ui.theme.RoadResQText

@Composable
fun HomeScreen(
  motorcycle: Motorcycle?,
  location: String,
  onEditLocation: () -> Unit,
  onChangeMotorcycle: () -> Unit,
  onRequestAssistance: () -> Unit,
  onQuickService: (String) -> Unit,
  onOpenPms: () -> Unit,
  onOpenPlus: () -> Unit,
  onSosClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var showMoreServices by remember { mutableStateOf(false) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RoadResQBackground)
      .padding(horizontal = 16.dp)
      .verticalScroll(rememberScrollState())
  ) {
    RoadResQTopBar(
      showBack = false,
      showLogo = true,
      showSos = true,
      onSosClick = onSosClick
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
      text = "Vehicle Trouble?\nWe're Here to Help.",
      fontSize = 24.sp,
      fontWeight = FontWeight.Bold,
      color = RoadResQNavy,
      lineHeight = 30.sp
    )
    Text(
      text = "Connect with nearby repair and roadside assistance providers.",
      fontSize = 14.sp,
      color = RoadResQMuted,
      modifier = Modifier.padding(top = 4.dp, bottom = 14.dp)
    )

    // Location Card
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(RoadResQCard)
        .border(1.5.dp, RoadResQLine, RoundedCornerShape(16.dp))
        .clickable { onEditLocation() }
        .padding(14.dp)
        .testTag("home_location_card")
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = RoadResQTeal,
            modifier = Modifier.padding(end = 10.dp)
          )
          Column {
            Text(
              text = "Your location",
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              color = RoadResQMuted
            )
            Text(
              text = location.ifBlank { "Not set. Tap to share or type it." },
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = RoadResQText
            )
          }
        }
        Text(
          text = "Edit",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = RoadResQTeal
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Selected Motorcycle Card
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(RoadResQCard)
        .border(1.5.dp, RoadResQLine, RoundedCornerShape(16.dp))
        .padding(14.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          Icon(
            imageVector = Icons.Default.TwoWheeler,
            contentDescription = null,
            tint = RoadResQTeal,
            modifier = Modifier.padding(end = 10.dp)
          )
          Text(
            text = "${motorcycle?.model ?: "Motorcycle"} · ${motorcycle?.plateNumber ?: "Plate --"}",
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = RoadResQNavy
          )
        }
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(RoadResQTealTint)
            .border(1.dp, RoadResQTeal, RoundedCornerShape(12.dp))
            .clickable { onChangeMotorcycle() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .testTag("home_change_moto_button")
        ) {
          Text(
            text = "Change",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = RoadResQTeal
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    Text(
      text = "Quick services",
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold,
      color = RoadResQNavy
    )

    Spacer(modifier = Modifier.height(10.dp))

    // 2x2 Services Grid
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      ServiceTile(
        title = "Roadside Help",
        icon = Icons.Default.Build,
        onClick = onRequestAssistance,
        modifier = Modifier.weight(1f),
        tag = "service_roadside_help"
      )
      ServiceTile(
        title = "Tire Change",
        icon = Icons.Default.Speed,
        onClick = { onQuickService("Flat tire") },
        modifier = Modifier.weight(1f),
        tag = "service_tire_change"
      )
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      ServiceTile(
        title = "Dead Battery",
        icon = Icons.Default.BatteryAlert,
        onClick = { onQuickService("Dead battery") },
        modifier = Modifier.weight(1f),
        tag = "service_dead_battery"
      )
      ServiceTile(
        title = if (showMoreServices) "Fewer Services" else "More Services",
        icon = if (showMoreServices) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
        onClick = { showMoreServices = !showMoreServices },
        modifier = Modifier.weight(1f),
        tag = "service_more_toggle"
      )
    }

    // Expandable secondary services
    if (showMoreServices) {
      Spacer(modifier = Modifier.height(10.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        ServiceTile(
          title = "PMS Maintenance",
          icon = Icons.Default.HomeRepairService,
          onClick = onOpenPms,
          modifier = Modifier.weight(1f),
          tag = "service_pms"
        )
        ServiceTile(
          title = "Consultation",
          icon = Icons.Default.QuestionAnswer,
          onClick = { onQuickService("Basic consultation") },
          modifier = Modifier.weight(1f),
          tag = "service_consultation"
        )
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // How it works box
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(RoadResQTealTint)
        .padding(14.dp)
    ) {
      Text(
        text = "How it works: Tell us what happened, we match you with a registered provider, and they arrive to help you. RoadResQ does not do repairs itself.",
        fontSize = 13.sp,
        color = RoadResQNavy,
        lineHeight = 18.sp
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    // RoadResQ PLUS Banner
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(RoadResQCard)
        .border(1.5.dp, RoadResQLine, RoundedCornerShape(16.dp))
        .clickable { onOpenPlus() }
        .padding(14.dp)
        .testTag("home_plus_banner")
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "RoadResQ PLUS",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = RoadResQTeal
          )
          Text(
            text = "Priority dispatch & maintenance discounts",
            fontSize = 13.sp,
            color = RoadResQMuted,
            modifier = Modifier.padding(top = 2.dp)
          )
        }
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(RoadResQAmberTint)
            .border(1.dp, RoadResQAmber, RoundedCornerShape(10.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = "PLUS",
            color = RoadResQAmber,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.sp
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Primary action
    Button(
      onClick = onRequestAssistance,
      colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
      shape = RoundedCornerShape(18.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(62.dp)
        .testTag("home_request_assistance_button")
    ) {
      Text(
        text = "Request Assistance",
        fontSize = 19.sp,
        fontWeight = FontWeight.Bold,
        color = RoadResQCard
      )
    }
    Text(
      text = "You will review everything before a request is sent.",
      fontSize = 12.sp,
      color = RoadResQMuted,
      textAlign = TextAlign.Center,
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 6.dp, bottom = 24.dp)
    )
  }
}

@Composable
private fun ServiceTile(
  title: String,
  icon: ImageVector,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  tag: String
) {
  Box(
    modifier = modifier
      .height(96.dp)
      .clip(RoundedCornerShape(18.dp))
      .background(RoadResQCard)
      .border(1.5.dp, RoadResQLine, RoundedCornerShape(18.dp))
      .clickable { onClick() }
      .padding(10.dp)
      .testTag(tag),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = RoadResQTeal,
        modifier = Modifier.size(32.dp)
      )
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = RoadResQText,
        textAlign = TextAlign.Center
      )
    }
  }
}

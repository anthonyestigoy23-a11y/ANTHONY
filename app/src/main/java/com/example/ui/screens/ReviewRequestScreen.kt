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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Motorcycle
import com.example.data.model.Provider
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
fun ReviewRequestScreen(
  motorcycle: Motorcycle?,
  problemText: String,
  location: String,
  provider: Provider,
  priorityBoostActive: Boolean,
  onTogglePriorityBoost: () -> Unit,
  onEditMotorcycle: () -> Unit,
  onEditProblem: () -> Unit,
  onEditLocation: () -> Unit,
  onEditProvider: () -> Unit,
  onOpenPlus: () -> Unit,
  onConfirmRequest: () -> Unit,
  onBack: () -> Unit,
  onSosClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val basePrice = 250
  val boostPrice = if (priorityBoostActive) 40 else 0
  val totalPrice = basePrice + boostPrice

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RoadResQBackground)
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .verticalScroll(rememberScrollState())
  ) {
    RoadResQTopBar(
      title = "Review Request",
      showBack = true,
      showLogo = false,
      showSos = true,
      onBackClick = onBack,
      onSosClick = onSosClick
    )

    // 4-step progress indicator (All 4 complete)
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
    ) {
      repeat(4) {
        Box(
          modifier = Modifier
            .weight(1f)
            .height(6.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(RoadResQTeal)
        )
        if (it < 3) {
          Spacer(modifier = Modifier.padding(horizontal = 2.dp))
        }
      }
    }

    Text(
      text = "Step 4 of 4 · Final review",
      fontSize = 13.sp,
      fontWeight = FontWeight.Medium,
      color = RoadResQMuted,
      modifier = Modifier.padding(bottom = 12.dp)
    )

    // Summary Card
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(18.dp))
        .background(RoadResQCard)
        .border(1.5.dp, RoadResQLine, RoundedCornerShape(18.dp))
        .padding(16.dp)
    ) {
      ReviewRow(
        label = "Motorcycle",
        value = "${motorcycle?.model ?: "Motorcycle"} · ${motorcycle?.plateNumber ?: "Plate --"}",
        onEdit = onEditMotorcycle
      )

      HorizontalDivider(color = RoadResQLine, thickness = 1.dp, modifier = Modifier.padding(vertical = 10.dp))

      ReviewRow(
        label = "Problem",
        value = problemText,
        onEdit = onEditProblem
      )

      HorizontalDivider(color = RoadResQLine, thickness = 1.dp, modifier = Modifier.padding(vertical = 10.dp))

      ReviewRow(
        label = "Location",
        value = location.ifBlank { "Not set" },
        onEdit = onEditLocation
      )

      HorizontalDivider(color = RoadResQLine, thickness = 1.dp, modifier = Modifier.padding(vertical = 10.dp))

      ReviewRow(
        label = "Provider",
        value = "${provider.name} · ${provider.phone}\nETA ${provider.distanceEta}",
        onEdit = onEditProvider
      )

      HorizontalDivider(color = RoadResQLine, thickness = 1.dp, modifier = Modifier.padding(vertical = 10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(text = "Estimated cost (sample)", fontSize = 12.sp, color = RoadResQMuted)
          Text(
            text = "₱$totalPrice" + if (priorityBoostActive) " (incl. Priority Boost)" else "",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = RoadResQTeal
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Priority Boost Card with Switch
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(18.dp))
        .background(RoadResQCard)
        .border(1.5.dp, RoadResQLine, RoundedCornerShape(18.dp))
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "Priority Boost",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = RoadResQNavy
            )
            Box(
              modifier = Modifier
                .padding(start = 6.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(RoadResQAmberTint)
                .border(1.dp, RoadResQAmber, RoundedCornerShape(8.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = "+₱40 sample",
                color = RoadResQAmber,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
          Text(
            text = "Nearest providers are alerted first with instant audio alert.",
            fontSize = 13.sp,
            color = RoadResQMuted,
            modifier = Modifier.padding(top = 2.dp)
          )
        }

        Switch(
          checked = priorityBoostActive,
          onCheckedChange = { onTogglePriorityBoost() },
          colors = SwitchDefaults.colors(
            checkedThumbColor = RoadResQCard,
            checkedTrackColor = RoadResQTeal,
            uncheckedThumbColor = RoadResQCard,
            uncheckedTrackColor = RoadResQLine
          ),
          modifier = Modifier.testTag("review_priority_boost_switch")
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Calm disclaimer
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(RoadResQTealTint)
        .padding(12.dp)
    ) {
      Text(
        text = "Prices are sample estimates. The final repair cost is agreed directly with the provider on-site. SOS and accident help are always free.",
        fontSize = 13.sp,
        color = RoadResQNavy,
        lineHeight = 18.sp
      )
    }

    Spacer(modifier = Modifier.height(24.dp))

    Button(
      onClick = onConfirmRequest,
      colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
      shape = RoundedCornerShape(18.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .testTag("review_confirm_request_button")
    ) {
      Text("Confirm Request", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = RoadResQCard)
    }

    Spacer(modifier = Modifier.height(16.dp))
  }
}

@Composable
private fun ReviewRow(
  label: String,
  value: String,
  onEdit: () -> Unit
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.Top
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(text = label, fontSize = 12.sp, color = RoadResQMuted, fontWeight = FontWeight.Medium)
      Text(
        text = value,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = RoadResQNavy,
        modifier = Modifier.padding(top = 2.dp)
      )
    }
    Text(
      text = "Edit",
      color = RoadResQTeal,
      fontSize = 13.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier
        .clickable { onEdit() }
        .padding(4.dp)
    )
  }
}

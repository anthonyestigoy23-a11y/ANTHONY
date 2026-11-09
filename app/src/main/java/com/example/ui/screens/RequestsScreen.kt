package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AssistanceRequest
import com.example.data.model.RequestStatus
import com.example.ui.components.RoadResQTopBar
import com.example.ui.theme.RoadResQAmber
import com.example.ui.theme.RoadResQAmberTint
import com.example.ui.theme.RoadResQBackground
import com.example.ui.theme.RoadResQCard
import com.example.ui.theme.RoadResQGreen
import com.example.ui.theme.RoadResQGreenTint
import com.example.ui.theme.RoadResQLine
import com.example.ui.theme.RoadResQMuted
import com.example.ui.theme.RoadResQNavy
import com.example.ui.theme.RoadResQRed
import com.example.ui.theme.RoadResQStar
import com.example.ui.theme.RoadResQTeal
import com.example.ui.theme.RoadResQTealTint

@Composable
fun RequestsScreen(
  requests: List<AssistanceRequest>,
  onViewTracking: (requestId: Int) -> Unit,
  onRequestCancel: (request: AssistanceRequest) -> Unit,
  onBack: () -> Unit,
  onSosClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RoadResQBackground)
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .verticalScroll(rememberScrollState())
  ) {
    RoadResQTopBar(
      title = "My Requests",
      showBack = true,
      showLogo = false,
      showSos = true,
      onBackClick = onBack,
      onSosClick = onSosClick
    )

    Spacer(modifier = Modifier.height(10.dp))

    if (requests.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 40.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
            imageVector = Icons.Default.ReceiptLong,
            contentDescription = null,
            tint = RoadResQMuted,
            modifier = Modifier.padding(bottom = 12.dp)
          )
          Text("No requests yet", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = RoadResQNavy)
          Text("Roadside bookings and PMS requests will appear here.", fontSize = 13.sp, color = RoadResQMuted)
        }
      }
    } else {
      requests.forEach { req ->
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(RoadResQCard)
            .border(1.5.dp, RoadResQLine, RoundedCornerShape(18.dp))
            .padding(16.dp)
            .testTag("request_item_${req.id}")
        ) {
          Column {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = req.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = RoadResQNavy,
                modifier = Modifier.weight(1f)
              )

              StatusBadge(status = req.status)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.TwoWheeler,
                contentDescription = null,
                tint = RoadResQTeal,
                modifier = Modifier.padding(end = 6.dp)
              )
              Text(
                text = "${req.motorcycle} · ${req.requestTime}",
                fontSize = 13.sp,
                color = RoadResQMuted
              )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "${req.providerName} · ",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = RoadResQNavy
              )
              Icon(
                imageVector = Icons.Default.Call,
                contentDescription = null,
                tint = RoadResQTeal,
                modifier = Modifier.padding(end = 4.dp)
              )
              Text(
                text = req.providerPhone,
                fontSize = 13.sp,
                color = RoadResQTeal,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                  val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${req.providerPhone.replace(" ", "")}"))
                  context.startActivity(intent)
                }
              )
            }

            if (req.location.isNotBlank()) {
              Spacer(modifier = Modifier.height(3.dp))
              Text(
                text = "Location: ${req.location}",
                fontSize = 13.sp,
                color = RoadResQMuted
              )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
              text = req.price + (if (req.pickupDetails != null) " · ${req.pickupDetails}" else ""),
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = RoadResQTeal
            )

            if (req.rating != null) {
              Spacer(modifier = Modifier.height(6.dp))
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Your rating: ", fontSize = 12.sp, color = RoadResQMuted)
                repeat(req.rating ?: 5) {
                  Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = RoadResQStar,
                    modifier = Modifier.padding(end = 2.dp)
                  )
                }
              }
            }

            // Action buttons
            if (req.status == RequestStatus.PROCESSING) {
              Spacer(modifier = Modifier.height(10.dp))
              OutlinedButton(
                onClick = { onRequestCancel(req) },
                border = androidx.compose.foundation.BorderStroke(1.5.dp, RoadResQRed),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(46.dp)
              ) {
                Text("Cancel Request", color = RoadResQRed, fontWeight = FontWeight.Bold)
              }
            } else if (req.status == RequestStatus.ACCEPTED) {
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = "Cannot cancel: provider already accepted.",
                fontSize = 12.sp,
                color = RoadResQMuted
              )
              Spacer(modifier = Modifier.height(8.dp))
              Button(
                onClick = { onViewTracking(req.id) },
                colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(46.dp)
              ) {
                Text("View Tracking", color = RoadResQCard, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))
  }
}

@Composable
private fun StatusBadge(status: RequestStatus) {
  val (bgColor, textColor, borderColor) = when (status) {
    RequestStatus.PROCESSING -> Triple(RoadResQAmberTint, RoadResQAmber, RoadResQAmber)
    RequestStatus.ACCEPTED -> Triple(RoadResQTealTint, RoadResQTeal, RoadResQTeal)
    RequestStatus.COMPLETED -> Triple(RoadResQGreenTint, RoadResQGreen, RoadResQGreen)
    RequestStatus.CANCELLED -> Triple(Color(0xFFECEFF1), Color(0xFF546E7A), Color(0xFF90A4AE))
  }

  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(8.dp))
      .background(bgColor)
      .border(1.dp, borderColor, RoundedCornerShape(8.dp))
      .padding(horizontal = 8.dp, vertical = 3.dp)
  ) {
    Text(
      text = status.label,
      color = textColor,
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold
    )
  }
}

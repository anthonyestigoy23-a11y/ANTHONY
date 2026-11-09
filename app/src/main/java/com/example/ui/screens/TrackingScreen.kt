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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AssistanceRequest
import com.example.data.model.Provider
import com.example.data.model.RequestStatus
import com.example.ui.components.LiveRouteMap
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
import com.example.ui.theme.RoadResQText

@Composable
fun TrackingScreen(
  request: AssistanceRequest?,
  provider: Provider,
  onOpenChat: () -> Unit,
  onRequestCancel: (AssistanceRequest) -> Unit,
  onServiceCompleted: () -> Unit,
  onBack: () -> Unit,
  onSosClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val isAccepted = request?.status == RequestStatus.ACCEPTED || request?.status == RequestStatus.COMPLETED
  val isProcessing = request?.status == RequestStatus.PROCESSING

  val headerTitle = when (request?.status) {
    RequestStatus.PROCESSING -> "Waiting for provider..."
    RequestStatus.CANCELLED -> "Request cancelled"
    RequestStatus.COMPLETED -> "Service Completed"
    else -> "Help is on the way ✓"
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RoadResQBackground)
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .verticalScroll(rememberScrollState())
  ) {
    RoadResQTopBar(
      title = headerTitle,
      showBack = true,
      showLogo = false,
      showSos = true,
      onBackClick = onBack,
      onSosClick = onSosClick
    )

    Spacer(modifier = Modifier.height(10.dp))

    // Calm safety assurance card
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(RoadResQTealTint)
        .padding(14.dp)
    ) {
      Text(
        text = "You're safe. Stay where you are and keep your hazard lights on. (Demo: provider updates are simulated.)",
        fontSize = 13.sp,
        color = RoadResQNavy,
        lineHeight = 18.sp
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Provider Card
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
              text = request?.providerName ?: provider.name,
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold,
              color = RoadResQNavy
            )
            if (request?.isPriorityBoost == true) {
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

          Spacer(modifier = Modifier.height(4.dp))

          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = null,
              tint = RoadResQStar,
              modifier = Modifier.size(16.dp)
            )
            Text(
              text = "${provider.rating} · ${provider.specialty}",
              fontSize = 13.sp,
              color = RoadResQMuted,
              modifier = Modifier.padding(start = 4.dp)
            )
          }

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "📞 ${request?.providerPhone ?: provider.phone}",
            fontSize = 13.sp,
            color = RoadResQTeal,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable {
              val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${(request?.providerPhone ?: provider.phone).replace(" ", "")}"))
              context.startActivity(intent)
            }
          )
        }

        Text(
          text = provider.distanceEta.split("·").getOrNull(1)?.trim() ?: "6 min",
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
          color = RoadResQTeal
        )
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Animated Live Canvas Map
    LiveRouteMap(
      providerEta = provider.distanceEta.split("·").getOrNull(1)?.trim() ?: "6 min",
      isAccepted = isAccepted
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Step Status Timeline
    Text(
      text = "Service Progress",
      fontSize = 16.sp,
      fontWeight = FontWeight.Bold,
      color = RoadResQNavy
    )
    Spacer(modifier = Modifier.height(8.dp))

    val statusSteps = listOf(
      "Request sent",
      if (isProcessing) "Waiting for provider to accept" else "Provider accepted",
      "On the way",
      "Arrived",
      "Completed"
    )

    val currentStepIndex = when (request?.status) {
      RequestStatus.PROCESSING -> 1
      RequestStatus.ACCEPTED -> 2
      RequestStatus.COMPLETED -> 4
      RequestStatus.CANCELLED -> 0
      null -> 1
    }

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(RoadResQCard)
        .border(1.dp, RoadResQLine, RoundedCornerShape(16.dp))
        .padding(14.dp)
    ) {
      statusSteps.forEachIndexed { idx, stepLabel ->
        val isDone = idx < currentStepIndex
        val isCurrent = idx == currentStepIndex

        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(vertical = 5.dp)
        ) {
          Box(
            modifier = Modifier
              .size(22.dp)
              .clip(CircleShape)
              .background(
                when {
                  isDone -> RoadResQGreen
                  isCurrent -> RoadResQTeal
                  else -> RoadResQLine
                }
              ),
            contentAlignment = Alignment.Center
          ) {
            if (isDone) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = RoadResQCard,
                modifier = Modifier.size(14.dp)
              )
            }
          }
          Spacer(modifier = Modifier.size(10.dp))
          Text(
            text = stepLabel,
            fontSize = 14.sp,
            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
            color = when {
              isCurrent -> RoadResQTeal
              isDone -> RoadResQText
              else -> RoadResQMuted
            }
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Action Buttons: Chat & Call
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      OutlinedButton(
        onClick = onOpenChat,
        border = androidx.compose.foundation.BorderStroke(2.dp, RoadResQTeal),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
          .weight(1f)
          .height(52.dp)
          .testTag("tracking_open_chat")
      ) {
        Icon(imageVector = Icons.Default.Chat, contentDescription = null, tint = RoadResQTeal)
        Text(
          text = "Chat",
          color = RoadResQTeal,
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp,
          modifier = Modifier.padding(start = 6.dp)
        )
      }

      OutlinedButton(
        onClick = {
          val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${(request?.providerPhone ?: provider.phone).replace(" ", "")}"))
          context.startActivity(intent)
        },
        border = androidx.compose.foundation.BorderStroke(2.dp, RoadResQTeal),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
          .weight(1f)
          .height(52.dp)
          .testTag("tracking_call_provider")
      ) {
        Icon(imageVector = Icons.Default.Call, contentDescription = null, tint = RoadResQTeal)
        Text(
          text = "Call",
          color = RoadResQTeal,
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp,
          modifier = Modifier.padding(start = 6.dp)
        )
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Cancel Request Button or Locked Notice
    if (isProcessing && request != null) {
      OutlinedButton(
        onClick = { onRequestCancel(request) },
        border = androidx.compose.foundation.BorderStroke(1.5.dp, RoadResQRed),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("tracking_cancel_button")
      ) {
        Text("Cancel Request", color = RoadResQRed, fontWeight = FontWeight.Bold, fontSize = 15.sp)
      }
    } else if (isAccepted) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(14.dp))
          .background(RoadResQTealTint)
          .padding(10.dp)
      ) {
        Text(
          text = "Cancellation is closed because the provider has already accepted and dispatched.",
          fontSize = 12.sp,
          color = RoadResQMuted,
          textAlign = androidx.compose.ui.text.style.TextAlign.Center,
          modifier = Modifier.fillMaxWidth()
        )
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Service Completed Button
    Button(
      onClick = onServiceCompleted,
      colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
      shape = RoundedCornerShape(18.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(58.dp)
        .testTag("tracking_service_completed_button")
    ) {
      Text("Service Completed", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = RoadResQCard)
    }

    Spacer(modifier = Modifier.height(16.dp))
  }
}

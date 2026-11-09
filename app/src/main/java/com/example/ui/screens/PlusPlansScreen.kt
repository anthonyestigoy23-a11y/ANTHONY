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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PlusPlan
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
fun PlusPlansScreen(
  plans: List<PlusPlan>,
  selectedPlanIndex: Int,
  onSelectPlan: (Int) -> Unit,
  onConfirmPlan: (Int) -> Unit,
  onBack: () -> Unit,
  onSosClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var activePlanIndex by remember { mutableIntStateOf(selectedPlanIndex) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RoadResQBackground)
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .verticalScroll(rememberScrollState())
  ) {
    RoadResQTopBar(
      title = "RoadResQ PLUS",
      showBack = true,
      showLogo = false,
      showSos = true,
      onBackClick = onBack,
      onSosClick = onSosClick
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
      text = "Plans & Priority Options",
      fontSize = 22.sp,
      fontWeight = FontWeight.Bold,
      color = RoadResQNavy
    )
    Text(
      text = "Tap a plan to review included coverage and roadside perks.",
      fontSize = 14.sp,
      color = RoadResQMuted,
      modifier = Modifier.padding(top = 4.dp, bottom = 14.dp)
    )

    plans.forEachIndexed { index, plan ->
      val isSelected = index == activePlanIndex
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
          .clickable {
            activePlanIndex = index
            onSelectPlan(index)
          }
          .padding(16.dp)
          .testTag("plan_card_$index")
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = plan.name,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = RoadResQNavy
              )
              if (plan.badge != null) {
                Box(
                  modifier = Modifier
                    .padding(start = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(RoadResQAmberTint)
                    .border(1.dp, RoadResQAmber, RoundedCornerShape(8.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Text(
                    text = plan.badge,
                    color = RoadResQAmber,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }
            Text(
              text = plan.price,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = RoadResQTeal
            )
          }

          if (isSelected) {
            Spacer(modifier = Modifier.height(12.dp))
            plan.benefits.forEach { benefit ->
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 3.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = null,
                  tint = RoadResQTeal,
                  modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                  text = benefit,
                  fontSize = 13.sp,
                  color = RoadResQText,
                  lineHeight = 18.sp
                )
              }
            }
          } else {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "Tap to see full benefits",
              fontSize = 13.sp,
              color = RoadResQMuted
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Always free safety disclaimer
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(RoadResQCard)
        .border(1.dp, RoadResQLine, RoundedCornerShape(14.dp))
        .padding(12.dp)
    ) {
      Text(
        text = "Safety help (SOS, emergency calls, accident dispatch) is always 100% free for all riders.",
        fontSize = 13.sp,
        color = RoadResQNavy,
        lineHeight = 18.sp
      )
    }

    Spacer(modifier = Modifier.height(24.dp))

    val ctaText = when (activePlanIndex) {
      0 -> "Continue with Free"
      1 -> "Use Boost on my next request"
      else -> "Join PLUS · ₱99/month"
    }

    Button(
      onClick = { onConfirmPlan(activePlanIndex) },
      colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
      shape = RoundedCornerShape(18.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .testTag("plan_confirm_button")
    ) {
      Text(ctaText, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = RoadResQCard)
    }

    Spacer(modifier = Modifier.height(16.dp))
  }
}

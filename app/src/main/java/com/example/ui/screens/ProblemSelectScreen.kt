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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
fun ProblemSelectScreen(
  selectedProblem: String,
  otherDescription: String,
  onSelectProblem: (String) -> Unit,
  onChangeOtherDescription: (String) -> Unit,
  onNext: () -> Unit,
  onBack: () -> Unit,
  onSosClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var errorText by remember { mutableStateOf("") }

  val problems = listOf(
    Pair("Flat tire", Icons.Default.Speed),
    Pair("Dead battery", Icons.Default.BatteryAlert),
    Pair("Out of fuel", Icons.Default.LocalGasStation),
    Pair("Won't start", Icons.Default.Warning),
    Pair("Towing", Icons.Default.Build),
    Pair("Other", Icons.Default.HelpOutline)
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RoadResQBackground)
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .verticalScroll(rememberScrollState())
  ) {
    RoadResQTopBar(
      title = "What happened?",
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
          .background(RoadResQLine)
      )
      Spacer(modifier = Modifier.padding(horizontal = 2.dp))
      Box(
        modifier = Modifier
          .weight(1f)
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp))
          .background(RoadResQLine)
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
      text = "Step 1 of 4 · Identify the issue",
      fontSize = 13.sp,
      fontWeight = FontWeight.Medium,
      color = RoadResQMuted,
      modifier = Modifier.padding(bottom = 10.dp)
    )

    // Calm advice
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(RoadResQTealTint)
        .padding(12.dp)
    ) {
      Text(
        text = "Take a breath. Pick the closest match. You can provide additional details below.",
        fontSize = 13.sp,
        color = RoadResQNavy,
        lineHeight = 18.sp
      )
    }

    Spacer(modifier = Modifier.height(14.dp))

    // 3 rows of 2 columns
    for (i in problems.indices step 2) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        val p1 = problems[i]
        ProblemGridTile(
          title = p1.first,
          icon = p1.second,
          isSelected = selectedProblem == p1.first,
          onClick = {
            onSelectProblem(p1.first)
            errorText = ""
          },
          modifier = Modifier.weight(1f)
        )

        if (i + 1 < problems.size) {
          val p2 = problems[i + 1]
          ProblemGridTile(
            title = p2.first,
            icon = p2.second,
            isSelected = selectedProblem == p2.first,
            onClick = {
              onSelectProblem(p2.first)
              errorText = ""
            },
            modifier = Modifier.weight(1f)
          )
        }
      }
    }

    if (selectedProblem == "Other") {
      Spacer(modifier = Modifier.height(14.dp))
      Text(
        text = "Describe the problem",
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = RoadResQNavy
      )
      Spacer(modifier = Modifier.height(4.dp))
      OutlinedTextField(
        value = otherDescription,
        onValueChange = {
          onChangeOtherDescription(it)
          errorText = ""
        },
        placeholder = { Text("What's wrong? / Ilarawan ang sira (hal. umiinit ang makina, chain cut, etc.)") },
        minLines = 3,
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = RoadResQCard,
          unfocusedContainerColor = RoadResQCard,
          focusedBorderColor = RoadResQTeal,
          unfocusedBorderColor = RoadResQLine
        ),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("problem_other_description_input")
      )
    }

    if (errorText.isNotBlank()) {
      Text(
        text = errorText,
        color = RoadResQRed,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(top = 8.dp)
      )
    }

    Spacer(modifier = Modifier.height(24.dp))

    Button(
      onClick = {
        if (selectedProblem == "Other" && otherDescription.trim().isEmpty()) {
          errorText = "Please describe the problem so the provider knows what to prepare."
        } else {
          onNext()
        }
      },
      colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
      shape = RoundedCornerShape(18.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .testTag("problem_next_button")
    ) {
      Text("Next", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = RoadResQCard)
    }
  }
}

@Composable
private fun ProblemGridTile(
  title: String,
  icon: ImageVector,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .height(100.dp)
      .clip(RoundedCornerShape(18.dp))
      .background(if (isSelected) RoadResQTealTint else RoadResQCard)
      .border(
        width = if (isSelected) 2.5.dp else 1.5.dp,
        color = if (isSelected) RoadResQTeal else RoadResQLine,
        shape = RoundedCornerShape(18.dp)
      )
      .clickable { onClick() }
      .padding(10.dp)
      .testTag("problem_tile_$title"),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = if (isSelected) RoadResQTeal else RoadResQNavy,
        modifier = Modifier.size(32.dp)
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = if (isSelected) RoadResQTeal else RoadResQNavy,
        textAlign = TextAlign.Center
      )
      if (isSelected) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(top = 2.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = RoadResQTeal,
            modifier = Modifier.size(14.dp)
          )
          Text(
            text = "Selected",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = RoadResQTeal
          )
        }
      }
    }
  }
}

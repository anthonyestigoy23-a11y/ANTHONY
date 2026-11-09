package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.ui.theme.RoadResQStar
import com.example.ui.theme.RoadResQStarEmpty
import com.example.ui.theme.RoadResQTeal

@Composable
fun RatingScreen(
  onBack: () -> Unit,
  onSubmitRating: (rating: Int, feedback: String?) -> Unit,
  onSosClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var rating by remember { mutableIntStateOf(5) }
  var feedback by remember { mutableStateOf("") }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RoadResQBackground)
      .padding(horizontal = 20.dp, vertical = 8.dp)
      .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    RoadResQTopBar(
      title = "Receipt & Feedback",
      showBack = true,
      showLogo = false,
      showSos = true,
      onBackClick = onBack,
      onSosClick = onSosClick
    )

    Spacer(modifier = Modifier.height(24.dp))

    Text(
      text = "You're all set 🎉",
      fontSize = 26.sp,
      fontWeight = FontWeight.Bold,
      color = RoadResQNavy,
      textAlign = TextAlign.Center
    )

    Text(
      text = "How was your roadside service?",
      fontSize = 16.sp,
      color = RoadResQMuted,
      modifier = Modifier.padding(top = 6.dp)
    )

    Text(
      text = "Estimated ₱250 · Cash / GCash (simulated)",
      fontSize = 15.sp,
      fontWeight = FontWeight.SemiBold,
      color = RoadResQTeal,
      modifier = Modifier.padding(top = 6.dp)
    )

    Spacer(modifier = Modifier.height(28.dp))

    // Interactive 5 stars row
    Row(
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      for (i in 1..5) {
        val isFilled = i <= rating
        Icon(
          imageVector = if (isFilled) Icons.Default.Star else Icons.Outlined.StarOutline,
          contentDescription = "$i stars",
          tint = if (isFilled) RoadResQStar else RoadResQStarEmpty,
          modifier = Modifier
            .size(48.dp)
            .clickable { rating = i }
            .padding(4.dp)
            .testTag("rating_star_$i")
        )
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    OutlinedTextField(
      value = feedback,
      onValueChange = { feedback = it },
      placeholder = { Text("Write a quick review or note for the provider (optional)") },
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
        .testTag("rating_feedback_input")
    )

    Spacer(modifier = Modifier.height(30.dp))

    Button(
      onClick = {
        onSubmitRating(rating, feedback.ifBlank { null })
      },
      colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
      shape = RoundedCornerShape(18.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .testTag("rating_submit_button")
    ) {
      Text("Submit & Pay", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = RoadResQCard)
    }
  }
}

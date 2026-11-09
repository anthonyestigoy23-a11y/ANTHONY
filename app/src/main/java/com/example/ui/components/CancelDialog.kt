package com.example.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.RoadResQCard
import com.example.ui.theme.RoadResQLine
import com.example.ui.theme.RoadResQMuted
import com.example.ui.theme.RoadResQNavy
import com.example.ui.theme.RoadResQRed
import com.example.ui.theme.RoadResQTeal

@Composable
fun CancelDialog(
  onKeepRequest: () -> Unit,
  onConfirmCancel: () -> Unit
) {
  Dialog(onDismissRequest = onKeepRequest) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = RoadResQCard,
      modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, RoadResQLine, RoundedCornerShape(24.dp))
    ) {
      Column(
        modifier = Modifier.padding(20.dp)
      ) {
        Text(
          text = "Cancel this request?",
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
          color = RoadResQNavy
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "The provider has not accepted yet, so you can cancel for free.",
          fontSize = 15.sp,
          color = RoadResQMuted,
          lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
          onClick = onKeepRequest,
          colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag("dialog_keep_request")
        ) {
          Text("Keep Request", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
          onClick = onConfirmCancel,
          border = androidx.compose.foundation.BorderStroke(2.dp, RoadResQRed),
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("dialog_confirm_cancel")
        ) {
          Text("Yes, Cancel", color = RoadResQRed, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        }
      }
    }
  }
}

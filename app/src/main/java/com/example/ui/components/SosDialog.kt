package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.RoadResQCard
import com.example.ui.theme.RoadResQLine
import com.example.ui.theme.RoadResQMuted
import com.example.ui.theme.RoadResQNavy
import com.example.ui.theme.RoadResQRed
import com.example.ui.theme.RoadResQRedTint
import com.example.ui.theme.RoadResQTeal
import com.example.ui.theme.RoadResQText

@Composable
fun SosDialog(
  currentLocation: String,
  onDismiss: () -> Unit
) {
  val context = LocalContext.current

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = RoadResQCard,
      modifier = Modifier
        .fillMaxWidth()
        .border(2.dp, RoadResQRed, RoundedCornerShape(24.dp))
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .size(54.dp)
            .clip(CircleShape)
            .background(RoadResQRedTint),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Emergency Alert",
            tint = RoadResQRed,
            modifier = Modifier.size(30.dp)
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "Emergency SOS",
          fontSize = 22.sp,
          fontWeight = FontWeight.Bold,
          color = RoadResQRed
        )

        Text(
          text = "Are you in immediate danger or need police/medical rescue?",
          fontSize = 14.sp,
          color = RoadResQMuted,
          modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
          textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        // National Emergency Hotline (911)
        Button(
          onClick = {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:911"))
            context.startActivity(intent)
          },
          colors = ButtonDefaults.buttonColors(containerColor = RoadResQRed),
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag("sos_dial_911")
        ) {
          Icon(imageVector = Icons.Default.Call, contentDescription = null, tint = RoadResQCard)
          Spacer(modifier = Modifier.width(8.dp))
          Text("Call 911 (Police / Rescue)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Roadside Rescue Hotline
        OutlinedButton(
          onClick = {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:09179997377"))
            context.startActivity(intent)
          },
          border = androidx.compose.foundation.BorderStroke(2.dp, RoadResQTeal),
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("sos_dial_roadresq")
        ) {
          Icon(imageVector = Icons.Default.Call, contentDescription = null, tint = RoadResQTeal)
          Spacer(modifier = Modifier.width(8.dp))
          Text("Call RoadResQ Dispatch", color = RoadResQTeal, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Share Location via SMS
        OutlinedButton(
          onClick = {
            val locText = if (currentLocation.isNotBlank()) currentLocation else "my current road location"
            val message = "EMERGENCY: I am stranded with vehicle trouble. My location is: $locText. Sent via RoadResQ."
            val intent = Intent(Intent.ACTION_VIEW).apply {
              data = Uri.parse("sms:")
              putExtra("sms_body", message)
            }
            context.startActivity(intent)
          },
          border = androidx.compose.foundation.BorderStroke(1.dp, RoadResQLine),
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("sos_share_location")
        ) {
          Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = RoadResQNavy)
          Spacer(modifier = Modifier.width(8.dp))
          Text("Share Location via SMS", color = RoadResQNavy, fontWeight = FontWeight.Medium, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Safety Calm Notice
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(RoadResQRedTint)
            .padding(10.dp)
        ) {
          Text(
            text = "Stay safe: Move to the roadside shoulder, keep hazard lights on, and wear your helmet/reflective vest.",
            fontSize = 12.sp,
            color = RoadResQRed,
            lineHeight = 16.sp
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Button(
          onClick = onDismiss,
          colors = ButtonDefaults.buttonColors(containerColor = RoadResQLine),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.fillMaxWidth().height(46.dp)
        ) {
          Text("Close", color = RoadResQText, fontWeight = FontWeight.SemiBold)
        }
      }
    }
  }
}

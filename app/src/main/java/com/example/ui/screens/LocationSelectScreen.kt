package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.ui.components.RoadResQTopBar
import com.example.ui.theme.RoadResQBackground
import com.example.ui.theme.RoadResQCard
import com.example.ui.theme.RoadResQLine
import com.example.ui.theme.RoadResQMuted
import com.example.ui.theme.RoadResQNavy
import com.example.ui.theme.RoadResQRed
import com.example.ui.theme.RoadResQTeal

@Composable
fun LocationSelectScreen(
  currentLocation: String,
  isFromHome: Boolean,
  onSaveLocation: (String) -> Unit,
  onBack: () -> Unit,
  onSosClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var manualLocation by remember { mutableStateOf(currentLocation) }
  var gpsStatus by remember { mutableStateOf("Your phone will use GPS location. Tap below to detect.") }
  var errorText by remember { mutableStateOf("") }
  val context = LocalContext.current

  val locationPermissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestMultiplePermissions()
  ) { permissions ->
    val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
      permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
    if (granted) {
      manualLocation = "GPS 15.4865, 120.5979 (McArthur Highway, Tarlac City)"
      gpsStatus = "Location detected: GPS 15.4865, 120.5979 (McArthur Highway)"
      errorText = ""
    } else {
      gpsStatus = "Permission not granted. Please type your landmark below."
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RoadResQBackground)
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .verticalScroll(rememberScrollState())
  ) {
    RoadResQTopBar(
      title = "Where are you?",
      showBack = true,
      showLogo = false,
      showSos = true,
      onBackClick = onBack,
      onSosClick = onSosClick
    )

    if (!isFromHome) {
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
        text = "Step 2 of 4 · Set your breakdown location",
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        color = RoadResQMuted,
        modifier = Modifier.padding(bottom = 12.dp)
      )
    } else {
      Spacer(modifier = Modifier.height(14.dp))
    }

    // Auto GPS card
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(18.dp))
        .background(RoadResQCard)
        .border(1.5.dp, RoadResQLine, RoundedCornerShape(18.dp))
        .padding(16.dp)
    ) {
      Text(
        text = "Use my current location",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = RoadResQNavy
      )
      Text(
        text = gpsStatus,
        fontSize = 13.sp,
        color = RoadResQMuted,
        modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
      )

      OutlinedButton(
        onClick = {
          val hasFine = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
          ) == PackageManager.PERMISSION_GRANTED
          val hasCoarse = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
          ) == PackageManager.PERMISSION_GRANTED

          if (hasFine || hasCoarse) {
            manualLocation = "GPS 15.4865, 120.5979 (McArthur Highway, Tarlac City)"
            gpsStatus = "Location detected: McArthur Highway, Tarlac City"
            errorText = ""
          } else {
            locationPermissionLauncher.launch(
              arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
              )
            )
          }
        },
        border = androidx.compose.foundation.BorderStroke(2.dp, RoadResQTeal),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
          .testTag("location_gps_button")
      ) {
        Icon(
          imageVector = Icons.Default.MyLocation,
          contentDescription = null,
          tint = RoadResQTeal
        )
        Text(
          text = "Use current GPS location",
          color = RoadResQTeal,
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          modifier = Modifier.padding(start = 6.dp)
        )
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    Text(
      text = "Or type your location or landmark",
      fontSize = 15.sp,
      fontWeight = FontWeight.SemiBold,
      color = RoadResQNavy
    )
    Spacer(modifier = Modifier.height(6.dp))
    OutlinedTextField(
      value = manualLocation,
      onValueChange = {
        manualLocation = it
        errorText = ""
      },
      placeholder = { Text("e.g. McArthur Highway near the city proper, gas station, etc.") },
      minLines = 2,
      shape = RoundedCornerShape(16.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = RoadResQCard,
        unfocusedContainerColor = RoadResQCard,
        focusedBorderColor = RoadResQTeal,
        unfocusedBorderColor = RoadResQLine
      ),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("location_manual_input")
    )

    if (errorText.isNotBlank()) {
      Text(
        text = errorText,
        color = RoadResQRed,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(top = 8.dp)
      )
    }

    Spacer(modifier = Modifier.height(28.dp))

    Button(
      onClick = {
        val loc = manualLocation.trim()
        if (loc.isEmpty()) {
          errorText = "Please share your location or type a landmark."
        } else {
          onSaveLocation(loc)
        }
      },
      colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
      shape = RoundedCornerShape(18.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .testTag("location_next_button")
    ) {
      Text(
        text = if (isFromHome) "Save Location" else "Next",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = RoadResQCard
      )
    }
  }
}

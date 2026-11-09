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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PmsPackage
import com.example.ui.components.RoadResQTopBar
import com.example.ui.theme.RoadResQBackground
import com.example.ui.theme.RoadResQCard
import com.example.ui.theme.RoadResQLine
import com.example.ui.theme.RoadResQMuted
import com.example.ui.theme.RoadResQNavy
import com.example.ui.theme.RoadResQRed
import com.example.ui.theme.RoadResQTeal
import com.example.ui.theme.RoadResQTealTint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PmsBookingScreen(
  packages: List<PmsPackage>,
  onConfirmBooking: (pmsPackage: PmsPackage, date: String, time: String, pickupEnabled: Boolean, pickupAddress: String, pickupTime: String) -> Unit,
  onBack: () -> Unit,
  onSosClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val todayFormatted = remember {
    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
  }
  var selectedPackageIndex by remember { mutableIntStateOf(0) }
  var bookingDate by remember { mutableStateOf(todayFormatted) }
  var bookingTime by remember { mutableStateOf("15:30") }
  var pickupEnabled by remember { mutableStateOf(false) }
  var pickupAddress by remember { mutableStateOf("") }
  var pickupTime by remember { mutableStateOf("08:00") }
  var errorText by remember { mutableStateOf("") }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RoadResQBackground)
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .verticalScroll(rememberScrollState())
  ) {
    RoadResQTopBar(
      title = "Home Service & PMS",
      showBack = true,
      showLogo = false,
      showSos = true,
      onBackClick = onBack,
      onSosClick = onSosClick
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
      text = "Periodic Maintenance & Home Service",
      fontSize = 20.sp,
      fontWeight = FontWeight.Bold,
      color = RoadResQNavy
    )
    Text(
      text = "Scheduled preventive maintenance and home repair at your doorstep.",
      fontSize = 14.sp,
      color = RoadResQMuted,
      modifier = Modifier.padding(top = 4.dp, bottom = 14.dp)
    )

    Text(
      text = "Choose a service package",
      fontSize = 16.sp,
      fontWeight = FontWeight.Bold,
      color = RoadResQNavy
    )
    Spacer(modifier = Modifier.height(8.dp))

    packages.forEachIndexed { index, pkg ->
      val isSelected = index == selectedPackageIndex
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 5.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(if (isSelected) RoadResQTealTint else RoadResQCard)
          .border(
            width = if (isSelected) 2.5.dp else 1.5.dp,
            color = if (isSelected) RoadResQTeal else RoadResQLine,
            shape = RoundedCornerShape(16.dp)
          )
          .clickable { selectedPackageIndex = index }
          .padding(14.dp)
          .testTag("pms_package_$index")
      ) {
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = pkg.title,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = RoadResQNavy
            )
            Text(
              text = pkg.price,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = RoadResQTeal
            )
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = pkg.description,
            fontSize = 13.sp,
            color = RoadResQMuted
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = "Date and time",
      fontSize = 16.sp,
      fontWeight = FontWeight.Bold,
      color = RoadResQNavy
    )
    Spacer(modifier = Modifier.height(6.dp))

    OutlinedTextField(
      value = bookingDate,
      onValueChange = { bookingDate = it },
      label = { Text("Date (YYYY-MM-DD)") },
      shape = RoundedCornerShape(14.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = RoadResQCard,
        unfocusedContainerColor = RoadResQCard,
        focusedBorderColor = RoadResQTeal,
        unfocusedBorderColor = RoadResQLine
      ),
      modifier = Modifier.fillMaxWidth().testTag("pms_date_input")
    )

    Spacer(modifier = Modifier.height(10.dp))

    // Time shortcut chips
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      listOf("09:00" to "9:00 AM", "13:00" to "1:00 PM", "16:00" to "4:00 PM").forEach { (timeVal, timeLabel) ->
        val isSel = bookingTime == timeVal
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSel) RoadResQTealTint else RoadResQCard)
            .border(1.5.dp, if (isSel) RoadResQTeal else RoadResQLine, RoundedCornerShape(12.dp))
            .clickable { bookingTime = timeVal }
            .padding(vertical = 10.dp),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = timeLabel,
            fontSize = 13.sp,
            fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
            color = if (isSel) RoadResQTeal else RoadResQNavy
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
      value = bookingTime,
      onValueChange = { bookingTime = it },
      label = { Text("Custom Time (HH:MM)") },
      shape = RoundedCornerShape(14.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = RoadResQCard,
        unfocusedContainerColor = RoadResQCard,
        focusedBorderColor = RoadResQTeal,
        unfocusedBorderColor = RoadResQLine
      ),
      modifier = Modifier.fillMaxWidth().testTag("pms_time_input")
    )

    Spacer(modifier = Modifier.height(14.dp))

    // Pick-up and Delivery Toggle Card
    Column(
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
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Pick-up and delivery",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = RoadResQNavy
          )
          Text(
            text = "Shop picks up motorcycle at your chosen time and brings it back.",
            fontSize = 13.sp,
            color = RoadResQMuted,
            modifier = Modifier.padding(top = 2.dp)
          )
        }

        Switch(
          checked = pickupEnabled,
          onCheckedChange = { pickupEnabled = it },
          colors = SwitchDefaults.colors(
            checkedThumbColor = RoadResQCard,
            checkedTrackColor = RoadResQTeal,
            uncheckedThumbColor = RoadResQCard,
            uncheckedTrackColor = RoadResQLine
          ),
          modifier = Modifier.testTag("pms_pickup_switch")
        )
      }

      if (pickupEnabled) {
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
          value = pickupAddress,
          onValueChange = {
            pickupAddress = it
            errorText = ""
          },
          placeholder = { Text("Pick-up address or landmark") },
          shape = RoundedCornerShape(14.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = RoadResQCard,
            unfocusedContainerColor = RoadResQCard,
            focusedBorderColor = RoadResQTeal,
            unfocusedBorderColor = RoadResQLine
          ),
          modifier = Modifier.fillMaxWidth().testTag("pms_pickup_address_input")
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = pickupTime,
          onValueChange = { pickupTime = it },
          label = { Text("Pick-up time") },
          shape = RoundedCornerShape(14.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = RoadResQCard,
            unfocusedContainerColor = RoadResQCard,
            focusedBorderColor = RoadResQTeal,
            unfocusedBorderColor = RoadResQLine
          ),
          modifier = Modifier.fillMaxWidth().testTag("pms_pickup_time_input")
        )
      }
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
        if (bookingDate.isBlank() || bookingTime.isBlank()) {
          errorText = "Please choose a date and time."
        } else if (pickupEnabled && pickupAddress.trim().isEmpty()) {
          errorText = "Please enter the pick-up address."
        } else {
          val pkg = packages.getOrElse(selectedPackageIndex) { packages.first() }
          onConfirmBooking(pkg, bookingDate, bookingTime, pickupEnabled, pickupAddress, pickupTime)
        }
      },
      colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
      shape = RoundedCornerShape(18.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .testTag("pms_confirm_button")
    ) {
      Text("Confirm Booking", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = RoadResQCard)
    }

    Spacer(modifier = Modifier.height(16.dp))
  }
}

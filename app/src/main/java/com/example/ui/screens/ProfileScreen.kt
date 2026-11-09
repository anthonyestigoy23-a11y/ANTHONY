package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.TwoWheeler
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Motorcycle
import com.example.ui.components.RoadResQTopBar
import com.example.ui.theme.RoadResQBackground
import com.example.ui.theme.RoadResQCard
import com.example.ui.theme.RoadResQLine
import com.example.ui.theme.RoadResQMuted
import com.example.ui.theme.RoadResQNavy
import com.example.ui.theme.RoadResQRed
import com.example.ui.theme.RoadResQTeal

@Composable
fun ProfileScreen(
  userName: String,
  userPhone: String,
  motorcycles: List<Motorcycle>,
  onUpdateProfile: (name: String, phone: String) -> Unit,
  onAddMotorcycleClick: () -> Unit,
  onLogout: () -> Unit,
  onBack: () -> Unit,
  onSosClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var name by remember { mutableStateOf(userName) }
  var phone by remember { mutableStateOf(userPhone) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RoadResQBackground)
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .verticalScroll(rememberScrollState())
  ) {
    RoadResQTopBar(
      title = "My Profile",
      showBack = true,
      showLogo = false,
      showSos = true,
      onBackClick = onBack,
      onSosClick = onSosClick
    )

    Spacer(modifier = Modifier.height(14.dp))

    Text(
      text = "Account Details",
      fontSize = 20.sp,
      fontWeight = FontWeight.Bold,
      color = RoadResQNavy
    )
    Spacer(modifier = Modifier.height(12.dp))

    Text("Full name", fontSize = 14.sp, color = RoadResQMuted, fontWeight = FontWeight.Medium)
    Spacer(modifier = Modifier.height(4.dp))
    OutlinedTextField(
      value = name,
      onValueChange = { name = it },
      shape = RoundedCornerShape(16.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = RoadResQCard,
        unfocusedContainerColor = RoadResQCard,
        focusedBorderColor = RoadResQTeal,
        unfocusedBorderColor = RoadResQLine
      ),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("profile_name_input")
    )

    Spacer(modifier = Modifier.height(14.dp))

    Text("Contact number", fontSize = 14.sp, color = RoadResQMuted, fontWeight = FontWeight.Medium)
    Spacer(modifier = Modifier.height(4.dp))
    OutlinedTextField(
      value = phone,
      onValueChange = { phone = it },
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
      shape = RoundedCornerShape(16.dp),
      colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = RoadResQCard,
        unfocusedContainerColor = RoadResQCard,
        focusedBorderColor = RoadResQTeal,
        unfocusedBorderColor = RoadResQLine
      ),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("profile_phone_input")
    )

    Spacer(modifier = Modifier.height(20.dp))

    Text(
      text = "My motorcycles",
      fontSize = 17.sp,
      fontWeight = FontWeight.Bold,
      color = RoadResQNavy
    )
    Spacer(modifier = Modifier.height(8.dp))

    motorcycles.forEach { moto ->
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp)
          .clip(RoundedCornerShape(14.dp))
          .background(RoadResQCard)
          .border(1.dp, RoadResQLine, RoundedCornerShape(14.dp))
          .padding(12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.TwoWheeler,
              contentDescription = null,
              tint = RoadResQTeal,
              modifier = Modifier.padding(end = 10.dp)
            )
            Column {
              Text(
                text = moto.model,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = RoadResQNavy
              )
              Text(
                text = "${moto.type} · ${moto.plateNumber}",
                fontSize = 13.sp,
                color = RoadResQMuted
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    OutlinedButton(
      onClick = onAddMotorcycleClick,
      border = androidx.compose.foundation.BorderStroke(1.5.dp, RoadResQTeal),
      shape = RoundedCornerShape(14.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .testTag("profile_add_moto_button")
    ) {
      Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = RoadResQTeal)
      Text(
        text = "+ Add motorcycle",
        color = RoadResQTeal,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        modifier = Modifier.padding(start = 6.dp)
      )
    }

    Spacer(modifier = Modifier.height(24.dp))

    Button(
      onClick = { onUpdateProfile(name.trim(), phone.trim()) },
      colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
      shape = RoundedCornerShape(18.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(58.dp)
        .testTag("profile_save_button")
    ) {
      Text("Save Profile", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = RoadResQCard)
    }

    Spacer(modifier = Modifier.height(12.dp))

    OutlinedButton(
      onClick = onLogout,
      border = androidx.compose.foundation.BorderStroke(1.5.dp, RoadResQRed),
      shape = RoundedCornerShape(18.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(52.dp)
        .testTag("profile_logout_button")
    ) {
      Text("Log Out", color = RoadResQRed, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }

    Spacer(modifier = Modifier.height(20.dp))
  }
}

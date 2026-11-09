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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Motorcycle
import com.example.ui.components.RoadResQTopBar
import com.example.ui.theme.RoadResQBackground
import com.example.ui.theme.RoadResQCard
import com.example.ui.theme.RoadResQLine
import com.example.ui.theme.RoadResQMuted
import com.example.ui.theme.RoadResQNavy
import com.example.ui.theme.RoadResQTeal
import com.example.ui.theme.RoadResQTealTint

@Composable
fun MotorcycleSelectScreen(
  motorcycles: List<Motorcycle>,
  selectedIndex: Int,
  onSelectIndex: (Int) -> Unit,
  onAddMotorcycle: (model: String, plate: String, type: String) -> Unit,
  onContinue: () -> Unit,
  onBack: () -> Unit,
  onSosClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var showAddForm by remember { mutableStateOf(false) }
  var newModel by remember { mutableStateOf("") }
  var newPlate by remember { mutableStateOf("") }
  var selectedType by remember { mutableStateOf("Motorcycle") }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RoadResQBackground)
      .padding(horizontal = 20.dp, vertical = 12.dp)
      .verticalScroll(rememberScrollState())
  ) {
    RoadResQTopBar(
      title = "My Garage",
      showBack = true,
      showLogo = false,
      showSos = true,
      onBackClick = onBack,
      onSosClick = onSosClick
    )

    Spacer(modifier = Modifier.height(14.dp))

    Text(
      text = "Which motorcycle today?",
      fontSize = 22.sp,
      fontWeight = FontWeight.Bold,
      color = RoadResQNavy
    )
    Text(
      text = "Choose the one you're riding now so providers know what parts and tools to bring.",
      fontSize = 14.sp,
      color = RoadResQMuted,
      modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
    )

    // Motorcycle cards list
    motorcycles.forEachIndexed { index, moto ->
      val isSelected = index == selectedIndex
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
          .clickable { onSelectIndex(index) }
          .padding(16.dp)
          .testTag("motorcycle_item_$index")
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
          ) {
            Icon(
              imageVector = Icons.Default.TwoWheeler,
              contentDescription = null,
              tint = if (isSelected) RoadResQTeal else RoadResQNavy,
              modifier = Modifier.padding(end = 12.dp)
            )
            Column {
              Text(
                text = moto.model,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = RoadResQNavy
              )
              Text(
                text = "${moto.type} · Plate ${moto.plateNumber}",
                fontSize = 13.sp,
                color = RoadResQMuted,
                modifier = Modifier.padding(top = 2.dp)
              )
            }
          }

          if (isSelected) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = RoadResQTeal,
                modifier = Modifier.padding(end = 4.dp)
              )
              Text(
                text = "Selected",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = RoadResQTeal
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Expandable Add Form
    if (showAddForm) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(18.dp))
          .background(RoadResQCard)
          .border(1.5.dp, RoadResQLine, RoundedCornerShape(18.dp))
          .padding(16.dp)
      ) {
        Text(
          text = "Add a motorcycle",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = RoadResQNavy
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = newModel,
          onValueChange = { newModel = it },
          placeholder = { Text("Brand and model (e.g. Honda Click 125)") },
          shape = RoundedCornerShape(14.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = RoadResQCard,
            unfocusedContainerColor = RoadResQCard,
            focusedBorderColor = RoadResQTeal,
            unfocusedBorderColor = RoadResQLine
          ),
          modifier = Modifier.fillMaxWidth().testTag("add_moto_model_input")
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = newPlate,
          onValueChange = { newPlate = it },
          placeholder = { Text("Plate number (e.g. TLC 4821)") },
          shape = RoundedCornerShape(14.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = RoadResQCard,
            unfocusedContainerColor = RoadResQCard,
            focusedBorderColor = RoadResQTeal,
            unfocusedBorderColor = RoadResQLine
          ),
          modifier = Modifier.fillMaxWidth().testTag("add_moto_plate_input")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Type selection chips
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          listOf("Motorcycle", "With sidecar").forEach { typeOption ->
            val sel = selectedType == typeOption
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(if (sel) RoadResQTealTint else RoadResQCard)
                .border(1.5.dp, if (sel) RoadResQTeal else RoadResQLine, RoundedCornerShape(12.dp))
                .clickable { selectedType = typeOption }
                .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
              Text(
                text = typeOption,
                fontSize = 13.sp,
                fontWeight = if (sel) FontWeight.Bold else FontWeight.Medium,
                color = if (sel) RoadResQTeal else RoadResQNavy
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Button(
          onClick = {
            if (newModel.isNotBlank()) {
              onAddMotorcycle(
                newModel.trim(),
                newPlate.trim().ifEmpty { "No plate yet" },
                if (selectedType == "With sidecar") "Tricycle (sidecar)" else "Motorcycle"
              )
              newModel = ""
              newPlate = ""
              showAddForm = false
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
          Text("Save Motorcycle", fontWeight = FontWeight.Bold, color = RoadResQCard)
        }
      }
    } else {
      OutlinedButton(
        onClick = { showAddForm = true },
        border = androidx.compose.foundation.BorderStroke(2.dp, RoadResQTeal),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .testTag("add_motorcycle_expand_button")
      ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = RoadResQTeal)
        Text(
          text = "+ Add another motorcycle",
          color = RoadResQTeal,
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          modifier = Modifier.padding(start = 6.dp)
        )
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    Button(
      onClick = onContinue,
      colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
      shape = RoundedCornerShape(18.dp),
      modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .testTag("motorcycle_continue_button")
    ) {
      Text("Continue", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = RoadResQCard)
    }
  }
}

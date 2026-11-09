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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.data.model.ChatMessage
import com.example.ui.components.RoadResQTopBar
import com.example.ui.theme.RoadResQBackground
import com.example.ui.theme.RoadResQCard
import com.example.ui.theme.RoadResQLine
import com.example.ui.theme.RoadResQMuted
import com.example.ui.theme.RoadResQNavy
import com.example.ui.theme.RoadResQTeal
import com.example.ui.theme.RoadResQText

@Composable
fun ChatScreen(
  providerName: String,
  messages: List<ChatMessage>,
  onSendMessage: (String) -> Unit,
  onBack: () -> Unit,
  onSosClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var inputText by remember { mutableStateOf("") }
  val listState = rememberLazyListState()

  LaunchedEffect(messages.size) {
    if (messages.isNotEmpty()) {
      listState.animateScrollToItem(messages.size - 1)
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(RoadResQBackground)
      .padding(horizontal = 16.dp, vertical = 8.dp)
  ) {
    RoadResQTopBar(
      title = "Chat · $providerName",
      showBack = true,
      showLogo = false,
      showSos = true,
      onBackClick = onBack,
      onSosClick = onSosClick
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Messages list
    LazyColumn(
      state = listState,
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .padding(vertical = 4.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(messages, key = { it.id }) { msg ->
        val isMe = msg.isFromMe
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth(0.82f)
              .clip(
                RoundedCornerShape(
                  topStart = 16.dp,
                  topEnd = 16.dp,
                  bottomStart = if (isMe) 16.dp else 4.dp,
                  bottomEnd = if (isMe) 4.dp else 16.dp
                )
              )
              .background(if (isMe) RoadResQTeal else RoadResQCard)
              .border(
                width = if (isMe) 0.dp else 1.dp,
                color = RoadResQLine,
                shape = RoundedCornerShape(16.dp)
              )
              .padding(horizontal = 14.dp, vertical = 10.dp)
          ) {
            Column {
              Text(
                text = msg.text,
                color = if (isMe) RoadResQCard else RoadResQText,
                fontSize = 15.sp,
                lineHeight = 20.sp
              )
              Text(
                text = msg.timeText,
                color = if (isMe) RoadResQCard.copy(alpha = 0.7f) else RoadResQMuted,
                fontSize = 11.sp,
                modifier = Modifier
                  .align(Alignment.End)
                  .padding(top = 4.dp)
              )
            }
          }
        }
      }
    }

    // Input row
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      OutlinedTextField(
        value = inputText,
        onValueChange = { inputText = it },
        placeholder = { Text("Type a message to provider...") },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = RoadResQCard,
          unfocusedContainerColor = RoadResQCard,
          focusedBorderColor = RoadResQTeal,
          unfocusedBorderColor = RoadResQLine
        ),
        modifier = Modifier
          .weight(1f)
          .testTag("chat_input_field")
      )

      Spacer(modifier = Modifier.width(8.dp))

      Button(
        onClick = {
          val text = inputText.trim()
          if (text.isNotEmpty()) {
            onSendMessage(text)
            inputText = ""
          }
        },
        colors = ButtonDefaults.buttonColors(containerColor = RoadResQTeal),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
          .height(56.dp)
          .testTag("chat_send_button")
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.Send,
          contentDescription = "Send",
          tint = RoadResQCard
        )
      }
    }
  }
}

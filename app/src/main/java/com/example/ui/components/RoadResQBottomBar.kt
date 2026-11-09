package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ListAlt
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.Screen
import com.example.ui.theme.RoadResQCard
import com.example.ui.theme.RoadResQLine
import com.example.ui.theme.RoadResQMuted
import com.example.ui.theme.RoadResQTeal

sealed class NavItem(val screen: Screen, val label: String, val activeIcon: ImageVector, val inactiveIcon: ImageVector, val tag: String) {
  object Home : NavItem(Screen.HOME, "Home", Icons.Filled.Home, Icons.Outlined.Home, "nav_item_home")
  object Requests : NavItem(Screen.REQUESTS, "Requests", Icons.Filled.ListAlt, Icons.Outlined.ListAlt, "nav_item_requests")
  object Chat : NavItem(Screen.CHAT, "Chat", Icons.Filled.ChatBubbleOutline, Icons.Outlined.ChatBubbleOutline, "nav_item_chat")
  object Profile : NavItem(Screen.PROFILE, "Profile", Icons.Filled.Person, Icons.Outlined.Person, "nav_item_profile")
}

@Composable
fun RoadResQBottomBar(
  currentScreen: Screen,
  onSelectTab: (Screen) -> Unit,
  modifier: Modifier = Modifier
) {
  val items = listOf(NavItem.Home, NavItem.Requests, NavItem.Chat, NavItem.Profile)

  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(RoadResQCard)
      .border(width = 1.dp, color = RoadResQLine)
      .windowInsetsPadding(WindowInsets.navigationBars)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(64.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      items.forEach { item ->
        val selected = item.screen == currentScreen
        Column(
          modifier = Modifier
            .weight(1f)
            .clickable { onSelectTab(item.screen) }
            .padding(vertical = 8.dp)
            .testTag(item.tag),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = if (selected) item.activeIcon else item.inactiveIcon,
            contentDescription = item.label,
            tint = if (selected) RoadResQTeal else RoadResQMuted,
            modifier = Modifier.size(24.dp)
          )
          Text(
            text = item.label,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = if (selected) RoadResQTeal else RoadResQMuted,
            modifier = Modifier.padding(top = 2.dp)
          )
        }
      }
    }
  }
}

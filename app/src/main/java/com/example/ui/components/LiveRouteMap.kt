package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.RoadResQAmber
import com.example.ui.theme.RoadResQAmberTint
import com.example.ui.theme.RoadResQCard
import com.example.ui.theme.RoadResQLine
import com.example.ui.theme.RoadResQNavy
import com.example.ui.theme.RoadResQTeal
import com.example.ui.theme.RoadResQTealTint

@Composable
fun LiveRouteMap(
  providerEta: String,
  isAccepted: Boolean,
  modifier: Modifier = Modifier
) {
  val transition = rememberInfiniteTransition(label = "map_anim")
  val pulse by transition.animateFloat(
    initialValue = 8f,
    targetValue = 22f,
    animationSpec = infiniteRepeatable(
      animation = tween(1400, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "pulse"
  )
  val pulseAlpha by transition.animateFloat(
    initialValue = 0.6f,
    targetValue = 0f,
    animationSpec = infiniteRepeatable(
      animation = tween(1400, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "pulse_alpha"
  )

  val carProgress by transition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(4000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "car_prog"
  )

  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(160.dp)
      .clip(RoundedCornerShape(16.dp))
      .background(Color(0xFFE6EEF2))
      .border(2.dp, RoadResQLine, RoundedCornerShape(16.dp))
  ) {
    Canvas(modifier = Modifier.fillMaxSize()) {
      val w = size.width
      val h = size.height

      // Background grid / map roads
      drawLine(
        color = Color(0xFFD3E0E8),
        start = Offset(0f, h * 0.45f),
        end = Offset(w, h * 0.45f),
        strokeWidth = 14f,
        cap = StrokeCap.Round
      )
      drawLine(
        color = Color(0xFFD3E0E8),
        start = Offset(w * 0.35f, 0f),
        end = Offset(w * 0.35f, h),
        strokeWidth = 12f,
        cap = StrokeCap.Round
      )
      drawLine(
        color = Color(0xFFD3E0E8),
        start = Offset(w * 0.75f, 0f),
        end = Offset(w * 0.75f, h),
        strokeWidth = 10f,
        cap = StrokeCap.Round
      )

      // Start and End points for provider route
      val startX = w * 0.15f
      val startY = h * 0.80f
      val endX = w * 0.85f
      val endY = h * 0.28f

      // Bezier route path
      val path = Path().apply {
        moveTo(startX, startY)
        cubicTo(w * 0.35f, h * 0.65f, w * 0.55f, h * 0.25f, endX, endY)
      }

      // Draw dashed route
      drawPath(
        path = path,
        color = RoadResQAmber,
        style = Stroke(
          width = 8f,
          pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 16f), 0f),
          cap = StrokeCap.Round
        )
      )

      // User destination beacon (End point)
      drawCircle(
        color = RoadResQTeal.copy(alpha = pulseAlpha),
        radius = pulse * 1.5f,
        center = Offset(endX, endY)
      )
      drawCircle(
        color = RoadResQTeal,
        radius = 12f,
        center = Offset(endX, endY)
      )
      drawCircle(
        color = RoadResQCard,
        radius = 5f,
        center = Offset(endX, endY)
      )

      // Provider location (Start or moving along path if accepted)
      val currentProgress = if (isAccepted) (0.2f + 0.6f * carProgress) else 0f
      val provX = startX + (endX - startX) * currentProgress
      val provY = startY + (endY - startY) * currentProgress

      drawCircle(
        color = RoadResQAmber.copy(alpha = 0.3f),
        radius = 18f,
        center = Offset(provX, provY)
      )
      drawCircle(
        color = RoadResQAmber,
        radius = 11f,
        center = Offset(provX, provY)
      )
      drawCircle(
        color = RoadResQCard,
        radius = 4f,
        center = Offset(provX, provY)
      )
    }

    // Top overlay pill with status/ETA
    Surface(
      shape = RoundedCornerShape(12.dp),
      color = RoadResQCard.copy(alpha = 0.95f),
      shadowElevation = 2.dp,
      modifier = Modifier
        .align(Alignment.TopEnd)
        .padding(8.dp)
    ) {
      Text(
        text = if (isAccepted) "ETA $providerEta" else "Locating nearest unit...",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = if (isAccepted) RoadResQTeal else RoadResQAmber,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
      )
    }

    // Bottom legend
    Surface(
      shape = RoundedCornerShape(10.dp),
      color = RoadResQCard.copy(alpha = 0.92f),
      modifier = Modifier
        .align(Alignment.BottomStart)
        .padding(8.dp)
    ) {
      Text(
        text = "🟡 Provider  🔵 Your Location",
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        color = RoadResQNavy,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
      )
    }
  }
}

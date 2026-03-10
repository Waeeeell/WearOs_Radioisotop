package com.example.proyectoreloj.presentation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.proyectoreloj.presentation.viewmodel.MotivationalViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    motivationalViewModel: MotivationalViewModel,
    onNavigateToActivity: () -> Unit,
    onNavigateToSos: () -> Unit,
    onNavigateToEcg: () -> Unit
) {
    // ── Collect the dynamic motivational message from the ViewModel ──────
    val motivationalMessage by motivationalViewModel.currentHomeMessage.collectAsState()

    // ── Time formatting ─────────────────────────────────────────────────
    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    val currentDate = SimpleDateFormat("d 'de' MMMM", Locale("es", "ES")).format(Date())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // ── Bottom arc (progress ring) ──────────────────────────────────
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            val strokeWidth = 10.dp.toPx()
            val arcSize = Size(size.width - strokeWidth, size.height - strokeWidth)
            val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)

            // Blue arc — left side
            drawArc(
                color = Color(0xFF2196F3),
                startAngle = 150f,
                sweepAngle = 60f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Teal arc — bottom center
            drawArc(
                color = Color(0xFF26C6DA),
                startAngle = 210f,
                sweepAngle = 50f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Green arc — right side
            drawArc(
                color = Color(0xFF4CAF50),
                startAngle = 350f,
                sweepAngle = 50f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        // ── "7 días" labels on arcs ─────────────────────────────────────
        Text(
            text = "7 días",
            style = MaterialTheme.typography.caption1.copy(
                fontSize = 9.sp,
                color = Color(0xFF2196F3)
            ),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 12.dp, top = 40.dp)
        )

        Text(
            text = "7 días",
            style = MaterialTheme.typography.caption1.copy(
                fontSize = 9.sp,
                color = Color(0xFF4CAF50)
            ),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp, top = 40.dp)
        )

        // ── Content column ──────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            // Battery indicator
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFF2E7D32),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "⚡ 72%",
                    style = MaterialTheme.typography.caption1.copy(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )
            }

            // Date
            Text(
                text = currentDate,
                style = MaterialTheme.typography.body2.copy(
                    fontSize = 13.sp,
                    color = Color(0xFFB0B0B0)
                )
            )

            // Time — large display
            Text(
                text = currentTime,
                style = MaterialTheme.typography.display1.copy(
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )

            // ── Dynamic motivational message (reads from ViewModel) ─────
            // Tap to cycle to the next message
            Text(
                text = motivationalMessage,
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 13.sp,
                    lineHeight = 18.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { motivationalViewModel.nextHomeMessage() }
            )

            // ── Heart rate & SpO2 row ───────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Heart rate
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onNavigateToEcg() }
                ) {
                    Text(
                        text = "❤️",
                        fontSize = 20.sp
                    )
                    Text(
                        text = "78bpm",
                        style = MaterialTheme.typography.body1.copy(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    )
                }

                // Divider
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(28.dp)
                        .background(Color(0xFF444444))
                )

                // SpO2
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF26C6DA)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "O₂",
                            style = MaterialTheme.typography.caption1.copy(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                    Text(
                        text = "95%",
                        style = MaterialTheme.typography.body1.copy(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

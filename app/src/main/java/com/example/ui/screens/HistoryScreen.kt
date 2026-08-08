package com.example.ui.screens

import androidx.compose.foundation.layout.Box


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.QrCode


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.data.ScanHistoryEntity

@Composable
fun HistoryScreen(
    historyList: List<ScanHistoryEntity>,
    onToggleFavorite: (ScanHistoryEntity) -> Unit,
    onDelete: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color(0xFFF8FAFC)) // Slate 50
    ) {
        // App Bar
        androidx.compose.foundation.layout.Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            Text(
                text = "HISTORY",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = androidx.compose.ui.graphics.Color(0xFF1E293B) // Slate 800
                )
            )
        }

        if (historyList.isEmpty()) {
            Column(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
            ) {
                Text(
                    text = "No scan history yet.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = androidx.compose.ui.graphics.Color(0xFF64748B) // Slate 500
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
            ) {
                items(historyList) { item ->
                    androidx.compose.foundation.layout.Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(androidx.compose.ui.graphics.Color.White, androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
                            .border(1.dp, androidx.compose.ui.graphics.Color(0xFFE2E8F0), androidx.compose.foundation.shape.RoundedCornerShape(20.dp))
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(androidx.compose.ui.graphics.Color(0xFFF1F5F9), androidx.compose.foundation.shape.CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Default.QrCode,
                                contentDescription = "QR Code",
                                tint = androidx.compose.ui.graphics.Color(0xFF475569) // Slate 600
                            )
                        }
                        
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = item.rawValue,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                                    color = androidx.compose.ui.graphics.Color(0xFF1E293B) // Slate 800
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = item.type,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                                    color = androidx.compose.ui.graphics.Color(0xFF64748B) // Slate 500
                                )
                            )
                        }
                        
                        IconButton(onClick = { onToggleFavorite(item) }) {
                            Icon(
                                imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Toggle Favorite",
                                tint = if (item.isFavorite) androidx.compose.ui.graphics.Color(0xFF2563EB) else androidx.compose.ui.graphics.Color(0xFF94A3B8)
                            )
                        }
                        IconButton(onClick = { onDelete(item.id) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = androidx.compose.ui.graphics.Color(0xFFEF4444) // Red 500
                            )
                        }
                    }
                }
            }
        }
    }
}

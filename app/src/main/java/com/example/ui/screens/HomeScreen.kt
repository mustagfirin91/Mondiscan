package com.example.ui.screens

import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History


import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ui.scanner.CameraPreview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(onScanSuccess: (String, String, String) -> Unit) {
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    var scannedValue by remember { mutableStateOf<String?>(null) }
    
    LaunchedEffect(scannedValue) {
        if (scannedValue != null) {
            delay(3000)
            scannedValue = null // Reset after 3 seconds so we can scan again
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top App Bar
        androidx.compose.foundation.layout.Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.foundation.layout.Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
            ) {
                // We'll use the Mondiscan Logo here
                androidx.compose.foundation.Image(
                    painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.mondiscan_logo),
                    contentDescription = "Mondiscan Logo",
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "Mondiscan",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        color = Color(0xFF1E293B) // Slate 800
                    )
                )
            }
            androidx.compose.foundation.layout.Row(
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
            ) {
                androidx.compose.material3.IconButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFF1F5F9), androidx.compose.foundation.shape.CircleShape)
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.FlashlightOn,
                        contentDescription = "Flashlight",
                        tint = Color(0xFF475569) // Slate 600
                    )
                }
                androidx.compose.material3.IconButton(
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFF1F5F9), androidx.compose.foundation.shape.CircleShape)
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.PhotoLibrary,
                        contentDescription = "Gallery",
                        tint = Color(0xFF475569) // Slate 600
                    )
                }
            }
        }

        // Camera Preview Area
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(Color(0xFF0F172A), androidx.compose.foundation.shape.RoundedCornerShape(32.dp))
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(32.dp))
        ) {
            if (cameraPermissionState.status.isGranted) {
                CameraPreview(
                    onBarcodeDetected = { barcode ->
                        if (scannedValue != barcode.rawValue) {
                            barcode.rawValue?.let { value ->
                                scannedValue = value
                                onScanSuccess(value, barcode.format.toString(), barcode.valueType.toString())
                            }
                        }
                    }
                )
                
                // Scanner overlay UI
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(256.dp)
                            .border(2.dp, Color.White.copy(alpha = 0.2f), androidx.compose.foundation.shape.RoundedCornerShape(24.dp))
                    ) {
                        // Corner brackets can be drawn here, skipping for brevity or add a generic box
                    }
                    Text(
                        text = "ALIGN QR CODE INSIDE",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                            letterSpacing = 2.sp,
                            color = Color.White.copy(alpha = 0.6f)
                        ),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 280.dp)
                    )
                }
                
                // Status Bar inside Camera View
                androidx.compose.foundation.layout.Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 24.dp)
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.4f), androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                        .border(1.dp, Color.White.copy(alpha = 0.1f), androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "MODE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                letterSpacing = 1.5.sp,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        )
                        Text(
                            text = "Auto Detect",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
                                color = Color.White
                            )
                        )
                    }
                    androidx.compose.foundation.layout.Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
                    ) {
                        Box(modifier = Modifier.size(8.dp).background(Color(0xFF4ADE80), androidx.compose.foundation.shape.CircleShape))
                        Text(
                            text = "Ready",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        )
                    }
                }

                if (scannedValue != null) {
                    Text(
                        text = "Scanned: $scannedValue",
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 16.dp)
                            .background(Color.Black.copy(alpha = 0.7f), androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                            .padding(16.dp),
                        color = Color.White
                    )
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                ) {
                    Text(
                        "Camera permission is required for scanning.",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                        Text("Grant Permission")
                    }
                }
            }
        }

        // Bottom Actions
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF8FAFC), androidx.compose.foundation.shape.RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            androidx.compose.foundation.layout.Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "QUICK ACTIONS",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = Color(0xFF1E293B)
                    )
                )
                Text(
                    text = "VIEW ALL",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = Color(0xFF2563EB)
                    ),
                    modifier = Modifier.clickable { /* TODO */ }
                )
            }
            androidx.compose.foundation.layout.Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
            ) {
                androidx.compose.foundation.layout.Row(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White, androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                        .border(1.dp, Color(0xFFE2E8F0), androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                        .padding(16.dp)
                        .clickable { /* TODO */ },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(0xFFEFF6FF), androidx.compose.foundation.shape.RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.Add,
                            contentDescription = "Create",
                            tint = Color(0xFF2563EB)
                        )
                    }
                    Column {
                        Text(
                            text = "Create",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                color = Color(0xFF1E293B)
                            )
                        )
                        Text(
                            text = "NEW QR",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                                color = Color(0xFF64748B)
                            )
                        )
                    }
                }
                androidx.compose.foundation.layout.Row(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White, androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                        .border(1.dp, Color(0xFFE2E8F0), androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
                        .padding(16.dp)
                        .clickable { /* TODO */ },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color(0xFFFFF7ED), androidx.compose.foundation.shape.RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.History,
                            contentDescription = "Recent",
                            tint = Color(0xFFF59E0B)
                        )
                    }
                    Column {
                        Text(
                            text = "Recent",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                color = Color(0xFF1E293B)
                            )
                        )
                        Text(
                            text = "HISTORY",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                                color = Color(0xFF64748B)
                            )
                        )
                    }
                }
            }
        }
    }
}

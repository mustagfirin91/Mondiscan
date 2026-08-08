package com.example

import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.ScanDatabase
import com.example.data.ScanRepository
import com.example.ui.MainViewModel
import com.example.ui.MainViewModelFactory
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.MondiscanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = ScanDatabase.getDatabase(this)
        val repository = ScanRepository(database.scanHistoryDao())

        setContent {
            MondiscanTheme {
                val viewModel: MainViewModel = viewModel(factory = MainViewModelFactory(repository))
                MondiscanApp(viewModel)
            }
        }
    }
}

@Composable
fun MondiscanApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = androidx.compose.ui.graphics.Color.White,
                tonalElevation = 0.dp,
                modifier = Modifier.height(80.dp)
            ) {
                val colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                    selectedIconColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                    selectedTextColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                    indicatorColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("HOME", style = androidx.compose.material3.MaterialTheme.typography.labelSmall.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)) },
                    selected = currentDestination?.hierarchy?.any { it.route == "home" } == true,
                    colors = colors,
                    onClick = {
                        navController.navigate("home") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.History, contentDescription = "History") },
                    label = { Text("HISTORY", style = androidx.compose.material3.MaterialTheme.typography.labelSmall.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)) },
                    selected = currentDestination?.hierarchy?.any { it.route == "history" } == true,
                    colors = colors,
                    onClick = {
                        navController.navigate("history") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("SETTINGS", style = androidx.compose.material3.MaterialTheme.typography.labelSmall.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)) },
                    selected = currentDestination?.hierarchy?.any { it.route == "settings" } == true,
                    colors = colors,
                    onClick = {
                        navController.navigate("settings") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            composable("home") {
                HomeScreen(
                    onScanSuccess = { rawValue, format, type ->
                        viewModel.addScan(rawValue, format, type)
                    }
                )
            }
            composable("history") {
                val historyList by viewModel.history.collectAsStateWithLifecycle()
                HistoryScreen(
                    historyList = historyList,
                    onToggleFavorite = { scan -> viewModel.toggleFavorite(scan) },
                    onDelete = { id -> viewModel.deleteScan(id) }
                )
            }
            composable("settings") {
                SettingsScreen()
            }
        }
    }
}

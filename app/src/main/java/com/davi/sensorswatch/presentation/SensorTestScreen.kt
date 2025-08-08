package com.davi.sensorswatch.presentation

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.davi.sensorswatch.MainActivity
import com.davi.sensorswatch.presentation.viewModels.SensorTestViewModel
import com.davi.sensorswatch.services.WearSensorTestService

@Composable
fun SensorTestScreen(
    viewModel: SensorTestViewModel = viewModel()
) {
    val context = LocalContext.current

    val permissions = remember {
        MainActivity.permissionsVersion30
    }

    var allPermissionsGranted by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        allPermissionsGranted = permissionsMap.values.all { it }
    }

    LaunchedEffect(Unit) {
        val allGranted = permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

        if (allGranted) {
            allPermissionsGranted = true
            viewModel.initialize(context)
        } else {
            permissionLauncher.launch(permissions)
            permissionLauncher.launch(MainActivity.permissionsVersion33)
        }
    }

    val sensorStates by viewModel.sensorStates.collectAsState(emptyMap())

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
    ) {
        item {
            Spacer(Modifier.height(30.dp))
        }
        item {
            Text(
                text = "Sensors Watch",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
        }
        item {
            Button(
                onClick = { viewModel.startTests() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Green
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Iniciar")
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "Iniciar sensores",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
        item {
            Button(
                onClick = { viewModel.stopTests() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Stop, contentDescription = "Parar")
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "Parar sensores",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
        }
        itemsIndexed(sensorStates.values.toList()) { index, sensor ->
            SensorCard(sensorState = sensor)
        }
        item {
            Spacer(Modifier.height(30.dp))
        }
    }
}

@Composable
fun SensorCard(sensorState: WearSensorTestService.SensorState) {
    val iconColor = when {
        !sensorState.isAvailable -> Color.Gray
        sensorState.isWorking -> Color.Green
        else -> Color.Yellow
    }

    val icon = when {
        !sensorState.isAvailable -> Icons.Default.Block
        sensorState.isWorking -> Icons.Default.CheckCircle
        else -> Icons.Default.Warning
    }

    Card(
        onClick = {},
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Status",
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = sensorState.name,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Status:", style = MaterialTheme.typography.body2)
                Text(sensorState.status, fontWeight = FontWeight.SemiBold)
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Última leitura:", style = MaterialTheme.typography.body2)
                Text(sensorState.lastValue, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
package com.davi.sensorswatch

import android.Manifest
import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.davi.sensorswatch.presentation.SensorTestScreen
import com.davi.sensorswatch.presentation.theme.SensorsWatch8Theme
import com.davi.sensorswatch.presentation.viewModels.SensorTestViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_DeviceDefault)

        setContent {
            val viewModel: SensorTestViewModel = viewModel()
            val context = LocalContext.current

            LaunchedEffect(Unit) {
                viewModel.initialize(context)
            }

            SensorsWatch8Theme {
                SensorTestScreen(viewModel)
            }
        }
    }

    companion object {
        val permissionsVersion30 = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.HIGH_SAMPLING_RATE_SENSORS,
            Manifest.permission.ACTIVITY_RECOGNITION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
        )

        val permissionsVersion33 = arrayOf(
            Manifest.permission.BODY_SENSORS_BACKGROUND,
            Manifest.permission.NEARBY_WIFI_DEVICES,
        )

        val permissionsNotifications = arrayOf(
            Manifest.permission.POST_NOTIFICATIONS,
        )
    }
}
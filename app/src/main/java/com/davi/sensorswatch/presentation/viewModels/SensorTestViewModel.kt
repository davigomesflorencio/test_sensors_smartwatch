package com.davi.sensorswatch.presentation.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davi.sensorswatch.services.WearSensorTestService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SensorTestViewModel : ViewModel() {
    private lateinit var sensorService: WearSensorTestService

    private val _sensorStates = MutableStateFlow<Map<String, WearSensorTestService.SensorState>>(emptyMap())
    val sensorStates = _sensorStates.asStateFlow()

    fun initialize(context: Context) {
        sensorService = WearSensorTestService(context)

        viewModelScope.launch {
            sensorService.sensorStates.collect { states ->
                _sensorStates.value = states
            }
        }
    }

    fun startTests() {
        sensorService.startTests()
    }

    fun stopTests() {
        sensorService.stopTests()
    }
}
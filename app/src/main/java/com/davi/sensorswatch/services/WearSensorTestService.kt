package com.davi.sensorswatch.services

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow

class WearSensorTestService(private val context: Context) {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    // Estado dos sensores
    val sensorStates = MutableStateFlow<Map<String, SensorState>>(emptyMap())

    // Listener para atualizações dos sensores
    private val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val sensorName = event.sensor.name
            val values = event.values.joinToString(", ") { "%.2f".format(it) }

            sensorStates.value = sensorStates.value.toMutableMap().apply {
                this[sensorName] = SensorState(
                    name = sensorName,
                    isAvailable = true,
                    isWorking = true,
                    lastValue = values,
                    status = "Ativo"
                )
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            // Não necessário para nossos testes
        }
    }

    fun startTests() {
        val availableSensors = mutableMapOf<String, SensorState>()

        var sensorTypes = emptyList<Pair<Int, String>>()

        sensorManager.getSensorList(Sensor.TYPE_ALL).forEach { sensor ->
            Log.d("xing", "sensor ${sensor.name} - type ${sensor.type}  - vendor ${sensor.vendor} - version ${sensor.version}  - ${sensor.id}")
        }

        sensorTypes = sensorManager.getSensorList(Sensor.TYPE_ALL).map { sensor ->
            Pair(sensor.type, sensor.name)
        }

        sensorTypes.forEach { (type, name) ->
            val sensor = sensorManager.getDefaultSensor(type)
            if (sensor != null) {
                sensorManager.registerListener(
                    sensorListener,
                    sensor,
                    SensorManager.SENSOR_DELAY_NORMAL
                )
                availableSensors[name] = SensorState(
                    name = name,
                    isAvailable = true,
                    isWorking = false,
                    lastValue = "Testando...",
                    status = "Registrado"
                )
            } else {
                availableSensors[name] = SensorState(
                    name = name,
                    isAvailable = false,
                    isWorking = false,
                    lastValue = "N/A",
                    status = "Não disponível"
                )
            }
        }

        sensorStates.value = availableSensors
    }

    // Parar testes
    fun stopTests() {
        sensorManager.unregisterListener(sensorListener)
    }

    data class SensorState(
        val name: String,
        val isAvailable: Boolean,
        val isWorking: Boolean,
        val lastValue: String,
        val status: String
    )
}
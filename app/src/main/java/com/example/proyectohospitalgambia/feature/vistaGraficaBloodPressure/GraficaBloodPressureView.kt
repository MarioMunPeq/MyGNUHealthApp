package com.example.proyectohospitalgambia.feature.vistaGraficaBloodPressure

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

/**
 * Fragment que muestra las gráficas de presión sanguínea y ritmo cardíaco.
 */
class GraficaBloodPressureView : Fragment() {

    /**
     * Método que se llama para tener la vista del fragment inflada y lista.
     *
     * @param inflater El objeto LayoutInflater que se puede usar para inflar cualquier vista en el fragment.
     * @param container Si no es nulo, esta es la vista principal a la que se debe adjuntar la UI del fragment.
     * @param savedInstanceState Si no es nulo, este fragment se está reconstruyendo a partir de un estado guardado anteriormente.
     * @return Retorna la vista del fragment.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_grafica_blood_pressure_view, container, false)
    }

    /**
     * Método que se llama inmediatamente después de que onCreateView(LayoutInflater, ViewGroup, Bundle) ha retornado, pero antes de que se haya restaurado cualquier estado guardado en las vistas.
     *
     * @param view La vista devuelta por onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState Si no es nulo, este fragment se está reconstruyendo a partir de un estado guardado anteriormente.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chartBloodPressure = view.findViewById<LineChart>(R.id.graficoLineas_PresionSanguinea)
        val chartHeartRate = view.findViewById<LineChart>(R.id.graficoLineas_RitmoCardiaco)

        val idUsuarioActual = MainActivity.usuario?.id

        // Obtener los datos de presión sanguínea de la base de datos
        val datosPresionSanguinea =
            MainActivity.databaseHelper?.obtenerTodosLosDatosPresionSanguinea(idUsuarioActual!!)

        // Crear las entradas de la gráfica a partir de los datos de presión sanguínea
        val entriesSistolico = datosPresionSanguinea?.mapIndexed { index, presionSanguinea ->
            Entry(index.toFloat(), presionSanguinea.sistolico.toFloat())
        }
        val entriesDiastolico = datosPresionSanguinea?.mapIndexed { index, presionSanguinea ->
            Entry(index.toFloat(), presionSanguinea.diastolico.toFloat())
        }

        // Crear las entradas de la gráfica de ritmo cardíaco
        val entriesHeartRate = datosPresionSanguinea?.mapIndexed { index, presionSanguinea ->
            Entry(index.toFloat(), presionSanguinea.frecuenciaCardiaca.toFloat())
        }

        // Crear el conjunto de datos y personalizarlo
        val dataSetSistolico = LineDataSet(entriesSistolico, getString(R.string.sistolico))
        dataSetSistolico.color = Color.BLUE
        dataSetSistolico.valueTextColor = Color.BLACK
        dataSetSistolico.valueTextSize = 16f

        val dataSetDiastolico = LineDataSet(entriesDiastolico, getString(R.string.diastolico))
        dataSetDiastolico.color = Color.RED
        dataSetDiastolico.valueTextColor = Color.BLACK
        dataSetDiastolico.valueTextSize = 16f

        val dataSetHeartRate =
            LineDataSet(entriesHeartRate, getString(R.string.frecuencia_cardiaca))
        dataSetHeartRate.color = Color.GREEN
        dataSetHeartRate.valueTextColor = Color.BLACK
        dataSetHeartRate.valueTextSize = 16f

        // Crear el gráfico de líneas y personalizarlo
        val dataBloodPressure = LineData(dataSetSistolico, dataSetDiastolico)
        chartBloodPressure.data = dataBloodPressure
        chartBloodPressure.setTouchEnabled(true)
        chartBloodPressure.setPinchZoom(true)
        chartBloodPressure.description.text = getString(R.string.presion_sanguinea)
        chartBloodPressure.setNoDataText(getString(R.string.no_hay_datos_disponibles))
        chartBloodPressure.invalidate()

        // Crear el gráfico de ritmo cardíaco y personalizarlo
        val dataHeartRate = LineData(dataSetHeartRate)
        chartHeartRate.data = dataHeartRate
        chartHeartRate.setTouchEnabled(true)
        chartHeartRate.setPinchZoom(true)
        chartHeartRate.description.text = getString(R.string.ritmo_cardiaco)
        chartHeartRate.setNoDataText(getString(R.string.no_hay_datos_disponibles))
        chartHeartRate.invalidate()
    }
}
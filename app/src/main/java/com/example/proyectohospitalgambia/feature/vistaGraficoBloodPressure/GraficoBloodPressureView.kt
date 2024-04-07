package com.example.proyectohospitalgambia.feature.vistaGraficoBloodPressure

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectohospitalgambia.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import android.graphics.Color


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GraficoBloodPressureView.newInstance] factory method to
 * create an instance of this fragment.
 */
class GraficoBloodPressureView : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafico_blood_pressure_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chart = view.findViewById<LineChart>(R.id.graficoLineas_PresionSanguinea)

        // Datos de prueba para la presión sistólica
        val entriesSistolica = ArrayList<Entry>()
        entriesSistolica.add(Entry(0f, 120f)) // Día 1
        entriesSistolica.add(Entry(1f, 115f)) // Día 2
        entriesSistolica.add(Entry(2f, 110f)) // Día 3
        entriesSistolica.add(Entry(3f, 90f)) // Día 4
        entriesSistolica.add(Entry(4f, 105f)) // Día 5

        // Datos de prueba para la presión diastólica
        val entriesDiastolica = ArrayList<Entry>()
        entriesDiastolica.add(Entry(0f, 100f)) // Día 1
        entriesDiastolica.add(Entry(1f, 60f)) // Día 2
        entriesDiastolica.add(Entry(2f, 150f)) // Día 3
        entriesDiastolica.add(Entry(3f, 78f)) // Día 4
        entriesDiastolica.add(Entry(4f, 40f)) // Día 5

        // Crear los conjuntos de datos y personalizarlos
        val dataSetSistolica = LineDataSet(entriesSistolica, "Sistólica")
        dataSetSistolica.color = Color.RED

        val dataSetDiastolica = LineDataSet(entriesDiastolica, "Diastólica")
        dataSetDiastolica.color = Color.BLUE

        // Agregar los conjuntos de datos a los datos de la línea
        val lineData = LineData(dataSetSistolica, dataSetDiastolica)

        // Aplicar los datos al gráfico y refrescarlo
        chart.data = lineData
        chart.invalidate() // Refresca el gráfico

        // ---------------------- Gráfico de ritmo cardíaco ----------------------

        val chartRitmoCardiaco = view.findViewById<LineChart>(R.id.graficoLineas_RitmoCardiaco)

        // Datos de prueba para el ritmo cardíaco
        val entriesRitmoCardiaco = ArrayList<Entry>()
        entriesRitmoCardiaco.add(Entry(0f, 70f)) // Día 1
        entriesRitmoCardiaco.add(Entry(1f, 75f)) // Día 2
        entriesRitmoCardiaco.add(Entry(2f, 72f)) // Día 3
        entriesRitmoCardiaco.add(Entry(3f, 76f)) // Día 4
        entriesRitmoCardiaco.add(Entry(4f, 73f)) // Día 5

        // Crear el conjunto de datos y personalizarlo
        val dataSetRitmoCardiaco = LineDataSet(entriesRitmoCardiaco, "Ritmo cardíaco")
        dataSetRitmoCardiaco.color = Color.RED

        // Agregar el conjunto de datos a los datos de la línea
        val lineDataRitmoCardiaco = LineData(dataSetRitmoCardiaco)

        // Aplicar los datos al gráfico y refrescarlo
        chartRitmoCardiaco.data = lineDataRitmoCardiaco
        chartRitmoCardiaco.invalidate() // Refresca el gráfico
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GraficoBloodPressureView.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GraficoBloodPressureView().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
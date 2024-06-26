package com.example.proyectohospitalgambia.feature.vistaNuevoRegistroServidor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.proyectohospitalgambia.R
import com.example.proyectohospitalgambia.app.MainActivity
import com.example.proyectohospitalgambia.core.domain.model.datosPols.LibroVida
import com.example.proyectohospitalgambia.core.domain.model.pol.Pol
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID


/**
 * Clase NuevoRegistroServidorView.
 *
 * Esta clase representa la vista para el nuevo registro de servidor en la aplicación.
 *
 * @property btnGuardar Botón para guardar el nuevo registro.
 * @property btnListar Botón para listar los registros.
 * @property edtTextoDia EditText para introducir el día.
 * @property edtTextoMes EditText para introducir el mes.
 * @property edtTextoAnio EditText para introducir el año.
 * @property edtTextoHora EditText para introducir la hora.
 * @property edtTextoMinutos EditText para introducir los minutos.
 * @property edtTextoResumen EditText para introducir el resumen.
 * @property spinner1 Spinner para seleccionar la opción de medicina.
 * @property spinner2 Spinner para seleccionar la condición de salud.
 * @property edtTextoDetalles EditText para introducir los detalles.
 * @property spinner3 Spinner para seleccionar el tipo de relevancia.
 * @property cbPaginaPrivada CheckBox para marcar si la página es privada.
 * @property viewModel ViewModel asociado a esta vista.
 *
 * @method onCreateView Método que se llama para crear la vista del fragmento.
 * @method onItemSelected Método que se llama cuando se selecciona un ítem del spinner.
 * @method onNothingSelected Método que se llama cuando no se selecciona ningún ítem del spinner.
 * @method onViewCreated Método que se llama después de que la vista ha sido creada.
 * @method generarIdAleatorio Método para generar un ID aleatorio.
 * @method obtenerDatosFormulario Método para obtener los datos del formulario y crear el JSON.
 */
class NuevoRegistroServidorView : Fragment(), AdapterView.OnItemSelectedListener {


    private lateinit var btnGuardar: Button

    // Declaración de variables para los elementos del formulario
    private lateinit var edtTextoDia: EditText
    private lateinit var edtTextoMes: EditText
    private lateinit var edtTextoAnio: EditText
    private lateinit var edtTextoHora: EditText
    private lateinit var edtTextoMinutos: EditText
    private lateinit var edtTextoResumen: EditText
    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var edtTextoDetalles: EditText
    private lateinit var spinner3: Spinner
    private lateinit var cbPaginaPrivada: CheckBox

    private val viewModel: NuevoRegistroServidorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.fragment_nuevo_registro_servidor_view, container, false)

        btnGuardar = view.findViewById(R.id.btn_guardar)


        // Inicialización de elementos del formulario
        edtTextoDia = view.findViewById(R.id.edt_textoDia)
        edtTextoMes = view.findViewById(R.id.edt_textoMes)
        edtTextoAnio = view.findViewById(R.id.edt_textoAnio)
        edtTextoHora = view.findViewById(R.id.edt_textoHora)
        edtTextoMinutos = view.findViewById(R.id.edt_textoMinutos)
        edtTextoResumen = view.findViewById(R.id.edt_textoResumen)
        spinner1 = view.findViewById(R.id.spinner1)
        spinner2 = view.findViewById(R.id.spinner2)
        edtTextoDetalles = view.findViewById(R.id.edt_textoDetalles)
        spinner3 = view.findViewById(R.id.spinner3)
        cbPaginaPrivada = view.findViewById(R.id.cb_paginaPrivada)

        // Configura el adaptador para el primer Spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.opcion_medicina,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner1.adapter = adapter
        }

        // Configura el adaptador para el segundo Spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.condiciones_salud,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter = adapter
        }

        // Configura el adaptador para el tercer Spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.tipo_relevancia,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner3.adapter = adapter
        }

        spinner1.onItemSelectedListener = this
        spinner2.onItemSelectedListener = this
        spinner3.onItemSelectedListener = this

        return view
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // Acciones cuando se selecciona un ítem del spinner
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Acciones cuando no se selecciona ningún ítem del spinner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btnGuardar.setOnClickListener {

            val usuarioActivo = MainActivity.usuario

            // Obtener los datos del formulario
            val datosFormulario = obtenerDatosFormulario()
            // Verificar si se obtuvieron los datos del formulario correctamente
            if (datosFormulario != null) {

                // Mostrar un mensaje de éxito
                Toast.makeText(context, "Datos insertados correctamente", Toast.LENGTH_SHORT).show()

                // Generar IDs aleatorios como strings
                val idPols = generarIdAleatorio()
                val idBook =
                    MainActivity.usuario?.id.toString() // Asumiendo que MainActivity.idUsuario es un Long o un Int

                val pol = Pol(idPols, idBook, datosFormulario.toString(), "false")

                usuarioActivo?.pols?.add(pol)

                // Llamar al método del ViewModel para insertar datos
                val resultado = viewModel.insertarDatosEnBaseDeDatos(pol)

                if (resultado) {

                    Toast.makeText(
                        requireContext(),
                        R.string.toast_registro_correcto,
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.toast_registro_incorrecto,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                // Mostrar un mensaje de error
                Toast.makeText(context, R.string.toast_complete_datos, Toast.LENGTH_SHORT).show()
            }


        }

    }

    private fun generarIdAleatorio(): String {
        return UUID.randomUUID().toString()
    }

    // Método para obtener los datos del formulario y crear el JSON
    private fun obtenerDatosFormulario(): JSONObject? {
        // Obtener los valores de los EditText y Spinner
        val dia = edtTextoDia.text.toString()
        val mes = edtTextoMes.text.toString()
        val anio = edtTextoAnio.text.toString()
        val hora = edtTextoHora.text.toString()
        val minutos = edtTextoMinutos.text.toString()
        val resumen = edtTextoResumen.text.toString()
        val dominio = spinner1.selectedItem.toString()
        val contexto = spinner2.selectedItem.toString()
        val detalles = edtTextoDetalles.text.toString()
        val relevancia = spinner3.selectedItem.toString()
        val paginaPrivada = cbPaginaPrivada.isChecked

        // Verificar si algún campo está vacío
        if (dia.isEmpty() || mes.isEmpty() || anio.isEmpty() || hora.isEmpty() || minutos.isEmpty() ||
            resumen.isEmpty() || dominio.isEmpty() || contexto.isEmpty() || detalles.isEmpty() ||
            relevancia.isEmpty()
        ) {
            // Mostrar un Toast indicando que algún campo está vacío
            Toast.makeText(context, R.string.toast_complete_datos, Toast.LENGTH_SHORT).show()
            return null // Devolver null para indicar que no se han completado todos los campos
        }

        // Obtener la fecha y hora actual
        val currentDateAndTime =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

        val fecha = "$anio-$mes-$dia $hora:$minutos:00"
        val formatoEntrada = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val fechaCompleta = formatoEntrada.parse(fecha)

        val formatoSalida = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val fechaFormateada = formatoSalida.format(fechaCompleta!!)

        val libroVida = LibroVida(
            fechaRealizacion = currentDateAndTime,
            fechaLibro = fechaFormateada,
            resumen = resumen,
            dominio = dominio,
            contexto = contexto,
            detalles = detalles,
            relevancia = relevancia,
            paginaPrivada = paginaPrivada
        )

        // Crear el objeto JSON con los datos del formulario
        val jsonObject = JSONObject()
        jsonObject.put("TipoPol", libroVida.tipoPol)
        jsonObject.put("FechaInsercion", libroVida.fechaRealizacion)
        jsonObject.put("fecha", libroVida.fechaLibro)
        jsonObject.put("resumen", libroVida.resumen)
        jsonObject.put("dominio", libroVida.dominio)
        jsonObject.put("relevancia", libroVida.relevancia)
        jsonObject.put("detalles", libroVida.detalles)
        jsonObject.put("relevancia", libroVida.relevancia)
        jsonObject.put("paginaPrivada", libroVida.paginaPrivada)

        // Limpiar los elementos del formulario después de obtener los datos si son correctos
        edtTextoDia.text.clear()
        edtTextoMes.text.clear()
        edtTextoAnio.text.clear()
        edtTextoHora.text.clear()
        edtTextoMinutos.text.clear()
        edtTextoResumen.text.clear()
        spinner1.setSelection(0)
        spinner2.setSelection(0)
        edtTextoDetalles.text.clear()
        spinner3.setSelection(0)
        cbPaginaPrivada.isChecked = false

        return jsonObject
    }


}
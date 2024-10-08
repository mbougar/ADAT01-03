package com.gestorEmpleados.repositorio

import com.gestorEmpleados.modelo.Empleado
import java.io.BufferedReader
import java.nio.file.Files
import java.nio.file.Path

/**
 * Clase de utilidad para manejar la lectura de archivos de texto.
 **/
class GestorCSV {

    fun leerArchivoTexto(rutaFichero: Path): List<Empleado> {
        val listaEmpleados = mutableListOf<Empleado>()

        val br: BufferedReader = Files.newBufferedReader(rutaFichero)
        var firstLineRead = false

        br.use {
            it.forEachLine { linea ->
                if (firstLineRead) {
                    val columnas = linea.split(",")
                    val empleado = Empleado(
                        id = columnas[0].toInt(),
                        apellido = columnas[1],
                        departamento = columnas[2],
                        salario = columnas[3].toDouble()
                    )
                    listaEmpleados.add(empleado)
                } else {
                    firstLineRead = true
                }
            }
        }

        return listaEmpleados
    }
}
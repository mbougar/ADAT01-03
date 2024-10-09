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
        val br: BufferedReader

        try {
            br = Files.newBufferedReader(rutaFichero)
        } catch (_: java.io.IOException) {
            // Si levanta excepción durante la generación del bufferedReader devolvera una lista vacía ya que no lee nada
            return listaEmpleados
        }

        var firstLineRead = false

        br.use {
            it.forEachLine { linea ->
                if (firstLineRead) {
                    val columnas = linea.split(",")
                    try {
                        val empleado = Empleado(
                            id = columnas[0].toInt(),
                            apellido = columnas[1],
                            departamento = columnas[2],
                            salario = columnas[3].toDouble()
                        )
                        listaEmpleados.add(empleado)
                    } catch (_: NumberFormatException) {

                    }
                } else {
                    firstLineRead = true
                }
            }
        }

        return listaEmpleados
    }
}
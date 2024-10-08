package com.gestorEmpleados.servicio

import com.gestorEmpleados.modelo.Empleado
import com.gestorEmpleados.repositorio.GestorCSV
import com.gestorEmpleados.repositorio.GestorXML
import java.nio.file.Path

/**
 * Encargada de gestionar las operaciones principales sobre los empleados (lectura, escritura, modificación).
 */
class GestorEmpleados(
    private val gestorXML: GestorXML,
    private val gestorCSV: GestorCSV
) {

    private fun leerEmpleadosDesdeArchivo(rutaFichero: Path): List<Empleado> {
        val empleados = gestorCSV.leerArchivoTexto(rutaFichero)

        return empleados
    }

    fun generarArchivoXML(rutaLectura: Path, rutaEscritura: Path) {
        val empleados = leerEmpleadosDesdeArchivo(rutaLectura)
        gestorXML.crearXML(rutaEscritura, empleados)
    }

    fun modificarSalarioEmpleado(idEmpleado: Int, nuevoSalario: Double, rutaFichero: Path) {
        gestorXML.modificarNodoXML(idEmpleado, nuevoSalario, rutaFichero)
    }

    fun leerEmpleadosDesdeXML(rutaFichero: Path): List<Empleado> {
        val empleados = gestorXML.leerXML(rutaFichero)

        return empleados
    }
}
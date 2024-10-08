package com.gestorEmpleados

import com.gestorEmpleados.repositorio.GestorCSV
import com.gestorEmpleados.repositorio.GestorXML
import com.gestorEmpleados.servicio.GestorEmpleados
import kotlin.io.path.Path

fun main() {
    val gestorCSV = GestorCSV()
    val gestorXML = GestorXML()
    val gestorEmpleados = GestorEmpleados(gestorXML, gestorCSV)
    val pathLecturaCSV = Path("src/main/resources/empleados.csv")
    val pathEscrituraXML = Path("src/main/resources/empleados.xml")

    gestorEmpleados.generarArchivoXML(pathLecturaCSV, pathEscrituraXML)
    gestorEmpleados.modificarSalarioEmpleado(1, 20000.0, pathEscrituraXML)
    val empleados = gestorEmpleados.leerEmpleadosDesdeXML(pathEscrituraXML)

    empleados.forEach { empleado -> println(empleado) }
}
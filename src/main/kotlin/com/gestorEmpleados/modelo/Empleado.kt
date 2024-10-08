package com.gestorEmpleados.modelo

/**
 * Clase que representa a un empleado con sus propiedades básicas.
 */
data class Empleado (
    val id: Int,
    val apellido: String,
    val departamento: String,
    val salario: Double
) {
    override fun toString(): String {
        return "ID: $id, Apellido: $apellido, Departamento: $departamento, Salario: $salario"
    }
}
package com.gestorEmpleados.repositorio

import com.gestorEmpleados.modelo.Empleado
import org.w3c.dom.*
import java.nio.file.Path
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Source
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 * Clase de utilidad para generar y modificar archivos XML
**/
class GestorXML {

    fun crearXML(rutaFichero: Path, empleados: List<Empleado>) {

        val dbf: DocumentBuilderFactory
        val builder: DocumentBuilder
        val imp: DOMImplementation
        val document: Document

        try {
            dbf = DocumentBuilderFactory.newInstance()
            builder = dbf.newDocumentBuilder()
            imp = builder.domImplementation
            document = imp.createDocument(null, "empleados", null)
        } catch (_: Exception) {
            return
        }

        for (empleado in empleados) {
            val empleadoNodo: Element = document.createElement("empleado")
            document.documentElement.appendChild(empleadoNodo)
            empleadoNodo.setAttribute("id", empleado.id.toString())

            val apellido: Element = document.createElement("apellido")
            val departamento: Element = document.createElement("departamento")
            val salario: Element = document.createElement("salario")

            val textoApellido: Text = document.createTextNode(empleado.apellido)
            val textoDepartamento: Text = document.createTextNode(empleado.departamento)
            val textoSalario: Text = document.createTextNode(empleado.salario.toString())

            apellido.appendChild(textoApellido)
            departamento.appendChild(textoDepartamento)
            salario.appendChild(textoSalario)

            empleadoNodo.appendChild(apellido)
            empleadoNodo.appendChild(departamento)
            empleadoNodo.appendChild(salario)
        }

        val source: Source = DOMSource(document)
        val result: StreamResult

        try {
            result = StreamResult(rutaFichero.toFile())
        } catch (_: Exception) {
            return
        }

        val transformer: Transformer = TransformerFactory.newInstance().newTransformer()

        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.transform(source, result)
    }

    fun modificarNodoXML(idEmpleado: Int, nuevoSalario: Double, rutaFichero: Path) {

        val dbf: DocumentBuilderFactory
        val builder: DocumentBuilder
        val document: Document
        val root: Element

        try {
            dbf = DocumentBuilderFactory.newInstance()
            builder = dbf.newDocumentBuilder()
            document = builder.parse(rutaFichero.toFile())
            root = document.documentElement
        } catch (_: Exception) {
            return
        }

        root.normalize()

        val empleados = document.getElementsByTagName("empleado")

        for (i in 0 until empleados.length) {
            val empleadoNodo = empleados.item(i) as Element
            val id = empleadoNodo.getAttribute("id").toInt()

            if (id == idEmpleado) {

                val salarioElement = empleadoNodo.getElementsByTagName("salario").item(0) as Element
                salarioElement.textContent = nuevoSalario.toString()

                break
            }
        }

        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.INDENT, "no")
        val source = DOMSource(document)
        val result = StreamResult(rutaFichero.toFile())
        transformer.transform(source, result)
    }

    fun leerXML(rutaFichero: Path): List<Empleado> {

        val dbf: DocumentBuilderFactory
        val builder: DocumentBuilder
        val document: Document
        val empleados = mutableListOf<Empleado>()

        try {
            dbf = DocumentBuilderFactory.newInstance()
            builder = dbf.newDocumentBuilder()
            document = builder.parse(rutaFichero.toFile())
        } catch (_: Exception) {
            return empleados
        }

        val root: Element = document.documentElement
        root.normalize()

        val listaNodos: NodeList = root.getElementsByTagName("empleado")

        for (i in 0..< listaNodos.length) {

            val nodo = listaNodos.item(i)

            if (nodo.nodeType == Node.ELEMENT_NODE) {

                val nododElemento = nodo as Element

                val elementoId = nododElemento.getAttribute("id")
                val elementoApellido = nododElemento.getElementsByTagName("apellido")
                val elementoDepartamento = nododElemento.getElementsByTagName("departamento")
                val elementoSalario = nododElemento.getElementsByTagName("salario")

                val empleado = Empleado(
                    id = elementoId.toInt(),
                    apellido = elementoApellido.item(0).textContent,
                    departamento = elementoDepartamento.item(0).textContent,
                    salario = elementoSalario.item(0).textContent.toDouble()
                )

                empleados.add(empleado)
            }
        }

        return empleados
    }
}
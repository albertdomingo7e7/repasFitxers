package controllers

import utilities.exercicis.practica2.clientXposició
import utilities.exercicis.practica2.crearClient
import utilities.funcions.scan
import kotlin.io.path.Path



fun main(){
    val ruta = Path("clients.txt")
    println(clientXposició(ruta, 3))
}
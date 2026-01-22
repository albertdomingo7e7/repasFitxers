package org.example.controllers

import utilities.exercicis.practica1.llegirFrases
import kotlin.io.path.Path

// Exercici 1
fun main() {
    val path = Path("textos.txt")
    llegirFrases(path)
}
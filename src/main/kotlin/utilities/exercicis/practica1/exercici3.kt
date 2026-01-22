package utilities.exercicis.practica1

import utilities.funcions.readString
import utilities.funcions.scan
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.Path
import kotlin.io.path.bufferedReader
import kotlin.io.path.bufferedWriter
import kotlin.io.path.deleteExisting
import kotlin.io.path.deleteIfExists

//Llegir i mostrar el document per pantalla

fun mostrarFitxer(ruta : Path){
    ruta.bufferedReader().use { reader ->
        var linia = reader.readLine()
        while (linia != null) {
            println(linia)
            linia = reader.readLine()
        }
    }
}



// 	Mostri per pantalla el contingut de la línia X, sent X un número que li demanarem a l’usuari.
fun mostrarLinia(ruta : Path, nlinia : Int){
    var index = 1
    ruta.bufferedReader().use {
        var linia = it.readLine()
        while (linia != null) {
            if(index == nlinia){
                println(linia)
                return
            }
            index++
            linia = it.readLine()

        }
    }
}


//Inserir linia al principi del fitxer
fun inserirLinia(ruta : Path){
    val temp = Path("temp.txt")
    ruta.bufferedReader().use { reader ->
        temp.bufferedWriter().use { writer ->
            var linia = reader.readLine()
            while (linia != null) {
                writer.write(linia)
                writer.newLine()
                linia = reader.readLine()
            }
        }
    }
    scan().use { scanner ->
        val liniaAinserir = readString(scan(), "escriu la linia a inserir")
        ruta.bufferedWriter().use { writer ->
            //Inserim la nova linia al fitxer nou
            writer.write(liniaAinserir)
            writer.newLine()

            //Copiem el document
            val copia = temp.bufferedReader()
            copia.use{ reader ->
                var linia = reader.readLine()
                while (linia != null) {
                    writer.write(linia)
                    writer.newLine()
                    linia = reader.readLine()
                }
            }
        }
    }
    temp.deleteIfExists()
}


fun main(){
    val ruta = Path("textos.txt")
    inserirLinia(ruta)
}
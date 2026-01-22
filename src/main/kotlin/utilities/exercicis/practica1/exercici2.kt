package utilities.exercicis.practica1

import utilities.funcions.scan
import utilities.funcions.tancarScanner
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.appendText
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.exists
import kotlin.io.path.writeText

// Crea un programa que llegeixi frases per teclat i les vagi
// guardant en un fitxer de nom “textos.txt”.
// El programa finalitzarà quan s’entri una cadena buida.
/**
 * Aquesta funció s'encarrega d'emplenar [ruta]
 * amb el text que li proporcionem
 * El programa finalitza quan entra una linia sense contingut
 */
fun milloraExercici1(ruta : Path){
    val scan = scan()
    if(!ruta.exists()) {
        ruta.createFile()
    } else {
        var linia = scan.nextLine()
        if(linia == "@ESBORRA"){
            ruta.writeText("")
        }
        while(linia.isNotEmpty()){
            linia = scan.nextLine()
            ruta.appendText(linia+"\n")
            if(linia == "@ESBORRA"){
                ruta.writeText("")
            }
        }
    }
    tancarScanner()
}

fun main(){
    val arxiu = Path("arxiu.txt")
    milloraExercici1(arxiu)

}
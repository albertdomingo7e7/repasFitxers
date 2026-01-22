package utilities.exercicis.practica1

import utilities.funcions.scan
import utilities.funcions.tancarScanner
import java.nio.file.Path
import kotlin.io.path.appendText

// Crea un programa que llegeixi frases per teclat i les vagi
// guardant en un fitxer de nom “textos.txt”.
// El programa finalitzarà quan s’entri una cadena buida.
/**
 * Aquesta funció s'encarrega d'emplenar [ruta]
 * amb el text que li proporcionem
 * El programa finalitza quan entra una linia sense contingut
 */
fun llegirFrases(ruta : Path){
    val scan = scan()
    var linia = scan.nextLine()
    while (linia.isNotEmpty()){
        linia = scan.nextLine()
        ruta.appendText(linia+"\n")
    }
    tancarScanner()
}
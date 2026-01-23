package utilities.exercicis.practica2

import java.nio.file.Path
import java.util.Scanner
import kotlin.io.path.appendText
import kotlin.io.path.useLines

fun agafarCamps(mida: Int, sc: Scanner): String {
    var camp: String = sc.nextLine()
    while (camp.length > mida) {
        println("Aquest camp no pot tindre una llargada superior a $mida caracters. Torna-hi:")
        camp = sc.nextLine()
    }

    return camp.padEnd(mida, ' ')
}

fun crearClient(ruta: Path, sc: Scanner) {
    val MIDA_CODI = 6
    val MIDA_NOM = 20
    val MIDA_COGNOMS = 30
    val MIDA_DATANAIXAMENT = 8
    val MIDA_CODI_POSTAL = 40
    val MIDA_EMAIL = 30

    val codi = agafarCamps(MIDA_CODI, sc)
    val nom = agafarCamps(MIDA_NOM, sc)
    val cognom = agafarCamps(MIDA_COGNOMS, sc)
    val datanaixement = agafarCamps(MIDA_DATANAIXAMENT, sc)
    val codipostal = agafarCamps(MIDA_CODI_POSTAL, sc)
    val email = agafarCamps(MIDA_EMAIL, sc)

    ruta.appendText(codi + nom + cognom + datanaixement + codipostal + email + "\n")
}

fun clientXposició(ruta : Path, nlinia : Int): String {
    var mostrarClient = ""
    ruta.useLines { lines ->
        var index = 1
        for(line in lines) {
            if(index == nlinia) {
                mostrarClient = line
            }
            index++

        }
    }
    return mostrarClient

}


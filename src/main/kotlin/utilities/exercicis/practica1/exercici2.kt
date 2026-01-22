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

/**
 * Millora de la funció anterior, aquesta permet crear [ruta]
 * en cas que no existeixi, a part, ens permet esborrar el contingut
 * del mateix escribint per consola "@ESBORRA"
 */
fun milloraExercici1(ruta : Path){
    if (!ruta.exists()) {
        ruta.createFile()
    }
    scan().use { scanner ->
        while (scanner.hasNextLine()) {
            val linia = scanner.nextLine()

            if (linia == "@ESBORRA") {
                ruta.writeText("")
                return
            } else if(linia.isEmpty()){
                return
            }

            ruta.appendText("$linia\n")
        }
    }
}

fun main(){
    val arxiu = Path("arxiu.txt")
    milloraExercici1(arxiu)

}
package utilities

import M06_RA1_TA38_FitxersBinaris.opcionsMenu
import java.util.Scanner

//Definir un scanner a nivell global
var scan : Scanner = Scanner(System.`in`)

val RESET: String = "\u001b[0m"
val WHITE: String = "\u001b[97m"
val BOLD: String = "\u001b[1m"
val RED_BACKGROUND: String = "\u001b[41m"

// Demana un String i el retalla a un tamany determinat
// Si el tamany és zero o negatiu, no es retalla
fun demanarString(missatge: String, tamany:Int=0):String {
    print(missatge)
    var valor: String = scan.nextLine()
    if (tamany > 0 && tamany < valor.length) {
        valor = valor.substring(0, tamany)
    }
    return valor
}


// Demana un valor numèric comprès entre valorMin i valorMax (opcionals)
fun readInt(missatge: String, valorMin: Int = Integer.MIN_VALUE, valorMax: Int = Integer.MAX_VALUE):Int {
    print(missatge)
    var valorCorrecte: Boolean = false
    var valor: Int = 0
    while (!valorCorrecte) {
        while (!scan.hasNextInt()) {
            print("El valor ha de ser numèric. Torna-ho a intentar! ")
            scan.next()
        }
        valor = scan.nextInt();
        if (valor >= valorMin && valor <= valorMax)
            valorCorrecte = true
        else
            print("El valor ha d'estar entre $valorMin i $valorMax. Torna-ho a intentar! ")
    }
    scan.nextLine() //Elimina el salt de línia
    return valor
}

/**
 * Mostra el menú segons el enum class opcionsMenu
 * que s'hagi definit
 */
fun mostrarMenu(): Int {
    println("*** MENÚ ***")
    for (e in opcionsMenu.entries) {
        println("${e.opcio}:: ${e.text}")
    }
    return (readInt("Quina opció tries? ",
        opcionsMenu.entries.get(0).opcio,
        opcionsMenu.entries.get(opcionsMenu.entries.lastIndex).opcio))
}


// Tanca l'Scanner
fun tancarScanner() {
    scan.close()
}


fun mostrarError(missatge:String) {
    println("$RED_BACKGROUND$BOLD$WHITE ERROR:: $missatge $RESET")
}
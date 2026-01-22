package utilities.funcions
import java.nio.file.Path
import java.util.Scanner
import kotlin.io.path.Path
import kotlin.io.path.appendText
import kotlin.io.path.bufferedReader
import kotlin.io.path.bufferedWriter

/**
 * Obre un objecte `Scanner` per llegir dades des de l'entrada estàndard.
 *
 * @return Un objecte `Scanner` que permet llegir dades.
 * @author adomingo
 */
fun scan(): Scanner {
    return Scanner(System.`in`)
}

/**
 * Funció que ens permet llegir un document de text
 * linia per linia sense carregar-lo completament en memòria
 */
fun mostrarFitxer(ruta : Path){
    ruta.bufferedReader().use { reader ->
        var linia = reader.readLine()
        while (linia != null) {
            println(linia)
            linia = reader.readLine()
        }
    }
}

/**
 * Funcio que ens permet consultar una linia en
 * concret del fitxer accedint amb un [Int]
 */
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




/**
 * Llegeix un nombre enter des de l'entrada estàndard, demanant a l'usuari que introdueixi un valor vàlid.
 * Si l'usuari no introdueix un nombre enter, es demanarà de nou fins que es faci una entrada vàlida.
 *
 * @param scan L'objecte `Scanner` que es fa servir per llegir les dades.
 * @param missatge El missatge que es mostrarà a l'usuari abans de llegir el nombre.
 * @return El nombre enter llegit des de l'entrada.
 * @author adomingo
 */
fun readInt(scan: Scanner, missatge: String): Int {
    var nombre: Int = 0
    println(missatge)
    // Comprova si l'entrada és un enter, sinó demana-ho de nou
    while (!scan.hasNextInt()) {
        println("Això no és un nombre, escriu un nombre enter si us plau")
        scan.nextLine()  // Llegim la línia incorrecta per evitar bloquejar el scanner
    }
    nombre = scan.nextInt()  // Llegeix el nombre enter
    return nombre
}

/**
 * Llegeix una cadena no buida des de l'entrada estàndard.
 * Si l'usuari introdueix una cadena buida, es demanarà de nou fins que introdueixi alguna cosa.
 *
 * @param scan L'objecte `Scanner` que es fa servir per llegir les dades.
 * @param missatge El missatge que es mostrarà a l'usuari abans de llegir la cadena.
 * @return La cadena no buida llegida des de l'entrada.
 * @author adomingo
 */
fun readString(scan: Scanner, missatge: String): String {
    var linia: String

    println(missatge)
    linia = scan.nextLine().trim()  // Llegim la línia i eliminem espais al principi i al final

    // Continuem demanant fins que la línia no estigui buida
    while (linia.isEmpty()) {
        println("Has d'introduir una paraula o lletra (Format String).")
        linia = scan.nextLine().trim()  // Tornem a llegir l'entrada
    }

    return linia  // Retorna la línia llegida
}

/**
 * Funció que tanca escàner quan hem acabat
 * de fer-lo servir
 */
fun tancarScanner() {
    return Scanner(System.`in`).close()
}


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




/**
 * Llegeix un nombre de tipus Double des de l'entrada estàndard.
 * Si l'usuari no introdueix un valor vàlid de tipus Double, es demanarà de nou fins que ho faci.
 *
 * @param scan L'objecte `Scanner` que es fa servir per llegir les dades.
 * @param missatge El missatge que es mostrarà a l'usuari abans de llegir el nombre.
 * @return El nombre de tipus Double llegit des de l'entrada.
 * @author adomingo
 */
fun readDouble(scan: Scanner, missatge: String): Double {
    var double: Double = 0.0
    println(missatge)
    // Comprova si l'entrada és un Double, sinó demana-ho de nou
    while (!scan.hasNextDouble()) {
        println("Has d'introduir un nombre en format Double si us plau")
        scan.nextLine()  // Llegim la línia incorrecta per evitar bloquejar el scanner
    }
    double = scan.nextDouble()  // Llegeix el nombre Double
    return double  // Retorna el valor Double
}

/**
 * Llegeix un nombre de tipus Float des de l'entrada estàndard.
 * Si l'usuari no introdueix un valor vàlid de tipus Float, es demanarà de nou fins que ho faci.
 *
 * @param scan L'objecte `Scanner` que es fa servir per llegir les dades.
 * @param missatge El missatge que es mostrarà a l'usuari abans de llegir el nombre.
 * @return El nombre de tipus Float llegit des de l'entrada.
 * @author adomingo
 */
fun readFloat(scan: Scanner, missatge: String): Float {
    var Float: Float = 0.0f
    println(missatge)
    // Comprova si l'entrada és un Double, sinó demana-ho de nou
    while (!scan.hasNextDouble()) {
        println("Has d'introduir un nombre en format Float si us plau")
        scan.nextLine()  // Llegim la línia incorrecta per evitar bloquejar el scanner
    }
    Float = scan.nextFloat()  // Llegeix el nombre Float
    return Float  // Retorna el valor Float
}

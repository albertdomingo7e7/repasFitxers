package M06_RA1_TA38_FitxersBinaris
import sun.security.jgss.GSSToken.readInt
import utilities.copiarFitxer
import utilities.demanarString
import utilities.existeixClient
import utilities.guardarClient
import utilities.mostrarError
import utilities.mostrarMenu
import utilities.obrirFitxerLectura
import utilities.readInt
import utilities.seguentRegistre
import utilities.tancarScanner

//Opcions de menu
enum class opcionsMenu(var text: String, var opcio:Int) {
    alta("Alta d'un client", 1),
    consultaPosicio("Consulta per posició", 2),
    consultaCodi("Consulta per codi", 3),
    modificacio("Modificar un client", 4),
    esborrarClient("Esborrar un client", 5),
    llistarTots("Llistar tots els clients", 6),
    crearFitxerProves("Crear Fitxer de Proves", 7),
    sortir("Sortir", 8)
}

//Definir la data class del Client
data class Client (
    var codi: Int,
    var nom: String,
    var cognoms: String,
    var diaNaixement: Int,
    var mesNaixement: Int,
    var anyNaixement: Int,
    var adrecaPostal: String,
    var email: String,
    var esVIP:Boolean
)

//Nom del fitxer de clients
const val FITXER_CLIENTS = "./src/clients.dat"
const val CODI_CLIENT_NO_EXISTEIX = Integer.MIN_VALUE


fun main() {
    do {
        var opcio: Int = mostrarMenu()
        gestionarMenu(opcio)
    } while (opcio != opcionsMenu.sortir.opcio)

    tancarScanner()
}

fun gestionarMenu(opcio: Int) {
    when (opcio) {
        opcionsMenu.alta.opcio -> altaClient()
        opcionsMenu.consultaCodi.opcio -> consultaClientCodi()
        opcionsMenu.consultaPosicio.opcio -> consultaClientPosicio()
        opcionsMenu.modificacio.opcio -> modificacioClient()
        opcionsMenu.esborrarClient.opcio -> esborrarClient()
        opcionsMenu.llistarTots.opcio -> llistarTots()
        opcionsMenu.crearFitxerProves.opcio -> crearFitxerProves()
        opcionsMenu.sortir.opcio -> {}  //No es fa res
    }
}

/**
 * Obre el fitxer, el recorre fins al final (quan retorni un client amb codi buit)
 * i va mostrant les dades de cada client
 */
fun llistarTots() {
    println("*** Llistat de clients ***")

    var lectorClients = obrirFitxerLectura()

    //Llegir tots els clients
    var client = seguentRegistre(lectorClients)
    while (client.codi != CODI_CLIENT_NO_EXISTEIX ) {
        mostrarDadesClient(client)
        client = seguentRegistre(lectorClients)
    }
    lectorClients.close()
}

/**
 * Esborrar un client
 * Demana el codi del client a esborrar (mostra error i surt si no existeix)
 * Fa una còpia del fitxer, registre a registre, ignorant el client a esborrar
 */
fun esborrarClient() {
    var client:Client = nouClient()
    client.codi = readInt("Quin és el codi del client a esborrar? ")
    if ( !existeixClient(client) ) {
        mostrarError("No hi ha cap client amb aquest codi!")
    } else {
        copiarFitxer(client, nouClient())
    }
}

/**
 * Modificar un client
 * Demana el codi del client a modificar (mostra error i surt si no existeix)
 * Demana les dades noves del client (mostra error si canvia el codi i aquest ja existeix)
 * Fa una còpia del fitxer, registre a registre, ignorant les dades antigues del client
 * i canviant-les per les noves
 */
fun modificacioClient() {
    var clientAntic = nouClient()
    clientAntic.codi = readInt("Quin és el codi del client que vols reemplaçar? ")

    if (!existeixClient(clientAntic)) {
        mostrarError("No hi ha cap client amb aquest codi!")
    } else {
        var nouClient = demanarDadesClient()
        if (existeixClient(nouClient) && nouClient.codi != clientAntic.codi) {
            mostrarError("Ja hi ha un client amb aquest codi!")
        } else {
            copiarFitxer(clientAntic, nouClient)
        }
    }
}

/**
 * Consultar el client per la seva posició al fitxer
 * Va saltant-se dades fins a arribar a la posició demanada
 * Finalment, mostra les dades del client retornat
 */
fun consultaClientPosicio() {
    var posicio = readInt("A quina posició es troba el client que vols consultar? ")

    var lectorClients = obrirFitxerLectura()

    //Llegir tots els clients fins arribar al que volem
    var client = seguentRegistre(lectorClients)
    var index : Int = 1
    while (index!=posicio && client.codi != CODI_CLIENT_NO_EXISTEIX) {
        client = seguentRegistre(lectorClients)
        index++
    }
    lectorClients.close()

    mostrarDadesClient(client)
}

/**
 * Mostra les dades d'un client.
 * Si el codi està buit, és que no s'han trobat dades.
 */
fun mostrarDadesClient(client: Client) {
    if (client.codi == CODI_CLIENT_NO_EXISTEIX) {
        println("No s'han trobat dades per aquest client!")
    } else {
        println("Dades del client ${client.codi}")
        println("Nom::${client.nom} Cognoms::${client.cognoms} Adreça::${client.adrecaPostal} DataNaixement ${client.diaNaixement}/${client.mesNaixement}/${client.anyNaixement} Mail::${client.email} VIP::${client.esVIP}")
    }
}

/**
 * Consultar client per codi
 * Demana el codi, recupera el client i mostra les seves dades
 */
fun consultaClientCodi() {
    var codiClientBuscat = readInt("Quin és el codi del client que vols consultar? ")
    var client = recuperarDadesClient(codiClientBuscat)
    mostrarDadesClient(client)
}

/**
 * Recupera les dades d'un client a partir del seu codi
 * Recorre el fitxer fins que troba un client amb el codi demanat, o bé s'arriba al final del fitxer
 */
fun recuperarDadesClient(codi:Int) : Client {
    var lectorClients = obrirFitxerLectura()

    //Llegir tots els clients fins arribar al que volem
    var client = seguentRegistre(lectorClients)
    while (client.codi != CODI_CLIENT_NO_EXISTEIX && client.codi != codi) {
        client = seguentRegistre(lectorClients)
    }
    lectorClients.close()

    return client
}

/**
 * Estàs fart d'introduïr dades per refer el fitxer?
 * Aquí tens la solució! Pots afegir tants registres com vulguis!
 */
fun crearFitxerProves() {
    var llistaClients: MutableList<Client> = mutableListOf()
    llistaClients.add(Client(123,"Santi","Rivas",7, 11, 1971, "C/Major, 20", "santi@mymail.com", true))
    llistaClients.add(Client(456,"Dani","Santiago",1, 1, 1980, "C/Vallès, 120", "dani@mymail.com", true))
    llistaClients.add(Client(789,"Alicia","Vázquez",1,1,1985, "C/Sant Vicenç, 320", "alicia@mymail.com", true))
    llistaClients.add(Client(1230002,"Nom massa llarg que no hi cap en 20 caracters","CognomsCognomsCognomsCognomsCognomsCognomsCognomsCognoms",1,1,2000, "Adreça postal nº 3", "elMail@mymail.com", false))
    llistaClients.add(Client(9000,"Nou mil","Cognoms",31,12,2000, "C/Adreça", "mail@mymail.com",false))
    llistaClients.add(Client(5555,"Cinc mil","Cinc-cents cinquanta-cinc",5,5,2005, "C/Cinc, 5  5e5a", "v5@mymail.com", false))

    for (c in llistaClients) {
        guardarClient(c)
    }

}

/**
 * Demana les dades d'un client, vigila que el codi no existeixi i el guarda
 */
fun altaClient() {
    var client:Client = demanarDadesClient(true)
    if ( !existeixClient(client) )
        guardarClient(client)
    else
        mostrarError("ERROR: el client ja existeix!")
}


/**
 * Genera un data class Client amb tots els camps buits o valors per defecte
 */
fun nouClient(): Client {
    return Client(CODI_CLIENT_NO_EXISTEIX,"","",1,1,1900, "", "", false)
}

/**
 * Demana les dades d'un client, les retalla a la seva longitud màxima
 *
 * Fart d'introduïr sempre el mateix registre?  Pots dir-li que crei un registre "fake" predeterminat
 */
fun demanarDadesClient(fake: Boolean = false) : Client {
    var client:Client = nouClient()

    if (!fake) {
        client.codi = readInt("Codi: ",1)
        client.nom = demanarString("Nom: ")
        client.cognoms = demanarString("Cognoms: ")
        client.diaNaixement = readInt("Dia data de Naixement (1-31): ", 1, 31)
        client.mesNaixement = readInt("Mes data de Naixement (1-12): ", 1, 12)
        client.anyNaixement = readInt("Any data de Naixement (>1900): ", 1900)
        client.adrecaPostal = demanarString("Adreça Postal: ")
        client.email = demanarString("Email: ")
        client.esVIP = demanarString("Es VIP (S/N)? ") == "S"
    } else {
        client = Client(123,"Santi","Rivas", 1,1,2001,"C/Pi","a@b.com", true)
    }
    return client
}


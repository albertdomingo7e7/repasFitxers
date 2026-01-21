package utilities

import M06_RA1_TA38_FitxersBinaris.CODI_CLIENT_NO_EXISTEIX
import M06_RA1_TA38_FitxersBinaris.Client
import M06_RA1_TA38_FitxersBinaris.FITXER_CLIENTS

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.EOFException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream



/**
 * Obre un fitxer de i retorna el seu DataInputStream corresponent
 * Si no s'indica el nom del fitxer s'assumeix que és el fitxer de clients
 */
fun obrirFitxerLectura(nom:String = FITXER_CLIENTS) : DataInputStream {
    var fitxerClients = File(nom)
    if (!fitxerClients.exists()) {
        fitxerClients.createNewFile()
    }
    var fr = FileInputStream(fitxerClients.absolutePath)
    var br: DataInputStream = DataInputStream(fr)

    return br
}

/**
 * LLegeix les linies del fitxer de clients fins que troba el codi
 * o bé arriba al final del fitxer
 */
fun existeixClient(client: Client): Boolean {
    var lectorClients = obrirFitxerLectura()

    var seguent = seguentRegistre(lectorClients)
    var trobat = false
    while (!trobat && seguent.codi != CODI_CLIENT_NO_EXISTEIX) {
        if (seguent.codi == client.codi) {
            trobat = true
        }
        seguent = seguentRegistre(lectorClients)
    }
    lectorClients.close()
    return trobat
}

/**
 * Llegeix el següent registre d'un fitxer de clients
 */
fun seguentRegistre(lectorClients: DataInputStream) : Client {
    var client:Client = nouClient()

    try {
        client.codi = lectorClients.readInt()
        client.nom = lectorClients.readUTF()
        client.cognoms = lectorClients.readUTF()
        client.diaNaixement = lectorClients.readInt()
        client.mesNaixement = lectorClients.readInt()
        client.anyNaixement = lectorClients.readInt()
        client.adrecaPostal = lectorClients.readUTF()
        client.email = lectorClients.readUTF()
        client.esVIP = lectorClients.readBoolean()
    } catch (e: EOFException) {
        // S'ha arribat al final del fitxer, no hi ha nou client
        client.codi = CODI_CLIENT_NO_EXISTEIX
    }
    return client
}


/**
 * Guarda el client al final del fitxer de clients
 */
fun guardarClient(client: Client) {
    var fitxerClients = File(FITXER_CLIENTS)
    var fos : FileOutputStream = FileOutputStream(fitxerClients, true)
    var dos : DataOutputStream = DataOutputStream(fos)

    escriureClient(client, dos)

    dos.flush()
    fos.close()
}

/**
 * Escriu un client seguint l'ordre dels camps de la seva definició
 */
fun escriureClient(client: Client, sortida: DataOutputStream) {
    sortida.writeInt(client.codi)
    sortida.writeUTF(client.nom)
    sortida.writeUTF(client.cognoms)
    sortida.writeInt(client.diaNaixement)
    sortida.writeInt(client.mesNaixement)
    sortida.writeInt(client.anyNaixement)
    sortida.writeUTF(client.adrecaPostal)
    sortida.writeUTF(client.email)
    sortida.writeBoolean(client.esVIP)
}


/**
 * Copiar el fitxer original sobre un fitxer auxiliar
 * Llegirem l'auxiliar i regenerem l'original
 * A més copia tots els registres de l'auxiliar sobre l'original
 * En trobar el clientAntic el substitueix pel clientNou, excepte si té el codi buit
 * que llavors l'ignora (l'esborra)
 */
fun copiarFitxer(clientAntic: Client, clientNou: Client) {
    var nomFitxerAuxiliar : String = FITXER_CLIENTS + ".aux"
    var fitxerClients = File(FITXER_CLIENTS)
    var fitxerClientsAux = File(nomFitxerAuxiliar)
    clonarFitxerAuxiliar(fitxerClients, fitxerClientsAux)

    //Llegir tots els clients de la còpia auxiliar i passar-los al fitxer regenerat
    var lectorClients = obrirFitxerLectura(nomFitxerAuxiliar)
    var client = seguentRegistre(lectorClients)
    while (client.codi != CODI_CLIENT_NO_EXISTEIX) {
        if (clientAntic.codi == client.codi) {
            if (clientNou.codi != CODI_CLIENT_NO_EXISTEIX) {    //Què cal fer amb el client que s'ha passat per paràmetre?
                guardarClient(clientNou)   //Reemplaçar el client
            } else { }                     //Si no fem res, s'elimina aquest registre
        }
        else guardarClient(client)
        client = seguentRegistre(lectorClients)
    }
    lectorClients.close()

    //Esborrar el fitxer auxiliar
    fitxerClientsAux.delete()
}

/**
 * Copia el fitxerOriginal a l'auxiliar i neteja l'original
 */
fun clonarFitxerAuxiliar(fitxerOriginal: File, fitxerAuxiliar: File) {
    //Copia el fitxer original a l'auxiliar
    fitxerOriginal.copyTo(fitxerAuxiliar)
    //Regenera el fitxer original
    fitxerOriginal.delete()
    fitxerOriginal.createNewFile()
}
package it.mandeep.client.chat;

import it.mandeep.libreria.datastructures.Messaggio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatHandler extends Thread {

    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Messaggio messaggio;

    /**
     * Unico costruttore della classe, consentedi inizializzare il Socker del Client da gestire.
     * @param client oggetto client da gestire.
     */
    ChatHandler(Socket client) {
        this.client = client;
    }

    /**
     * Metodo run della classe Thread, gestisce l'effettiva richiesta ricevuta dal client.
     */
    @Override
    public void run() {
        // Inizializza gli stream di input ed output.
        try {
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
        } catch (IOException ex) {
            System.err.println("Errore durante l'inizializzazione degli stream.. " + ex.getMessage());
        }

        try {
            messaggio = (Messaggio) in.readObject();
        } catch (IOException ex) {
            System.err.println("Errore durante la lettura del messaggio.. " + ex.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
                out.close();
                in.close();
            } catch (IOException ex) {
                System.err.println("Errore durante la chiusura della connessione: " + ex.getMessage());
            }
        }
    }
}
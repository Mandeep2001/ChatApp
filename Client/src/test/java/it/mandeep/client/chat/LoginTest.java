package it.mandeep.client.chat;

import it.mandeep.client.networking.RequestThread;
import it.mandeep.libreria.datastructures.Utente;
import it.mandeep.libreria.network.richiesta.ConcreteRichiestaBuilder;
import it.mandeep.libreria.network.richiesta.Richiesta;
import it.mandeep.libreria.network.richiesta.RichiestaBuilder;
import it.mandeep.libreria.network.richiesta.TipoRichiesta;
import it.mandeep.libreria.network.risposta.Risposta;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

    Utente mittente = new Utente("Mandeep", "Singh", "Dio", "1234");

    @Test
    void loginTest() {
        RichiestaBuilder richiestaBuilder = new ConcreteRichiestaBuilder();
        Richiesta richiesta = richiestaBuilder.buildTipoRichiesta(TipoRichiesta.LOGIN).buildMittente(mittente)
                .build();

        // Invio di una richiesta al server
        RequestThread requestThread = new RequestThread(richiesta);
        requestThread.start();
        try {
            requestThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Ricezione di una risposta
        Risposta risposta = requestThread.getRisposta();
        assertEquals(risposta.getRisultatoRisposta(), 0);
    }
}

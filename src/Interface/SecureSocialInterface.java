package Interface;

import Eccezioni.NotAllowedActionException;
import Eccezioni.PermessoNegatoException;
import com.company.Post;

import java.util.NoSuchElementException;

public interface SecureSocialInterface {

    /*REQUIRES: true
     *THROWS:
     *EFFECTS: Questo metodo permette ad un utente di segnalare un post della rete sociale e se si supera
     *1 segnalazione per lo stesso post, quest'ultimo viene rimosso dalla rete sociale
     *MODIFIES: posts
     */
    public void segnalaPost(int id, String segnalatore)
            throws IllegalArgumentException, PermessoNegatoException,
            NoSuchElementException, NotAllowedActionException;

    /*REQUIRES: true
     *THROWS:
     *EFFECTS: valuta la segnalazione fatta al post preso come argomento
     *MODIFIES:
     */
    public boolean valutaSegnalazione(Post p) throws NullPointerException;

    /*REQUIRES: true
     *THROWS:
     *EFFECTS: stampa tutti i post che sono stati segnalati
     *MODIFIES:
     */
    public void printSegnalati();

}

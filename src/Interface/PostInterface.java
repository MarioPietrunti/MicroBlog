package Interface;

import java.sql.Timestamp;

public interface PostInterface {
    /*REQUIRES: true
     *THROWS:
     *EFFECTS: restituisce l'ID del post
     *MODIFIES:
     */
    int getId();

    /*REQUIRES: true
     *THROWS:
     *EFFECTS: restituisce l'utente che è autore del post
     *MODIFIES:
     */
    String getAuthor();

    /*REQUIRES: true
     *THROWS:
     *EFFECTS: restituisce il testo del post
     *MODIFIES:
     */
    String getText();

    /*REQUIRES: true
     *THROWS:
     *EFFECTS: restituisce la data e l'ora in cui e' stato scritto il post
     *MODIFIES:
     */
    Timestamp getTimestamp();

    /*REQUIRES: true
     *THROWS:
     *EFFECTS: aumenta di uno il numero di segnalazioni ogni volta che ne viene effettuata una
     *MODIFIES:
     */
    public void aggiungiSegnalazione();

    /*REQUIRES: true
     *THROWS:
     *EFFECTS: Stampa il post nel seguente formato
     *MODIFIES:
     */
    String toString();
}

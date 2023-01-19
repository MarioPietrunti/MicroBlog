package com.company;

import Eccezioni.*;
import Interface.PostInterface;
import java.sql.Timestamp;

public class Post  implements PostInterface, Comparable<Post>{
    /*
     * OVERVIEW: Post e' una classe mutabile. Ogni post ha un id univoco, un autore che
     * puo' essere modificato,un testo modificabile che deve essere non vuoto (0 caratteri) e al
     * massimo lungo 140 caratteri e il numero di segnalazioni che un determinato post ha ricevuto.
     * Inoltre è presente un timestamp che indica la data e l'orario della creazione del post
     *
     * TYPICAL ELEMENT: <id, author, text, timestamp, segnalazioni>. Con id, author,text e segnalazionei(inizializzata a 0) passati dal
     * costruttore, timestamp generato automaticamente.
     *
     * FUNZIONE DI ASTRAZIONE:
     * <id, author, text, timestamp,segnalazioni> dove:
     * id (int) -> identificatore del post (univocita' garantita in SocialNetwork)
     * author (String) -> autore del post (validazione dell'input in SocialNetwork)
     * text (String) -> testo del post con 0<text.length<=140
     * timestamp (Timestamp) -> data e ora di invio del post
     * segnalazioni (int) -> numero di segnalazioni ricevute dal post
     *
     * INVARIANTE DI RAPPRESENTAZIONE:
     * author != null && text!=null && (0<text.length<=140)
     */
    private final int id;
    private String author;
    private String text;
    private final Timestamp timestamp;
    private int segnalazioni;

    public Post(int id, String author, String text)throws IllegalArgumentException, TxtFormatException {
        if(author == null)
            throw new IllegalArgumentException("Eccezione: Il nome dell'utente non può essere nullo");
        if(text == null)
            throw new IllegalArgumentException("Eccezione: il post non può essere vuoto");

        this.author = author;
        this.id = id;
        this.text = text;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.segnalazioni = 0;

    if(text.length() > 140 || text.isBlank())
        throw new TxtFormatException("Formato non valido: il testo deve essere lungo almeno un carattere" +
                " e massimo 140");
        try {
            //ho introdotto un ritardo per differenziare i timestamp dei post
            Thread.sleep(105);
        } catch (InterruptedException e) {
            System.err.println("Eccezione: thread interrotto!");
        }
    }
    /*
     * OVERVIEW:
     * Questo metodo restituisce l'id del post
     */
    public int getId() {
        return id;
    }
    /*
     * OVERVIEW:
     * Questo metodo restituisce l'autore del post
     */
    public String getAuthor() {
        return author;
    }
    /*
     * OVERVIEW:
     * Questo metodo restituisce il testo del post
     */
    public String getText() {
        return text;
    }
    /*
     * OVERVIEW:
     * Questo metodo restituisce la data e l'orario in cui il post e' stato inserito
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }
    /*
     * OVERVIEW:
     * Questo metodo restituisce il numero di segnalazioni ricevute dal post
     */
    public int getSegnalazioni() {
        return segnalazioni;
    }
    /*
     * OVERVIEW:
     * Questo metodo imposta l'autore del post
     */
    /*REQUIRES: author != null
     *THROWS: IllegalArgument Exception if author == null
     *EFFECTS: imposta l'utente del SocialNetwork che ha scritto il post
     *MODIFIES: this.author
     */
    public void setAuthor(String author)throws IllegalArgumentException{
        if(author == null)
            throw new IllegalArgumentException("Eccezione: Il nome dell'utente non può essere nullo");
        this.author = author;
    }
    /*
     * OVERVIEW:
     * Questo metodo imposta il testo del post, che non deve essere vuoto e deve avere
     * un massimo di 140 caratteri
     */
    /*REQUIRES: text != null && 0<text.length<=140
     *THROWS: IllegalArgument Exception if text==null
     *		and TxtFormat Exception(custom) if text.lenth>140 || text.length<=0
     *EFFECTS: imposta il testo del post (non vuoto e massimo 140 caratteri)
     *MODIFIES: this.text
     */
    public void setText(String text) throws IllegalArgumentException,TxtFormatException{
        if(text == null)
            throw new IllegalArgumentException("Eccezione: il post non può essere vuoto");
        if(text.length() > 140 || text.isBlank())
            throw new TxtFormatException("Formato non valido: il testo deve essere lungo almeno un carattere e massimo 140");
        this.text = text;
    }
    /*
     * OVERVIEW:
     * Questo metodo aumenta di uno il numero di segnalazioni ogni volta che ne viene effettuata una
     */
    public void aggiungiSegnalazione() {
        this.segnalazioni++;
    }
    /*
     * OVERVIEW:
     * Questo metodo stampa un post con un formato leggibile e distinguendo i seguenti
     * campi: id, autore,testo del post, timestamp e il numero di segnalazioni
     */
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                ", segnalazioni=" + segnalazioni +
                '}';
    }
    @Override
    public int compareTo(Post p){
        if(p.getId()>id) return -1;
        if(p.getId()<id) return 1;

        return 0;
    }
}

package com.company;

import Eccezioni.*;
import Interface.SecureSocialInterface;
import java.util.*;


public class SecureSocial extends SocialNetwork implements SecureSocialInterface {
    /*
     * OVERVIEW: SecureSocial e' una collezione mutabile di post e utenti, in cui sono
     * presenti tutte le funzionalita' della classe SocialNetwork con l'aggiunta di un sistema
     * segnalazione dei post.Ogni utente puo' segnalare
     * un post tramite il suo id; il post segnalato viene valutato in base alla presenza di una parola generica (BADWORD)
     * e se presente nella rete sociale verra' aggiunto nella lista dei post segnalati.
     * E' inoltre alla seconda segnalzione di un post, quest'ultimo viene rimosso dalla lista dei post della rete sociale.
     *
     * TYPICAL ELEMENT: <social, followers, posts, segnalati>.
     *
     * FUNZIONE DI ASTRAZIONE:
     *
     * 	Map<Integer, String> segnalati dove:
     * 	Integer -> l'id del post
     * 	String -> e' uno username del giusto formato
     *
     * INVARIANTE DI RAPPRESENTAZIONE:
     * ForAll <id,user> in segnalati . user is in social && exists p in
     * posts . p.id == id && p.author==user
     */

    private Map<Integer, String> segnalati;

    //Ho preferito utilizzare una parola generale che ogni volta che viene usata rende il post
    //offensivo e da cancellare, in modo da simulare un criterio per la gestione di contenuti offensivi
    private static final String BADWORD= "*****";

    public SecureSocial(){
        super();
        segnalati = new HashMap<>();
    }
    /*
     * OVERVIEW:
     * Questo metodo permette ad un utente di segnalare un post della rete sociale
     */
    public void segnalaPost(int id, String segnalatore)
            throws IllegalArgumentException, PermessoNegatoException,
    NoSuchElementException, NotAllowedActionException {
        if(segnalatore == null)
            throw new IllegalArgumentException ("Eccezione: il nome utente non può essere nullo!");
        if(!(social.containsKey(segnalatore)))
            throw new PermessoNegatoException("Permesso negato: Il post non può essere segnalato da chi non è registrato");
        for(Post p : posts){
            //se l'id coincide aggiungo la segnalazione
            if(p.getId() == id){
                if(p.getAuthor().equals(segnalatore))
                    throw new NotAllowedActionException("Azione non consentita: gli utenti non possono segnalare i propri post");
                for(Map.Entry<Integer, String> r : segnalati.entrySet()){
                    if(r.getKey() == id && r.getValue().equals(segnalatore))
                        throw new NotAllowedActionException("Azione non consentita: un utente non può segnalare più volte lo stesso post");
                }
                if(valutaSegnalazione(p)) {
                    p.aggiungiSegnalazione();
                    if (!segnalati.containsKey(p))
                        segnalati.put(p.getId(), segnalatore);
                }
                //se le segnalazioni inerenti a quel posto sono più di 1 cancello il post
                if(p.getSegnalazioni() > 1)
                    posts.remove(p);
                return;
            }
        }
        throw new NoSuchElementException("Eccezione: La rete sociale non contiene quell'id!");
    }
    /*
     * OVERVIEW:
     * Questo metodo valuta se la segnalazione fatta da un segnalatore (diverso dall'autore del post) ad un determinato post
     * è sufficiente per poter rimuovere il post dalla rete sociale. Lo fa controllando se sono presenti parole
     * offensive all'interno del testo, che ho deciso di gestire con un generica parola BADWORD(*****)
     */
    public boolean valutaSegnalazione(Post p) throws NullPointerException{
        if(p == null)
            throw new NullPointerException("Eccezione: gli elementi in input non possono essere nulli");
        if(p.getText().toLowerCase().contains(BADWORD))
            return true;
        else
            return false;
    }
    /*
     * OVERVIEW
     * Questo metodo stampa i post che hanno ricevuto una segnalazione.
     */
    public void printSegnalati(){
        for (Map.Entry<Integer, String> r : segnalati.entrySet()) {
            System.out.println("Post #" + r.getKey() + " è stato segnalato da " + r.getValue());
        }
    }

}



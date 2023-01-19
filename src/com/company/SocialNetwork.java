package com.company;

import Eccezioni.*;
import Interface.SocialNetworkInterface;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * OVERVIEW: SocialNetwork e' una collezione mutabile di post e utenti, in cui ogni
 * utente puo' mettere like al post di un altro (e quindi seguirlo),
 * taggarlo, seguirlo.
 *
 * TYPICAL ELEMENT: <social, followers, posts>.
 *
 * FUNZIONE DI ASTRAZIONE:
 * Map<String, Set<String>> social dove:
 * 	String -> e' uno username del giusto formato
 * 	Set<String> -> la lista degli utenti registrati seguiti dallo username
 * 	di cui sopra.
 *
 * Map<String, Set<String>> followers dove:
 * 	String -> username del giusto formato
 * 	Set<String> -> la lista degli utenti registrati che seguono lo username
 * 	di cui sopra.
 *
 * Set<Post> posts dove:
 * 	Post -> e'un post come definito nella classe Post
 *
 * INVARIANTE DI RAPPRESENTAZIONE:
 * 	social != null && posts != null && followers !=null
 *    //parte dell'invariante relativa alla map SOCIAL
 *	&& {ForAll <user, following> in social . user != null && following != null
 * && [~Exist u in following . u== null && u==user]
 * && 15=>user.length=>4 &&
 *	ForAll c in user . c=='0..9' || c=='a..z' || c=='A..Z' || c=='.' || c=='_'}
 *    //parte dell'invariante relativa alla map FOLLOWERS
 *	&& {ForAll <user, followedBy> in followers . user != null &&
 * followedBy != null && [~Exist u in followedBy . u == null && u==user]
 * && 15=>user.length=>4 &&
 *	ForAll c in user . c=='0..9' || c=='a..z' || c=='A..Z' || c=='.' || c=='_'}
 *    //parte dell'invariante relativa al set POSTS
 *	&& ForAll p in posts . p != null
 */

public class SocialNetwork implements SocialNetworkInterface {
    protected Map<String, Set<String>> social;
    protected Map<String, Set<String>> followers;
    protected Set<Post> posts;

    private static final String TAG = "@";
    private static final String  NOMEVALIDO=
            "\\B"+TAG+
                    "(?!(?:[a-z0-9.]*_){2})(?!(?:[a-z0-9_]*\\.){2})[._a-z0-9]{"+
                    4+","+15+"}\\b";

    public SocialNetwork(){
        social = new HashMap<>();
        followers = new HashMap<>();
        posts = new TreeSet<>();
    }
    /*
     * OVERVIEW:
     * Questo metodo restituisce la rete sociale derivata dalla lista di post,
     * dove ogni elemento della map ha un campo chiave (l'utente), e un campo
     * valore (gli autori che sta seguendo). Assumiamo che mettendo follow
     * a un utente o like all'id di un suo post lo si cominci a seguire.
     */
    public Map<String, Set<String>> guessFollowers(List<Post> ps)
            throws IllegalArgumentException, NullPointerException, NoSuchElementException{
        if(ps == null)
            throw new IllegalArgumentException("Eccezione: La lista di input non può essere nulla");
        //distinguo i tre tipi di post nella lista: post normali, follow e like
        HashMap<Integer,Post> postsMap = new HashMap<>();
        HashMap<Integer,Post> followsMap = new HashMap<>();
        HashMap<Integer,Post> likesMap = new HashMap<>();
        //divido i post in base alla tipologia
        for(Post p : ps){
            if(p == null)
                throw new NullPointerException("Eccezione: gli elementi di input non possono essere nulli");
            //i follows sono dei post che contengono la parola "follow:"
            //seguito dal nome dell'utente che l'autore vuole seguire.
            if (p.getText().toLowerCase().contains("follow:"))
                followsMap.put(p.getId(), p);
            //i like sono dei post che contengono la parola "like:"
            //seguito dall'id del post a cui si vuole mettere like.
            else if (p.getText().toLowerCase().contains("like:"))
                    likesMap.put(p.getId(), p);
            //i post rimanenti sono quelli normali dove non viene fatta alcuna azione di follow o like
            else postsMap.put(p.getId(), p);
        }
        //per ogni azione di following prendo l'autore che l'utente vuole seguire
        //e lo aggiungo alla lista degli autori che sta seguendo.
        for (Post f : followsMap.values()) {
            //separo la keyword "follow" dal nome dell'autore che voglio seguire
            String[] splitText = f.getText().split(":");
            //prendo l'autore che voglio seguire
            String followedAuthor = splitText[1].trim();
            //controllo che l'autore esista e che l'utente non lo stia gia'
            //seguendo
            if (!social.containsKey(followedAuthor))
                throw new NoSuchElementException("Eccezione: il social network non contiene tale utente!");
            boolean isFollowing =
                    getFollowing(f.getAuthor()).contains(followedAuthor);
            //se lo sta gia' seguendo passo avanti
            if(isFollowing) continue;
            //altrimenti inserisco l'utente alla lista dei follower dell'autore
            //a cui ha messo follow
            try {
                startFollowing(f.getAuthor(), followedAuthor);
            } catch (IllegalArgumentException | NoSuchElementException
                    | NotAllowedActionException e) {
                e.printStackTrace();
            }
        }
        //per ogni like prendo il post corrispondente (tramite l'id)
        //aggiungo chi ha messo like nel set dei follower dell'autore del post
        for (Post l : likesMap.values()) {
            //separo la keyword "like" dall'id del post a cui voglio mettere like
            String[] splitText = l.getText().split(":");
            Post likedPost = null;
            //prendo l'id del post a cui l'utente ha messo like
            try {
                int idLiked = Integer.parseInt(splitText[1].trim());
                //controllo che il post esista (non null) e mi ricavo il post originario
                //tramite il suo id
                likedPost = postsMap.get(idLiked);
            } catch(NumberFormatException e) {
                System.err.println("Formato non valido: l'id dovrebbe essere un numero");
                e.printStackTrace();
            }
            if (likedPost==null)
                throw new NoSuchElementException("Eccezione: il social network non contiene tale ID!");
            boolean isFollowing =
                    getFollowing(l.getAuthor()).contains(likedPost.getAuthor());
            boolean isAutoLike =
                    l.getAuthor().equals(likedPost.getAuthor());
            //se sta gia' seguendo l'autore o ha messo like a un suo stesso post
            //passo avanti. Non si tratta di errori.
            if(isFollowing || isAutoLike) continue;
            //dato che ho assunto che mettendo like a un autore si diventa suo
            //follower, aggiungo l'autore a cui l'utente ha messo like alla lista
            //degli autori da lui seguiti

            //avendo assunto che il like ad un autore corrisponde a diventare suo follower
            try {
                startFollowing(l.getAuthor(), likedPost.getAuthor());
            } catch (IllegalArgumentException | NoSuchElementException
                    | NotAllowedActionException e) {
                e.printStackTrace();
            }
        }
        return social;
    }
    /*
     * OVERVIEW:
     * Questo metodo restituisce gli utenti più influenti delle rete sociale.
     * Affinche' un autore possa comparire nella lista, deve avere almeno un follower.
     */
    public List<String> influencers() {
        //uso una struttura dati per memorizzare coppie chiave/valore, contenenti
        //autore e relativo numero di follower. Da qui ordino per numero di follower
        //e costruisco una lista ordinata.
        List<String> influencers = new ArrayList<>();
        Map<String, Integer> followedBy = new HashMap<>();
        int followersSize = 0;
        //la mia nuova hashmap e' costituita da tutte le coppie utente/numero di
        //follower ricavate dalla rete sociale.
        for (Map.Entry<String, Set<String>> user : social.entrySet()) {
            followersSize = getFollowers(user.getKey()).size();
            //se l'utente ha zero follower lo salto
            if(followersSize==0) continue;
            followedBy.put(user.getKey(), followersSize);
        }
        //per ordinare l'hashmap in ordine decrescente di valori, chiamo entrySet()
        //che mi da tutti gli elementi della map e converto in una lista
        List<Map.Entry<String, Integer>> sortedByFollowers =
                new ArrayList<>(followedBy.entrySet());
        //poi ordino la lista per ordine decrescente di follower
        sortedByFollowers
                .sort(Map.Entry
                        .comparingByValue(Comparator
                                .reverseOrder()));
        //infine aggiungo i nomi degli influencer alla mia lista di influencer.
        for (Map.Entry<String, Integer> i : sortedByFollowers) {
            influencers.add(i.getKey());
        }
        return influencers;
    }
    /*
     * OVERVIEW:
     * Questo metodo restituisce tutti gli utenti menzionati (inclusi) nella
     * rete sociale. Per ricavarli occorre restituire il campo String della
     * struttura Map<String, Set<String>> che implementa la rete sociale.
     * Assumiamo che, in parole povere, restituisca gli utenti registrati.
     */
    public Set<String> getMentionedUsers() {
        Set<String> mentionedUsers = new HashSet<String>();
        for (Map.Entry<String, Set<String>> user : social.entrySet()) {
            mentionedUsers.add(user.getKey());
        }
        return mentionedUsers;
    }
    /*
     * OVERVIEW:
     * Questo metodo restituisce gli utenti menzionati (inclusi) nella lista dei
     * post. Dunque si comporta come un tag all'interno di un post. Assumiamo che
     * le menzioni siano del formato "@nome_utente".
     * Assumiamo che un utente possa taggare un altro che non sta seguendo, purche'
     * sia iscritto.
     */
    public Set<String> getMentionedUsers(List<Post> ps)
            throws IllegalArgumentException, NullPointerException, NoSuchElementException {
        if (ps == null)
            throw new IllegalArgumentException("Eccezione: l'elenco di input non può essere vuoto!");
        Set<String> taggedAuthors = new HashSet<>();
        Pattern pattern = Pattern.compile(NOMEVALIDO, Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        String taggedUser;
        for (Post p : ps) {
            if (p == null)
                throw new NullPointerException ("Eccezione: gli elementi in input non possono essere nulli");
            matcher = pattern.matcher(p.getText());
            while (matcher.find()) {
                taggedUser = matcher.group().replace(TAG, "");
                if (!social.containsKey(taggedUser))
                    throw new NoSuchElementException("Eccezione: il social network non contiene tale utente!");
                taggedAuthors.add(taggedUser);
            }
        }
        return taggedAuthors;
    }
    /*
     * OVERVIEW:
     * Questo metodo restituisce la lista dei post scritti dall’utente nella
     * rete sociale il cui nome e' dato dal parametro username
     */
    public List<Post> writtenBy(String username)
            throws IllegalArgumentException, NoSuchElementException {
        if (username == null)
            throw new IllegalArgumentException ("Eccezione: il nome utente non può essere nullo! ");
        if (!(social.containsKey(username)))
            throw new NoSuchElementException("Eccezione: il social network non contiene tale utente!");
        List<Post> list = new ArrayList<>();
        for (Post p : posts) {
            if (p.getAuthor().equals(username)) list.add(p);
        }
        return list;
    }
    /*
     * OVERVIEW:
     * Questo metodo restituisce la lista dei post effettuati dall’utente il cui
     * nome e' dato dal parametro username presenti nella lista ps
     */
    public List<Post> writtenBy(List<Post> ps, String username)
            throws IllegalArgumentException, NullPointerException, NoSuchElementException {
        if (username == null)
            throw new IllegalArgumentException("Eccezione: il nome utente non può essere nullo!");
        if (ps == null)
            throw new IllegalArgumentException("Eccezione: l'elenco di input non può essere nullo!");
        if (!(social.containsKey(username)))
            throw new NoSuchElementException("Eccezione: il social network non contiene tale utente!");
        List<Post> list = new ArrayList<>();
        for (Post p : ps) {
            if (p == null)
                throw new NullPointerException ("Eccezione: gli elementi di input non possono essere null");
            if (p.getAuthor().equals(username)) list.add(p);
        }
        return list;
    }
    /*
     * OVERVIEW:
     * Questo metodo restituisce la lista dei post presenti nella rete sociale
     * che includono almeno una delle parole presenti nella lista delle parole
     * argomento del metodo
     */
    public List<Post> containing(List<String> words)
            throws IllegalArgumentException, NullPointerException {
        if (words == null)
            throw new IllegalArgumentException("Eccezione: l'elenco di input non può essere nullo!");
        for(String s : words)
            if (s == null)
                throw new NullPointerException ("Eccezione: l'elenco di input non può essere nullo!");
        List<Post> list = new ArrayList<>();
        for (Post p : posts) {
            for (String s : words) {
                //verifico che il post contenga la parola s
                if (p.getText().toLowerCase().contains(s.toLowerCase())) {
                    list.add(p);
                    break;//almeno *una* parola, quando la trovo esco
                }
            }
        }
        return list;
    }
    /*
     * OVERVIEW:
     * Questo metodo restituisce un post e permette di aggiungerlo alla lista dei post
     * della rete sociale.
     */
    public Post createPost(int id, String username, String text)
            throws IllegalArgumentException, DuplicatiException, PermessoNegatoException {
        if (username==null)
            throw new IllegalArgumentException ("Eccezione: il nome utente non può essere nullo!");
        if (text== null)
            throw new IllegalArgumentException ("Eccezione: il testo non può essere nullo!");
        if (!social.containsKey(username))
            throw new PermessoNegatoException("Permesso negato: l'utente non registrato non può creare il post");
        for (Post p : posts) {
            if (p.getId() == id)
                throw new DuplicatiException ("Duplicato: l'ID è già stato preso da un altro post ");
        }
        Post p = null;
        try {
            p = new Post(id, username, text);
        } catch (IllegalArgumentException | TxtFormatException e) {
            e.printStackTrace();
        }
        posts.add(p);

        return p;
    }
    /*
     * OVERVIEW:
     * Questo metodo permette di rimuovere un post dalla lista dei post della
     * rete sociale. Cio' e' permesso soltanto agli autori del post (chi lo ha
     * scritto) e ai moderatori (cfr. classe ModeratedSocial).
     */
    public void deletePost(String deleter, int id)
            throws IllegalArgumentException, PermessoNegatoException, NoSuchElementException {
        if (deleter==null)
            throw new IllegalArgumentException ("Eccezione: il nome utente non può essere nullo!");
        if (!(social.containsKey(deleter)))
            throw new NoSuchElementException("Eccezione: il social network non contiene tale utente!");
        for (Post p : posts) {
            if (p.getId() == id) {
                //se non l'ho scritto io non posso cancellarlo
                if (!p.getAuthor().equals(deleter))
                    throw new PermessoNegatoException ("Permesso negato: solo gli autori possono cancellare i propri post!");
                posts.remove(p);
                return;// l'id e' univoco, non ne troveremo altri quindi si esce dal for
            }
        }
        //se siamo arrivati alla fine della lista dei post senza trovarne uno con
        //lo stesso id, vuol dire che l'id non e' stato trovato nella lista
        throw new NoSuchElementException ("Eccezione: il social network non contiene tale ID!");
    }
    /*
     * OVERVIEW:
     * Questo metodo permette di registrare un utente nella rete sociale, a
     * condizione che lo username scelto sia valido e che non sia gia' stato preso
     */
    public void createUser(String username)
            throws IllegalArgumentException,TxtFormatException, DuplicatiException {
        if (username==null)
            throw new IllegalArgumentException ("Eccezione: il nome utente non può essere nullo!");
        //Controllo che lo username scelto sia composto da lettere, numeri, dot
        //e underscore e che sia della lunghezza corretta
        boolean isRightLength = (username.length() >= 4
                && username.length() <= 15);
        Pattern pattern =
                Pattern.compile("[A-Za-z0-9_.]+", Pattern.CASE_INSENSITIVE);
        if (!(pattern.matcher(username).matches() && isRightLength)) {
            throw new TxtFormatException("Formato non valido:"+
                    " i nomi utente devono essere lunghi 4-15 caratteri e contenere solo lettere"
                    + ", numeri, punti e trattini bassi.");
        }
        //Se dopo i controlli sullo username non e' gia' preso lo aggiungiamo
        //alla rete sociale. All'inizio un nuovo utente non segue nessuno.
        if (social.containsKey(username))
            throw new DuplicatiException ("Duplicati: questo nome utente è già stato preso!");
        social.put(username, new HashSet<>());
        followers.put(username, new HashSet<>());
    }
    /*
     * OVERVIEW:
     * Questo metodo permette di rimuovere un utente dalla rete sociale (bannarlo).
     * E' concesso solo ai moderatori (cfr. classe ModeratedSocial) e gli altri utenti
     * possono farlo solo con se stessi.
     */
    public void deleteUser(String deleter, String username)
            throws IllegalArgumentException, NoSuchElementException,
            PermessoNegatoException{
        if (deleter==null || username==null)
            throw new IllegalArgumentException ("Eccezione: il nome utente non può essere nullo!");
        if (!(social.containsKey(username) && social.containsKey(deleter)))
            throw new NoSuchElementException ("Eccezione: il social network non contiene tale utente!");
        if (!deleter.equals(username))
            throw new PermessoNegatoException ("Permesso negato: gli utenti possono eliminare solo i propri account!");

        for (Map.Entry<String, Set<String>> user : social.entrySet()) {
            getFollowing(user.getKey()).remove(username);
        }
        for (Map.Entry<String, Set<String>> user : followers.entrySet()) {
            getFollowers(user.getKey()).remove(username);
        }
        social.remove(username);
        followers.remove(username);
    }
    /*
     * Questo metodo restituisce tutti i followers di un autore
     */
    public Set<String> getFollowers(String username)
            throws IllegalArgumentException, NoSuchElementException{
        if (username==null)
            throw new IllegalArgumentException ("Eccezione: il nome utente non può essere nullo!");
        if (!social.containsKey(username))
            throw new NoSuchElementException("Eccezione: il social network non contiene tale utente!");
        Set <String> followersSet = followers.get(username);

        return followersSet;
    }

    /*
     * OVERVIEW:
     * Questo metodo restituisce tutti gli autori seguiti dall'utente
     */
    public Set<String> getFollowing(String username) throws IllegalArgumentException, NoSuchElementException {
        if (username==null)
            throw new IllegalArgumentException ("Eccezione: il nome utente non può essere nullo!");
        if (!social.containsKey(username))
            throw new NoSuchElementException("Eccezione: il social network non contiene tale utente!");
        Set<String> followingSet = social.get(username);

        return followingSet;
    }
    /*
     * OVERVIEW:
     * Questo metodo permette di aggiungere un autore alla lista degli autori
     * seguiti da un utente
     */
    public void startFollowing(String follower, String followed)
            throws IllegalArgumentException, NoSuchElementException, NotAllowedActionException {
        //controllo che entrambi gli utenti esistano nella rete sociale
        if (follower==null || followed==null)
            throw new IllegalArgumentException ("Eccezione: il nome utente non può essere nullo!");
        if (!(social.containsKey(follower) && social.containsKey(followed)))
            throw new NoSuchElementException("Eccezione: il social network non contiene tale utente!");
        //controllo che l'utente non voglia seguire se stesso.
        if (followed.equals(follower))
            throw new NotAllowedActionException("Azione non consentita: gli utenti non possono seguire se stessi!");
        boolean isFollowing = getFollowing(follower).contains(followed);
        if(isFollowing)
            throw new NotAllowedActionException("Azione non consentita: stai già seguendo l'autore!");
        //aggiungo l'utente alla lista dei follower dell'autore a cui ha
        //messo follow.
        social.get(follower).add(followed);
        followers.get(followed).add(follower);
    }
    /*
     * OVERVIEW:
     * Questo metodo permette di rimuovere un autore alla lista degli autori
     * seguiti da un utente
     */
    public void stopFollowing(String follower, String followed)
            throws IllegalArgumentException, NoSuchElementException, NotAllowedActionException{
        if (follower==null || followed==null)
            throw new IllegalArgumentException ("Eccezione: il nome utente non può essere nullo!");
        //controllo che entrambi gli utenti esistano nella rete sociale
        if (!(social.containsKey(follower) && social.containsKey(followed)))
            throw new NoSuchElementException("Eccezione: il social network non contiene tale utente!");
        //controllo che l'utente stia seguendo l'autore che vuole smettere di
        //seguire
        boolean isFollowing = getFollowing(follower).contains(followed);
        if (!isFollowing)
            throw new NotAllowedActionException ("Azione non consentita: gli utenti possono smettere di seguire solo gli autori che sono" +
                    " già seguiti!");
        getFollowing(follower).remove(followed);
        getFollowers(followed).remove(follower);
    }
    /*
     * OVERVIEW:
     * Questo metodo permette di stampare tutti gli utenti della rete sociale
     */
    public void printUsers() {
        for (Map.Entry<String, Set<String>> user : social.entrySet())
            System.out.println(user.getKey());
        System.out.println("----------");
    }
    /*
     * OVERVIEW:
     * Questo metodo permette di stampare tutti i post della rete sociale.
     */
    public void printPosts(){
        for (Post p : posts) {
            System.out.println(p);
        }
        System.out.println("----------");
    }
}
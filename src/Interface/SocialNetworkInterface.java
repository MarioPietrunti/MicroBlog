package Interface;

import Eccezioni.*;
import com.company.*;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public interface SocialNetworkInterface {
    /*REQUIRES: (ps != null) && (~exists p in ps . p == null) &&
     * 			(exists user in social . user == followedAuthor) &&
     * 			(exists p1 in ps && p2 in likesMap . p1.id == p2.idLiked)
     *THROWS: IllegalArgumentException if (ps == null) and NullPointerException if (exists p in ps . p ==null) and PNE(custom)
     *			if (~exists user in social . user == followedAuthor
     *			or ~exists p1 in ps && p2 in likesMap . p1.id == p2.idLiked)
     *EFFECTS: restituisce la rete sociale derivata dalla lista di post
     *MODIFIES: social, followers
     */
    Map<String, Set<String>> guessFollowers(List<Post> ps)
            throws IllegalArgumentException, NullPointerException, PermessoNegatoException,
            NoSuchElementException;

    /*REQUIRES: true
     *THROWS:
     *EFFECTS: restituisce gli utenti piu' influenti della rete sociale
     *MODIFIES:
     */
    List<String> influencers();

    /*REQUIRES: true
     *THROWS:
     *EFFECTS: restituisce l'insieme degli utenti menzionati (inclusi) nella rete sociale
     *MODIFIES:
     */
    Set<String> getMentionedUsers();

    /*REQUIRES: (ps != null) && (~exists a p in ps . p == null) && (exists p.author in posts . p.author == taggedUser)
     *THROWS: IllegalArgumentException if ps == null and NullPointerException if exists a p in ps . p == null
     * || ~exists p.author in posts . p.author == taggedUser
     *EFFECTS: restituisce l'insieme degli utenti menzionati (inclusi) nella lista parametro
     * 			del metodo
     *MODIFIES:
     */
    Set<String> getMentionedUsers(List<Post> ps)
            throws IllegalArgumentException, NullPointerException, PermessoNegatoException, NoSuchElementException;

    /*REQUIRES: username != null && exists user in social . user == username
     *THROWS: IllegalArgumentException if username == null and NSEE if ~exists user in social . user == username
     *EFFECTS: restituisce la lista dei post fatti dall'utente nella rete sociale
     *			il cui nome e' dato dal parametro username
     *MODIFIES:
     */
    List<Post> writtenBy(String username)
            throws IllegalArgumentException, NoSuchElementException;

    /*REQUIRES: username != null && exists user in social . user == username &&
     * 			ps != null && ~exists p in ps . p ==null
     *THROWS: IllegalArgumentException if username != null or ps != null and NPE if exists p in ps . p ==null
     *			and NSEE if ~exists user in social . user == username
     *EFFECTS: restituisce la lista dei post fatti dall'utente nella rete sociale
     *			il cui nome e' dato dal parametro username presenti nella lista parametro
     *			del metodo
     *MODIFIES:
     */
    List<Post> writtenBy(List<Post> ps, String username)
            throws IllegalArgumentException, NullPointerException, NoSuchElementException;

    /*REQUIRES: words != null && ~exists w in words. w == null
     *THROWS: IllegalArgumentException if words == null and NUllPointerException if exists w in words . w == null
     *EFFECTS: restituisce la lista dei post presenti nella rete sociale che includono
     *			almeno una delle parole presenti nella lista delle parole parametro del
     *			metodo
     *MODIFIES:
     */
    List<Post> containing(List<String> words)
            throws IllegalArgumentException, NullPointerException;


    /*REQUIRES: username != null && text != null && ~exists p in posts .
     * 			p.id == id
     *THROWS: IAE if username == null or text == null and DE(custom) if
     *			exists p in posts . p.id == id
     *EFFECTS: aggiunge un post con un suo id, autore e testo alla lista
     *			dei post della rete sociale
     *MODIFIES: posts
     */
    Post createPost(int id, String username, String text)
            throws IllegalArgumentException, DuplicatiException, PermessoNegatoException;

    /*REQUIRES: deleter != null && exists p in posts .
     * 			p.id == id &&
     * 			(exists p in posts . p.author == deleter && p.id == id)
     *THROWS: IAE if deleter == null and PDE if (~exists p in posts . p.author == deleter && p.id == id)
     *			and NSEE if ~exists p in posts . p.id == id
     *EFFECTS: rimuove un post dalla lista dei post della rete sociale
     *MODIFIES: posts
     */
    void deletePost(String deleter, int id)
            throws IllegalArgumentException, PermessoNegatoException, NoSuchElementException;

    /*REQUIRES: username != null && 3<=username<=24 &&
     * 			ForAll c in username . c is in ['0'..'9','A'..'Z','a'..'z','.','_']
     * 			&& ~exists user in social . username==user
     *THROWS: IllegalArgumentException if username == null and TxtFormatException (custom) if username<3 || username>24
     *			|| ~ForAll c in username . c is in ['0'..'9','A'..'Z','a'..'z','.','_']
     *			and DE(custom) if exists user in social . username==user
     *EFFECTS: inserisce un utente nella rete sociale il cui nome e' dato dal parametro
     *			username
     *MODIFIES: social, followers
     */
    void createUser(String username)
            throws IllegalArgumentException, TxtFormatException, DuplicatiException;

    /*REQUIRES: username != null && deleter != null &&
     * 			exist user1, user2 in social . user1 == deleter && user2 == username
     * 			&& deleter == username
     *THROWS: IllegalArgumentException if username == null or deleter == null and NSEE if [~exist user1,
     *			user2 in social . user1 == deleter && user2 == username]
     * 			and PNE if deleter != username
     *EFFECTS: rimuove un utente dalla rete sociale il cui nome e' dato dal parametro username
     *MODIFIES: social, followers
     */
    void deleteUser(String deleter, String username)
           throws IllegalArgumentException, NoSuchElementException,
            PermessoNegatoException;

    /*REQUIRES: username != null && exists user in social . user == username
     *THROWS: IllegalArgumentException if username == null and NSEE if ~exists user in social . user == username
     *EFFECTS: restituisce tutti i follower di un autore
     *MODIFIES:
     */
    Set<String> getFollowers(String username)
            throws IllegalArgumentException, NoSuchElementException;

    /*REQUIRES: username != null && exists user in social . user == username
     *THROWS: IllegalArgumentException if username == null and NSEE if ~exists user in social . user == username
     *EFFECTS: restituisce tutti gli autori che un utente sta seguendo
     *MODIFIES:
     */
    Set<String> getFollowing(String username)
            throws IllegalArgumentException, NoSuchElementException;

    /*REQUIRES: follower != null && followed != null &&
     *			[exist user1, user2 in social . user1 == follower && user2 == followed
     * 			&& follower != followed &&
     * 			[~exists user1 in social . user1== follower
     * 			&& (exists user2 in user1.followed . user2 = followed)]
     *THROWS: IAE if follower == null && followed == null and NSEE if
     *			[~exist user1, user2 in social . user1 == follower && user2 == followed
     * 			and NAE if follower == followed and
     * 			[exists user1 in social . user1==follower
     * 			&& (exists user2 in user1.followed . user2==followed)]
     *EFFECTS: inserisce un autore alla lista degli autori seguiti da un utente
     *MODIFIES: social, followers
     */
    void startFollowing(String follower, String followed)
            throws IllegalArgumentException,NoSuchElementException, NotAllowedActionException;

    /*REQUIRES: follower != null && followed != null &&
     * 			[exist user1, user2 in social . user1 == follower && user2 == followed
     * 			&& [exists user1 in social . user1==follower
     * 				&& (exists user2 in user1.followed . user2==followed)]
     *THROWS: IAE if follower == null && followed == null and NSEE if
     *			[~exist user1, user2 in social . user1 == follower && user2 == followed
     * 			and [~exists user1 in social . user1==follower
     * 					&& (exists user2 in user1.followed . user2==followed)]
     *EFFECTS: rimuove un autore alla lista degli autori seguiti da un utente
     *MODIFIES: social, followers
     */
    void stopFollowing(String follower, String followed)
            throws IllegalArgumentException, NoSuchElementException, NotAllowedActionException;

    /*REQUIRES: true
     *THROWS:
     *EFFECTS: stampa tutti gli utenti della rete sociale
     *MODIFIES:
     */
    void printUsers();

    /*REQUIRES: true
     *THROWS:
     *EFFECTS: stampa tutti i post della rete sociale
     *MODIFIES:
     */
    void printPosts();
}

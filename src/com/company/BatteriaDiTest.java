package com.company;

import Eccezioni.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class BatteriaDiTest {
    public static void main(String[] args){
        SocialNetwork blog= new SocialNetwork();
        SecureSocial secureblog = new SecureSocial();
        List<Post> ps = new ArrayList<>();

        System.out.println("INIZIO DEL TEST\n");

        System.out.println("Test della classe Post:\n");
        //ritardo 10 ms per la leggibilità dell'output
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Test con campo nullo");
        try {
            new Post(1, null, "Buongiorno");
        } catch (IllegalArgumentException | TxtFormatException e) {
            e.printStackTrace();
        }
        try {
            new Post(2, "Mario", null);
        } catch (IllegalArgumentException | TxtFormatException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con username di lunghezza < 1");
        try {
            new Post(3, "Claudio", "");
        } catch (IllegalArgumentException | TxtFormatException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con username di lunghezza > 140");
        try {
            new Post(4, "Claudio", "Ciaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaooooooooooooooooooooooooooooooooooooooooooooooooooo");
        } catch (IllegalArgumentException | TxtFormatException e) {
            e.printStackTrace();
        }
        //ritardo 10 ms
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nTest della classe SocialNetwork\n");
        System.out.println("Test del metodo guessFollowers(ps)");
        System.out.println("Test con il campo nullo");
        try {
            blog.guessFollowers(null);
        } catch (IllegalArgumentException | NullPointerException | NoSuchElementException e ) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con elemento della lista in input nullo");
        ps.add(null);
        try {
            blog.guessFollowers(ps);
        } catch (IllegalArgumentException | NullPointerException | NoSuchElementException e ) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test follow a persona non iscritta");
        try {
            blog.createUser("   Giovanni");
            ps = new ArrayList<>();
            ps.add(blog.createPost(3, "Giovanni", "follow:sdufbeuif"));
            blog.guessFollowers(ps);
        } catch (IllegalArgumentException | NullPointerException | NoSuchElementException
                | PermessoNegatoException | DuplicatiException | TxtFormatException e ) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test per like a post inesistente");
        try {
            ps = new ArrayList<>();
            ps.add(blog.createPost(4, "Giovanni", "like:156456464"));
            blog.guessFollowers(ps);
        } catch (IllegalArgumentException | NullPointerException | NoSuchElementException
                | PermessoNegatoException | DuplicatiException e ) {
            e.printStackTrace();
        }

        //ritardo 10 ms
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nTest del metodo getMentionedUsers(ps):");
        System.out.println("Test con campo nullo");
        try {
            blog.getMentionedUsers(null);
        } catch (IllegalArgumentException | NullPointerException | NoSuchElementException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con elemento della lista in input null");
        try {
            ps.add(null);
            blog.getMentionedUsers(ps);
        } catch (IllegalArgumentException | NullPointerException | NoSuchElementException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test per menzione a utente non registrato");
        try {
            ps = new ArrayList<>();
            ps.add(blog.createPost(5, "Marco", "bene, tu @dfasdfdfa?"));
            blog.getMentionedUsers(ps);
        } catch (IllegalArgumentException | NullPointerException | NoSuchElementException | DuplicatiException | PermessoNegatoException e) {
            e.printStackTrace();
        }
        //ritardo 10 ms
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nTest del metodo writtenBy(username):");
        System.out.println("Test con campo nullo");
        try {
            blog.writtenBy(null);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con utente non registrato");
        try {
            blog.writtenBy("Lorenzo");
        } catch (IllegalArgumentException | NoSuchElementException e) {
            e.printStackTrace();
        }
        //ritardo 10 ms
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nTest del metodo writtenBy(ps, username):");
        System.out.println("Test con campo nullo");
        try {
            blog.writtenBy(null, "Luca");
        } catch (IllegalArgumentException | NullPointerException | NoSuchElementException e) {
            e.printStackTrace();
        }
        try {
            ps.add(blog.createPost(23, "Marco", "follow:Luca"));
            blog.writtenBy(ps, null);
        } catch (IllegalArgumentException | NullPointerException | NoSuchElementException | DuplicatiException | PermessoNegatoException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con elemento della lista in input null");
        try {
            blog.createUser("Alessia");
            ps.add(null);
            blog.writtenBy(ps, "Alessia");
        } catch (IllegalArgumentException | NullPointerException | NoSuchElementException
                | DuplicatiException | TxtFormatException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con utente non registrato");
        try {
            ps.add(blog.createPost(343, "Michele", "follow:Luca"));
            blog.writtenBy(ps, "Francesco");
        } catch (IllegalArgumentException | NullPointerException | NoSuchElementException
                | DuplicatiException | PermessoNegatoException e) {
            e.printStackTrace();
        }
        //ritardo 10ms
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nTest del metodo containing(words):");
        System.out.println("Test con campo nullo");
        try {
            blog.containing(null);
        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con elemento della lista in input null");
        try {
            List<String> ws = new ArrayList<>();
            ws.add(null);
            blog.containing(ws);
        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
        }
        //ritardo 10ms
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nTest del metodo createPost():");
        System.out.println("Test con campo nullo");
        try {
            blog.createPost(45, null, "buonasera");
        } catch(IllegalArgumentException | DuplicatiException |PermessoNegatoException e) {
            e.printStackTrace();
        }
        try {
            blog.createPost(34, "Miriam", null);
        } catch(IllegalArgumentException | DuplicatiException | PermessoNegatoException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con ID gia' utilizzato");
        try {
            blog.createUser("Marco");
            blog.createUser("Angela");
            blog.createPost(0, "Marco", "testo");
            blog.createPost(0, "Angela", "testo");
        } catch(IllegalArgumentException | DuplicatiException |PermessoNegatoException | TxtFormatException e) {
            e.printStackTrace();
        }
        //ritardo 10ms
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nTest del metodo deletePost():");
        System.out.println("Test con campo nullo");
        try {
            blog.deletePost(null, 0);
        } catch(IllegalArgumentException | NoSuchElementException | PermessoNegatoException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test di permesso negato per un utente non registrato");
        try {
            blog.deletePost("Giovanni", 0);
        } catch(IllegalArgumentException | NoSuchElementException | PermessoNegatoException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test di permesso negato per un utente che cerca di cancellare il post di un altro utente");
        try {
            blog.createUser("Nicola");
            blog.createUser("Giorgio");
            blog.createPost(88, "Giorgio", "ciao");
            blog.deletePost("Nicola", 88);
        } catch(IllegalArgumentException | NoSuchElementException |
                PermessoNegatoException | TxtFormatException | DuplicatiException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test nel caso in cui l'utente provi a cancellare un post inesistente");
        try {
            blog.deletePost("Baldo", 5464);
        } catch(IllegalArgumentException | NoSuchElementException | PermessoNegatoException e) {
            e.printStackTrace();
        }
        //ritardo 10ms
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nTestdel metodo createUser():");
        System.out.println("Test con campo nullo");
        try {
            blog.createUser(null);
        } catch(IllegalArgumentException | NoSuchElementException | TxtFormatException | DuplicatiException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con caratteri proibiti");
        try {
            blog.createUser("?????");
        } catch(IllegalArgumentException | NoSuchElementException | TxtFormatException | DuplicatiException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con lunghezza username < 4");
        try {
            blog.createUser("ci");
        } catch(IllegalArgumentException | NoSuchElementException | TxtFormatException | DuplicatiException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con lunghezza username > 15");
        try {
            blog.createUser("MaarioROssi19765");
        } catch(IllegalArgumentException | NoSuchElementException | TxtFormatException | DuplicatiException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con utente gia' iscritto");
        try {
            blog.createUser("Paolo");
            blog.createUser("Paolo");
        } catch(IllegalArgumentException | NoSuchElementException | TxtFormatException | DuplicatiException e) {
            e.printStackTrace();
        }
        //ritardo di 10ms per rendere l'output piu' leggibile
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nTest del metodo deleteUser():");
        System.out.println("Test con campo nullo");
        try {
            blog.deleteUser(null, "Emanuele");
        } catch(IllegalArgumentException | NoSuchElementException | PermessoNegatoException e) {
            e.printStackTrace();
        }
        try {
            blog.deleteUser("Emanuele", null);
        } catch(IllegalArgumentException | NoSuchElementException | PermessoNegatoException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con utente non registrato");
        try {
            blog.createUser("Francesca");
            blog.deleteUser("Francesca", "Emanuele");
        } catch(IllegalArgumentException | NoSuchElementException | PermessoNegatoException |
                TxtFormatException | DuplicatiException e) {
            e.printStackTrace();
        }
        try {
            blog.createUser("Emanuele");
            blog.deleteUser("Francesca", "Emanuele");
        } catch(IllegalArgumentException | NoSuchElementException | PermessoNegatoException |
                TxtFormatException | DuplicatiException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con utente che prova a eliminare l'account di un altro");
        try {
            blog.deleteUser("Francesca", "Emanuele");
        } catch(IllegalArgumentException | NoSuchElementException | PermessoNegatoException e) {
            e.printStackTrace();
        }
        //ritardo 10ms
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nTest del metodo getFollowers(username):");
        System.out.println("Test con campo nullo");
        try {
            blog.getFollowers(null);
        } catch(IllegalArgumentException | NoSuchElementException e ) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con utente non registrato nella rete sociale");
        try {
            blog.getFollowers("Maurizio");
        } catch(IllegalArgumentException | NoSuchElementException e ) {
            e.printStackTrace();
        }
        //ritardo 10ms
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nTest del metodo getFollowing(username):");
        System.out.println("Test con campo nullo");
        try {
            blog.getFollowing(null);
        } catch(IllegalArgumentException | NoSuchElementException e ) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con utente non registrato nella rete sociale");
        try {
            blog.getFollowing("Raffaele");
        } catch(IllegalArgumentException | NoSuchElementException e ) {
            e.printStackTrace();
        }
        //ritardo 10ms
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nTest della classe SecureSocial:");
        System.out.println("\nTest del metodo segnalaPost(id, segnalatore):");
        System.out.println("Test con campo null");
        try{
            secureblog.segnalaPost(0, null);
        }catch (IllegalArgumentException | PermessoNegatoException|NoSuchElementException| NotAllowedActionException e){
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con utente non registrato che prova a segnalare un post");
        try {
            secureblog.segnalaPost(0,"Michele");
        }catch (IllegalArgumentException | PermessoNegatoException
                | NoSuchElementException| NotAllowedActionException e){
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con utente che prova a segnalare un suo post");
        try {
            secureblog.createUser("Mimmo");
            secureblog.createPost(0,"Mimmo","Ciao a tutti amici");
            secureblog.segnalaPost(0,"Mimmo");
        }catch (IllegalArgumentException | PermessoNegatoException
                | NoSuchElementException| NotAllowedActionException | TxtFormatException | DuplicatiException e){
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con utente che prova a segnalare due volte un post");
        try {
            secureblog.createUser("Mario");
            secureblog.createUser("Gianmarco");
            secureblog.createPost(1,"Mario","Forza Inter *****");
            secureblog.segnalaPost(1, "Gianmarco");
            secureblog.segnalaPost(1, "Gianmarco");
        } catch(IllegalArgumentException | NoSuchElementException |
                PermessoNegatoException | NotAllowedActionException |TxtFormatException | DuplicatiException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test con utente che prova a segnalare un post inesistente");
        try {
            secureblog.createUser("Davide");
            secureblog.segnalaPost(66,"Davide");
        }catch (IllegalArgumentException | PermessoNegatoException | TxtFormatException
                | NotAllowedActionException | DuplicatiException | NoSuchElementException e){
            e.printStackTrace();
        }
        //ritardo 10ms
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nTest del metodo valutaSegnalazione(Post p):");
        System.out.println("Test con campo null");
        try{
            secureblog.valutaSegnalazione(null);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        //ritardo 10ms
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nFINE TEST ECCEZIONI");
    }
}
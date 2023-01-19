package com.company;

import Eccezioni.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Main {

    public static void main(String[] args) throws TxtFormatException, NotAllowedActionException,
            PermessoNegatoException, IllegalArgumentException, NullPointerException, NoSuchElementException, DuplicatiException {

        System.out.println("INIZIO DEL TEST\n");

        SecureSocial blog = new SecureSocial();
        //Lista di post
        List<Post> ps = new ArrayList<>();
        //Lista di parole
        List<String> words= new ArrayList<>();

        //creo gli user
        blog.createUser("Giuseppe");
        blog.createUser("Mario");
        blog.createUser("Loris");
        blog.createUser("Lorenzo");
        blog.createUser("Mimmo");
        blog.createUser("Emanuele");
        blog.createUser("Davide");
        blog.createUser("Viviana");
        blog.createUser("Francesca");

        //creo i post
        ps.add(blog.createPost(1,"Loris", "Ciao a tutti!"));
        ps.add(blog.createPost(2,"Mario","Like:1"));
        ps.add(blog.createPost(3,"Giuseppe", "Ciao @Loris"));
        ps.add(blog.createPost(4,"Francesca", "Oggi il mio gatto Pallina compie 1 anno"));
        ps.add(blog.createPost(5,"Giuseppe","Follow:Loris"));
        ps.add(blog.createPost(6,"Mario","Follow:Giuseppe"));
        ps.add(blog.createPost(7,"Lorenzo","Ciao a tutti, piacere sono @Lorenzo?"));
        ps.add(blog.createPost(8,"Loris","Like:3"));
        ps.add(blog.createPost(9,"Mario","Forza inter!"));
        ps.add(blog.createPost(10,"Loris","Follow:Lorenzo"));
        ps.add(blog.createPost(11,"Davide","Oggi è proprio una bella giornata, non sei d'accordo @Viviana?"));
        ps.add(blog.createPost(12,"Viviana", "Like:11"));
        ps.add(blog.createPost(13,"Mimmo","*****"));
        ps.add(blog.createPost(14,"Davide","Follow:Francesca"));


        //Test dei metodi

        System.out.println("Test di guessFollowers(ps)");
        System.out.println(blog.guessFollowers(ps));

        System.out.println("\nTest di influencers()");
        System.out.println(blog.influencers());

        System.out.println("\nTest di getMentionedUsers()");
        System.out.println(blog.getMentionedUsers());

        System.out.println("\nTest di getMentionedUsers(ps)");
        System.out.println(blog.getMentionedUsers(ps));

        System.out.println("\nTest di writtenBy(username)");
        System.out.println(blog.writtenBy("Loris"));

        System.out.println("\nTest di writtenBy(ps,username)");
        System.out.println(blog.writtenBy(ps,"Mario"));

        System.out.println("\nTest di containig(words) con lista vuota");
        System.out.println(blog.containing(words));

       //aggiungo le parole alla lista words
        words.add("Inter");
        words.add("Ciao");
        System.out.println("\nTest di containig(words)");
        System.out.println(blog.containing(words));

        //Test dei metodi aggiuntivi
        System.out.println("\nTest di deletePost(deleter, id)");
        blog.deletePost("Mario", 6);
        blog.printPosts();


        System.out.println("\nTest di stopFollowing(follower, followed)");
        blog.stopFollowing("Loris","Giuseppe");
        //verifico che Loris non segua più Giuseppe
        System.out.println(blog.getFollowing("Loris"));


        System.out.println("\nTest di deleteUser(deleter, username)");
        blog.deleteUser("Emanuele","Emanuele");
        System.out.println("Lista degli untenti aggiornata");
        blog.printUsers();


        System.out.println("\nTest di segnalaPost(id, segnalatore)");
        blog.segnalaPost(13,"Lorenzo");
        System.out.println("Post segnalati");
        blog.printSegnalati();
        blog.printPosts();
        //se il post viene segnalato una seconda volta viene rimosso dalla lista dei post
        blog.segnalaPost(13,"Mario");
        blog.printPosts();

        System.out.println("\nTest di valutaSegnalazione(Post p)");
        //creo un post e valuto se può essere segnalato o meno
        Post p1= new Post(15,"Emanuele","*****");
        System.out.println(blog.valutaSegnalazione(p1));
        Post p2= new Post(16,"Emanuele","Forza Milan");
        System.out.println(blog.valutaSegnalazione(p2));
    }
}

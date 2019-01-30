package com.elodie.jeux.exceptions;

public class ExceptionNaN extends Exception {
    public ExceptionNaN()
    {
        System.out.println( "Saisie erronée, votre choix n'est pas un nombre" );
    }
}

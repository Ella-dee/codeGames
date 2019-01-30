package com.elodie.jeux.exceptions;

public class ExceptionNaL extends Exception{
    public ExceptionNaL()
    {
        System.out.println( "Saisie erronée, votre choix n'est pas une lettre." );
    }
}

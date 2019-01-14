package com.elodie.jeux.Exceptions;

public class ExceptionNaL extends Exception{
    public ExceptionNaL()
    {
        System.out.println( "Saisie erronée, votre choix n'est pas une lettre." );
    }
}

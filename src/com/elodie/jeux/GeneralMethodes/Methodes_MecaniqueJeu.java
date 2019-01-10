package com.elodie.jeux.GeneralMethodes;

public class Methodes_MecaniqueJeu {

    public static int[] MakeSecretCode(){
        final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
        final int first = (int)(Math.random() * 10);
        final int second = (int)(Math.random() * 10);
        final int third = (int)(Math.random() * 10);
        int fourth = (int)(Math.random() * 10);
        final int[] code = {first, second, third, fourth};
        return code;
    }
}

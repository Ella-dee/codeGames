package com.elodie.jeux.Modes;

public abstract class Mode {
    String name;

    Mode(String name){
        this.name = name;
    }
    public String toString(){
        return "Mode " + name;
    }

   // public void StartGame(String userInput, String[] nbr, int[] secretCode, String reponseToString, ArrayList reponse){}
}

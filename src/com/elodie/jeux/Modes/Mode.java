package com.elodie.jeux.Modes;

import com.elodie.jeux.Exceptions.ExceptionNaN;

import java.util.ArrayList;

import static com.elodie.jeux.GeneralMethodes.Methodes.checkOccurencesFromListInArray;
import static com.elodie.jeux.GeneralMethodes.Methodes.createArrayFromInput;

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

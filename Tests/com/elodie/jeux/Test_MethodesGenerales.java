package com.elodie.jeux;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static com.elodie.jeux.utilities.utils.*;
import static org.junit.jupiter.api.Assertions.*;

class Test_MethodesGenerales {

    @Test
    void WhenGiven_String_PutsEachElementOfItIn_ArrayList() {
        String testInput = "abcd";
        ArrayList testListe = createArrayListeFromInput(testInput);

        String result = myTrimString(testListe.toString());
        String expected = testInput;

        assertEquals(expected, result);
    }

    @Test
    void WhenGiven_ArrayListAndArrayOfString_ValidateIfEachEntryOfListInArray(){
        boolean expected = true;
        ArrayList testListe = new ArrayList();
        testListe.add( "a" );
        testListe.add( "b" );
        testListe.add( "c" );
        testListe.add( "d" );
        String [] testArraofString = {"a", "b", "c", "d"};
        boolean result = checkOccurencesFromListInArray(testListe, testArraofString);
        assertEquals(expected, result);
    }
    @Test
    void WhenGiven_ArrayListAndArrayOfString_ReturnsFalseIfEachEntryOfListNOTInArray(){
        boolean expected = false;
        int count = 0;
        ArrayList testListe = new ArrayList();
        testListe.add( "a" );
        testListe.add( "b" );
        testListe.add( "e" );
        testListe.add( "f" );
        String [] testArraofString = {"a", "b", "c", "d"};
        boolean result = checkOccurencesFromListInArray(testListe, testArraofString);
        assertEquals(expected, result);
    }
    @Test
    void WhenGiven_String_TrimsGivenCharacters(){
        String testString = "[ ab c]d";
        String expected = "abcd";
        String result =  myTrimString(testString);
        assertEquals(expected, result);
    }
}
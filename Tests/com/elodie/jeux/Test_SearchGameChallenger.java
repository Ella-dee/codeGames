package com.elodie.jeux;

import com.elodie.jeux.utilities.utilsGameMecanics;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class Test_SearchGameChallenger {

    @Test
    void Given_SecretCodeIsFound_DisplayEqualsOperators() {
        String equal = "=";
        String minus = "-";
        String plus = "+";

        ArrayList liste = new ArrayList();
        ArrayList output = new ArrayList();
        int[] secret = {1,2,3,4};
        String input = "1234";

        liste.add(1);
        liste.add(2);
        liste.add(3);
        liste.add(4);

        String expected = "====";
        String result = utilsGameMecanics.tryOutCheckSearchGame(liste, secret, input);
        assertEquals( expected, result );
    }
    @Test
    void Given_SecretCodeIsNotFound_DisplayHelpComparisonOperators() {
        String equal = "=";
        String minus = "-";
        String plus = "+";

        ArrayList liste = new ArrayList();
        ArrayList output = new ArrayList();
        int[] secret = {1,2,3,4};
        String input = "1532";

        liste.add(1);
        liste.add(5);
        liste.add(3);
        liste.add(2);

        String expected = "=-=+";
        String result = utilsGameMecanics.tryOutCheckSearchGame(liste, secret, input);
        assertEquals( expected, result );
    }

}
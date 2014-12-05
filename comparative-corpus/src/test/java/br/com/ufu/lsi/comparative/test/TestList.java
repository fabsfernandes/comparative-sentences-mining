
package br.com.ufu.lsi.comparative.test;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestList {

    //@Test
    public void testList() {
        List< String > places = Arrays.asList( "Buenos Aires", "Cordoba", "La Plata", "Bariloche" );
        List< String > subPlaces = Arrays.asList( "La Plata", "Buenos Aires" );

        assertTrue( places.containsAll( subPlaces ) );
    }

    @Test
    public void testSatisfies() {
        
        List< Integer > sentence = Arrays.asList( 1, 2, 3, 4, 5, 6, 7, 8, 9, 4, 4 );
        List< Integer > rule = Arrays.asList( 1, 5, 4, 4, 9 );
        
        int i = 0;
        int asserts = 0;
        for ( Integer tag : rule ) {

            for ( ; i < sentence.size(); i++ ) {
                Integer sentenceTag = sentence.get( i );
                if ( tag.equals( sentenceTag ) ) {
                    asserts++ ;
                    break;
                }
            }
        }

        assertTrue( rule.size() == asserts );

    }
}

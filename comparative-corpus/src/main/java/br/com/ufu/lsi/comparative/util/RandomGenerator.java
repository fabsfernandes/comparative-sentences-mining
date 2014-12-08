
package br.com.ufu.lsi.comparative.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RandomGenerator {
    
    public static Random random;
    
    private static long seed = 10;
    
    static {
        random = new Random( seed );
    }

    public static int randInt( int min, int max ) {

        int randomNum = random.nextInt( ( max - min ) + 1 ) + min;

        return randomNum;
    }
    
    public static String randValue( HashMap<String,String> values ){
        int max = values.size();
        int rand = randInt( 0, max-1 );
        
        Collection<String> listValues = values.values();
        List<String> list = new ArrayList<String>( listValues );
        String gene = list.get( rand );
        
        int active = randInt( 0, 1 );
        
        return active + gene;
        
    }
    
    public static String randKey( HashMap<String,String> values ){
        int max = values.size();
        int rand = randInt( 0, max-1 );
        
        Collection<String> listValues = values.keySet();
        List<String> list = new ArrayList<String>( listValues );
        String gene = list.get( rand );
        
        int active = randInt( 0, 1 );
        
        return active + gene;
        
    }
    
    public static List<Integer> randListValues( int min, int max, int totalNumbers ) {
        
        List<Integer> randValues = new ArrayList<Integer>();
        for( int i = 0; i < totalNumbers; i++ ) {
            int value = randInt( min, max );
            if( !randValues.contains( value ) ) {
                randValues.add( value );
            } else {
                i--;
            }
        }
        return randValues;
    }
}

package br.com.ufu.lsi.comparative.util;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class SentenceUtil {
    
    public static String [] sentenceTokenize( String str ) {
        
        List<String> words = new ArrayList<String>();
        BreakIterator breakIterator = BreakIterator.getWordInstance();
        breakIterator.setText( str );
        int lastIndex = breakIterator.first();
        while (BreakIterator.DONE != lastIndex) {
            int firstIndex = lastIndex;
            lastIndex = breakIterator.next();
            if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(str.charAt(firstIndex))) {
                words.add(str.substring(firstIndex, lastIndex));
            }
        }
        return words.toArray(new String[words.size()]);
    }

}

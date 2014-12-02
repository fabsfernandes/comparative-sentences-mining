
package br.com.ufu.lsi.test;

import java.io.StringReader;

import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.junit.Test;

import br.com.ufu.lsi.util.SentenceUtil;

public class TestSentenceUtil {

    //@Test
    public void testTokenizer() {
        String[] tokens = SentenceUtil.sentenceTokenize( ", ,isso eh um teste" );
        for ( String str : tokens ) {
            System.out.println( str );
        }
    }

    @Test
    public void stopWords() throws Exception {

        StandardAnalyzer ana = new StandardAnalyzer( Version.LUCENE_30 );
        TokenStream tokenStream = new StandardTokenizer( Version.LUCENE_36, new StringReader( " this is a test sentence..." ) );
        StringBuilder sb = new StringBuilder();
        tokenStream = new StopFilter( Version.LUCENE_36, tokenStream, ana.STOP_WORDS_SET );
        CharTermAttribute token = tokenStream.getAttribute( CharTermAttribute.class );
        while ( tokenStream.incrementToken() ) {
            if ( sb.length() > 0 ) {
                sb.append( " " );
            }
            sb.append( token.toString() );
        }
        System.out.println( sb );

    }

}

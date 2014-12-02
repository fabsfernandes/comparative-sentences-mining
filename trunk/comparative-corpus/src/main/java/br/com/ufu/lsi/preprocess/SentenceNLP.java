
package br.com.ufu.lsi.preprocess;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import br.com.ufu.lsi.model.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.tagger.maxent.TestSentence;

public class SentenceNLP {

    public void removeStopwords( List< Sentence > sentences ) throws Exception {

        for ( Sentence sentence : sentences ) {

            StandardAnalyzer ana = new StandardAnalyzer( Version.LUCENE_30 );
            TokenStream tokenStream = new StandardTokenizer( Version.LUCENE_36, new StringReader( sentence.getText() ) );
            StringBuilder sb = new StringBuilder();
            tokenStream = new StopFilter( Version.LUCENE_36, tokenStream, ana.STOP_WORDS_SET );
            CharTermAttribute token = tokenStream.getAttribute( CharTermAttribute.class );

            while ( tokenStream.incrementToken() ) {
                if ( sb.length() > 0 ) {
                    sb.append( " " );
                }
                sb.append( token.toString() );
            }

            sentence.setText( sb.toString() );
        }
    }

    public void tagSentences( List< Sentence > sentences ) {

        MaxentTagger tagger = new MaxentTagger( "english-bidirectional-distsim.tagger" );

        for ( Sentence sentence : sentences ) {
            String text = sentence.getText();
            List< Word > sent = edu.stanford.nlp.ling.Sentence.toUntaggedList( Arrays.asList( text.split( "\\s+" ) ) );
            TestSentence testSentence = new TestSentence( tagger );
            List< TaggedWord > taggedWords = testSentence.tagSentence( sent, false );
            sentence.setTaggedWords( taggedWords );
        }
    }
}

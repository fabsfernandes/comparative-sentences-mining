
package br.com.ufu.lsi.engine;

import java.util.List;

import br.com.ufu.lsi.model.Sentence;
import br.com.ufu.lsi.preprocess.SentenceNLP;

public class ComparativeEngine {

    public static void main( String... args ) throws Exception {
        
        Long init = System.currentTimeMillis();

        ComparativeEngine comparativeEngine = new ComparativeEngine();
        
        List< Sentence > sentences = comparativeEngine.loadSentences();
        //comparativeEngine.removeStopWords( sentences );
        comparativeEngine.postagSentences( sentences );
        List< Sentence > pivotedSentences = comparativeEngine.pivotSentences( sentences );
        
        System.out.println( "=== FINAL SENTENCES === " );
        int i = 0;
        for ( Sentence s : pivotedSentences )
            System.out.println( ( i++ ) + "\t" + s );
        
        System.out.println( "STATS" );
        System.out.println( "Sentences length = " + sentences.size() );
        System.out.println( "Pivot sent length = " + pivotedSentences.size() );
        System.out.println( "Time = " + (System.currentTimeMillis() - init) + "ms");
    }

    /**
     * Load sentences from file
     * 
     * @return
     * @throws Exception
     */
    public List< Sentence > loadSentences() throws Exception {
        SentenceHandler loader = new SentenceHandler();
        List< Sentence > sentences = loader.load();

        return sentences;
    }

    /**
     * Remove stop words from sentences
     * 
     * @param sentences
     * @throws Exception
     */
    public void removeStopWords( List< Sentence > sentences ) throws Exception {

        SentenceNLP stopwordSentence = new SentenceNLP();
        stopwordSentence.removeStopwords( sentences );

    }

    /**
     * generate sentences from pivo
     * 
     * @throws Exception
     */
    public List< Sentence > pivotSentences( List< Sentence > sentences ) throws Exception {
        SentenceHandler generator = new SentenceHandler();
        List< Sentence > pivotedSentences = generator.generatePivot( sentences );

        return pivotedSentences;
    }

    /**
     * @param sentences
     * @return
     */
    public void postagSentences( List< Sentence > sentences ) {

        SentenceNLP sentenceNLP = new SentenceNLP();
        sentenceNLP.tagSentences( sentences );
    }

    // generate sequences
}


package br.com.ufu.lsi.engine;

import java.util.Arrays;
import java.util.List;

import br.com.ufu.lsi.csr.CSRFormatter;
import br.com.ufu.lsi.csr.ClassSequentialRule;
import br.com.ufu.lsi.model.Sentence;
import br.com.ufu.lsi.sentence.SentenceHandler;
import br.com.ufu.lsi.sentence.SentenceNLP;

public class SequenceBuilder {
    
    private static final List<String> COMPARISON_TAGS = Arrays.asList("JJR", "RBR", "JJS", "RBS");

    public static void main( String... args ) throws Exception {
        
        Long init = System.currentTimeMillis();

        SequenceBuilder comparativeEngine = new SequenceBuilder();
        
        List< Sentence > sentences = comparativeEngine.loadSentences();
        //comparativeEngine.removeStopWords( sentences );
        comparativeEngine.postagSentences( sentences );
        List< Sentence > pivotedSentences = comparativeEngine.pivotSentences( sentences );
        comparativeEngine.generateSequences( pivotedSentences );
        
        printResults( sentences, pivotedSentences, init );
        
        
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
        List< Sentence > pivotedSentences = generator.generatePivot( sentences, COMPARISON_TAGS );

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
    public void generateSequences( List<Sentence> sentences ) throws Exception {
        
        CSRFormatter formatter = new CSRFormatter();
        List<ClassSequentialRule> rules = formatter.convertSentenceToCSR( sentences );
        formatter.convertCSRToPrefixSpanFormat( rules, COMPARISON_TAGS );
        
        
    }
    
    
    /**
     * Just print for debug purposes
     * 
     * @param sentences
     * @param pivotedSentences
     * @param init
     */
    public static void printResults( List<Sentence> sentences, List<Sentence> pivotedSentences, long init ) {
        
        System.out.println( "=== FINAL SENTENCES === " );
        int i = 0;
        for ( Sentence s : pivotedSentences )
            System.out.println( ( i++ ) + "\t" + s );
        
        System.out.println( "STATS" );
        System.out.println( "Sentences length = " + sentences.size() );
        System.out.println( "Pivot sent length = " + pivotedSentences.size() );
        System.out.println( "Time = " + (System.currentTimeMillis() - init) + "ms");
    }
}

package br.com.ufu.lsi.comparative.main;

import java.util.List;

import br.com.ufu.lsi.comparative.ga.engineop.GARunnerOp;
import br.com.ufu.lsi.comparative.ga.model.Stats;
import br.com.ufu.lsi.comparative.model.Sentence;
import br.com.ufu.lsi.comparative.sentence.SentenceHandler;
import br.com.ufu.lsi.comparative.sentence.SentenceNLP;
import br.com.ufu.lsi.comparative.util.PropertiesUtil;

public class Main {
    
    private static final List< String > COMPARISON_TAGS = PropertiesUtil.getListProperty( "COMPARISON_TAGS" );
    
    public static void main( String... args ) throws Exception {

        Long init = System.currentTimeMillis();

        Main main = new Main();

        List<Sentence> pivotedSentences = main.buildSentences();
        
        // csr approach
        //SequenceBuilder sequenceBuilder = new SequenceBuilder();
        //sequenceBuilder.buildSequences( pivotedSentences );
        
        // csr approach part 2
        // run via csrbuilder

        //printResults( pivotedSentences, init  );
        
        // ga approach
        //GARunner runner = new GARunner();
        //List<Stats> statistics = runner.runGeneticEngine( pivotedSentences );
        
        // ga op approach
        GARunnerOp runnerOp = new GARunnerOp();
        List<Stats> statistics = runnerOp.runGeneticEngine( pivotedSentences );
        
        printStatsResults( statistics, init );
        
    }
    
    public List<Sentence> buildSentences() throws Exception {
        
        List< Sentence > sentences = loadSentences();
        //removeStopWords( sentences );
        postagSentences( sentences );
        List< Sentence > pivotedSentences = pivotSentences( sentences );
        
        return pivotedSentences;
        
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
     * pos tag sentences - heavy process 
     * 
     * @param sentences
     * @return
     */
    public void postagSentences( List< Sentence > sentences ) {

        SentenceNLP sentenceNLP = new SentenceNLP();
        sentenceNLP.tagSentences( sentences );
    }

    /**
     * Just print for test purposes
     */
    public static void printResults( List< Sentence > pivotedSentences,
            long init ) {

        System.out.println( "=== FINAL SENTENCES === " );
        int i = 0;
        for ( Sentence s : pivotedSentences )
            System.out.println( ( i++ ) + "\t" + s );

        System.out.println( "STATS" );
        //System.out.println( "Database size = " + databaseSize );
        System.out.println( "Pivoted sentences = " + pivotedSentences.size() );
        System.out.println( "Time = " + ( System.currentTimeMillis() - init ) + "ms" );
    }
    
    
    /**
     * Just print for test purposes    
     */
    public static void printStatsResults( Stats statistics, long init ) {
        System.out.println( "\n===== GA RESULTS ==== ");
        System.out.println( statistics );
        System.out.println( "Time = " + ( System.currentTimeMillis() - init ) + "ms" );
    }
    
    /**
     * Just print for test purposes    
     */
    public static void printStatsResults( List<Stats> statistics, long init ) {
        System.out.println( "\n===== GA RESULTS ==== ");
        Double precision = 0.0;
        Double recall = 0.0;
        Double fScore = 0.0;
        Double accuracy = 0.0;
        
        for( Stats stats : statistics ) {
            
            precision += stats.getPrecision();
            recall += stats.getRecall();
            fScore += stats.getfScore();
            accuracy += stats.getAccuracy();
        }
        
        System.out.println("Precision = " + precision / statistics.size() );
        System.out.println("Recall = " + recall / statistics.size() );
        System.out.println("FScore = " + fScore / statistics.size() );
        System.out.println("Accuracy = " + accuracy / statistics.size() );
        System.out.println( "Time = " + ( System.currentTimeMillis() - init ) + "ms" );
    }
}

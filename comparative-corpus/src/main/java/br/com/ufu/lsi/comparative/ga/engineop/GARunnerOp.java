
package br.com.ufu.lsi.comparative.ga.engineop;

import java.util.List;

import br.com.ufu.lsi.comparative.ga.model.Chromossome;
import br.com.ufu.lsi.comparative.ga.model.Stats;
import br.com.ufu.lsi.comparative.model.Sentence;

public class GARunnerOp {

    private static final Double TRAIN_PROPORTION = 0.8;

    public List<Stats> runGeneticEngine( List< Sentence > sentences ) {

        GeneticEngineOp engineOp = new GeneticEngineOp();

        List< Sentence > trainSentences = getTrainSentences( sentences );
        List< Chromossome > population = engineOp.generateInitialPopulation( trainSentences );
        engineOp.evolvePopulation( population, trainSentences );

        printPopulation( population );
        printDatabaseStats( trainSentences, sentences );
        
        List<Stats> stats = testResult( sentences, population, trainSentences.size(), engineOp );

        return stats;
    }

    public List<Stats> testResult( List< Sentence > sentences, List< Chromossome > population,
            int indexInit, GeneticEngineOp engine ) {

        List< Sentence > testSentences = sentences.subList( indexInit, sentences.size() );

        return engine.statistics( testSentences, population );
    }


    public List< Sentence > getTrainSentences( List< Sentence > sentences ) {

        Double quantity = Math.ceil( TRAIN_PROPORTION * sentences.size() );
        int trainSize = quantity.intValue();

        return sentences.subList( 0, trainSize );

    }

    public static void printPopulation( List< Chromossome > population ) {
        int i = 1;
        System.out.println( "\n===== FINAL POPULATION ======" );
        for ( Chromossome chrom : population ) {
            System.out.println( ( i++ ) + ".\t" + chrom );
        }
    }
    
    public static void printDatabaseStats( List<Sentence> trainSentences, List<Sentence> sentences ) {
        
        System.out.println( "\n====== DATABASE STATISTICS ====== " );
        System.out.println( "Pivoted sentences = " + sentences.size() );
        System.out.println( "Train size = " + trainSentences.size() );
        System.out.println( "Test size = " + sentences.subList( trainSentences.size(), sentences.size() ).size() );
    }
    
   

}


package br.com.ufu.lsi.comparative.ga.engine;

import java.util.ArrayList;
import java.util.List;

import br.com.ufu.lsi.comparative.ga.model.Chromossome;
import br.com.ufu.lsi.comparative.ga.model.Gene;
import br.com.ufu.lsi.comparative.ga.model.Stats;
import br.com.ufu.lsi.comparative.model.Sentence;
import br.com.ufu.lsi.comparative.util.PropertiesUtil;

public class GARunner {

    private static final String NOT_COMPARATIVE_CLASS = PropertiesUtil.getProperty( "NOT_COMPARATIVE_CLASS" );
    
    private static final Double TRAIN_PROPORTION = 0.8;

    public void runGeneticEngine( List<Sentence> sentences ) {
        
        GeneticEngine engine = new GeneticEngine();

        List<Sentence> trainSentences = getTrainSentences( sentences );
        System.out.println(trainSentences.size());
        List< Chromossome > population = engine.generateInitialPopulation( trainSentences );
        engine.evolvePopulation( population, trainSentences );

        printPopulation( population );
        
        Stats stats = testResult( sentences, population, trainSentences.size() );
        
        System.out.println( stats.getAccuracy() );
        
    }
    
    public Stats testResult( List<Sentence> sentences, List<Chromossome> population, int indexInit ) {
        
        List<Sentence> testSentences = sentences.subList( indexInit, sentences.size() );
        System.out.println( testSentences.size() );
        
        Stats stats = new Stats();
        
        stats.setAccuracy( accuracy( testSentences, population) );
        
        return stats;
    }
    
    
    public Double accuracy( List< Sentence > testSentences, List<Chromossome> population ) {
        
        Double correct = 0.0;
        for( Sentence sentence : testSentences ) {
            boolean defaultValue = true;
            for( Chromossome chromossome : population ) {
                if( satisfies( chromossome, sentence) ) {
                    correct++;
                    defaultValue = false;
                    break;
                }
            }
            /*if( defaultValue ) {
                if( sentence.getCategory().equals( NOT_COMPARATIVE_CLASS ) )
                    correct++;
            }*/
        }
        System.out.println( "Corrects = " + correct );
        return correct / testSentences.size();
    }
    
    public boolean satisfies( Chromossome chromossome, Sentence sentence ) {
        
        List< String > tags = new ArrayList< String >();
        for ( Gene gene : chromossome.getGenes() ) {
            String value = gene.getValue();
            if ( value.charAt( 0 ) == '1' ) {
                tags.add( value.substring( 1 ) );
            }
        }

        int i = 0;
        int asserts = 0;
        for ( String tag : tags ) {

            for ( ; i < sentence.getTaggedWords().size(); i++ ) {
                String sentenceTag = sentence.getTaggedWords().get( i ).tag();
                if ( tag.equals( sentenceTag ) ) {
                    asserts++ ;
                    break;
                }
            }
        }

        if( asserts == tags.size() ) {
            
            String geneClass = chromossome.getGenes()[ chromossome.getGenes().length - 1 ].getValue();

            return sentence.getCategory().equals( geneClass );
        }
        
        return false;
    }
    
    public List<Sentence> getTrainSentences( List<Sentence> sentences ) {
        
        Double quantity = Math.ceil( TRAIN_PROPORTION * sentences.size() );
        int trainSize = quantity.intValue();
        
        return sentences.subList( 0, trainSize );
        
    }
    

    public static void printPopulation( List< Chromossome > population ) {
        int i = 1;
        System.out.println("FINAL POPULATION");
        for ( Chromossome chrom : population ) {
            System.out.println( (i++) + ".\t" + chrom );
        }
    }

}

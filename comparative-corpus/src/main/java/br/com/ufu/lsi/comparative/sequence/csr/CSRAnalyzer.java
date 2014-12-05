
package br.com.ufu.lsi.comparative.sequence.csr;

import java.util.List;

import br.com.ufu.lsi.comparative.model.Sentence;
import br.com.ufu.lsi.comparative.util.PropertiesUtil;

public class CSRAnalyzer {

    private static final Double MINSUP = PropertiesUtil.getDoubleProperty( "MINSUP" );

    private static final String COMPARATIVE_CLASS = PropertiesUtil.getProperty( "COMPARATIVE_CLASS" );

    private static final String NOT_COMPARATIVE_CLASS = PropertiesUtil.getProperty( "NOT_COMPARATIVE_CLASS" );

    public List< Sentence > analyze( List< ClassSequentialRule > rules, List<Sentence> sentences ) {
        
        for ( ClassSequentialRule rule : rules ) {

            double counterComparative = 0.0;
            double counterNotComparative = 0.0;

            for ( Sentence sentence : sentences ) {

                if ( satisfies( rule, sentence ) ) {

                    if ( COMPARATIVE_CLASS.equals( sentence.getCategory() ) ) {
                        counterComparative++ ;
                    } else {
                        counterNotComparative++ ;
                    }
                }
            }
            double compSupport = counterComparative / sentences.size();
            double notCompSupport = counterNotComparative / sentences.size();

            if ( compSupport >= MINSUP ) {
                rule.setCategory( COMPARATIVE_CLASS );
                rule.setSupport( compSupport );
            } else if ( notCompSupport >= MINSUP ) {
                rule.setCategory( NOT_COMPARATIVE_CLASS );
                rule.setSupport( notCompSupport );
            }
        }

        this.calculateConfidence( rules, sentences );

        return sentences;
    }

    public void calculateConfidence( List< ClassSequentialRule > rules, List< Sentence > sentences ) {

        for ( ClassSequentialRule rule : rules ) {

            if ( rule.getCategory() != null ) {
                double antecedent = 0.0;
                double consequent = 0.0;

                for ( Sentence sentence : sentences ) {

                    if ( satisfies( rule, sentence ) ) {
                        antecedent++ ;
                        if ( rule.getCategory().equals( sentence.getCategory() ) ) {
                            consequent++ ;
                        }
                    }

                    rule.setConfidence( consequent / antecedent );
                }
            }
        }

    }

    
    public boolean satisfies( ClassSequentialRule rule, Sentence sentence ) {

        int i = 0;
        int asserts = 0;
        for ( String tag : rule.getTags() ) {

            for ( ; i < sentence.getTaggedWords().size(); i++ ) {
                String sentenceTag = sentence.getTaggedWords().get( i ).tag();
                if ( tag.equals( sentenceTag ) ) {
                    asserts++ ;
                    break;
                }
            }
        }

        return asserts == rule.getTags().size() ? true : false;
    }
}

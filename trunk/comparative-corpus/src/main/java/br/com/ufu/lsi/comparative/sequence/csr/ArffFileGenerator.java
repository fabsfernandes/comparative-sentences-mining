
package br.com.ufu.lsi.comparative.sequence.csr;

import java.util.List;

import br.com.ufu.lsi.comparative.model.Sentence;
import br.com.ufu.lsi.comparative.util.PropertiesUtil;

public class ArffFileGenerator {

    private static final Double MINSUP = PropertiesUtil.getDoubleProperty( "MINSUP" );

    private static final Double MINCONF = PropertiesUtil.getDoubleProperty( "MINCONF" );
    
    private static final String COMPARATIVE_CLASS = PropertiesUtil.getProperty( "COMPARATIVE_CLASS" );

    private static final String NOT_COMPARATIVE_CLASS = PropertiesUtil.getProperty( "NOT_COMPARATIVE_CLASS" );

    public void generateArffFile( List< ClassSequentialRule > rules, List< Sentence > sentences ) {

        StringBuilder builder = new StringBuilder();

        builder.append( "@relation CSR_Collection\n\n" );

        builder.append( generateAttributes( rules ) );

        builder.append( "\n@data\n" );
        
        builder.append( generateData( rules, sentences ) );

        System.out.println( builder.toString() );
    }

    public String generateData( List< ClassSequentialRule > rules, List< Sentence > sentences ) {

        CSRAnalyzer analyzer = new CSRAnalyzer();

        StringBuilder builder = new StringBuilder();

        int cont = 0;
        
        for ( Sentence sentence : sentences ) {

            builder.append( (cont++) + "," );
            
            for ( ClassSequentialRule rule : rules ) {

                if ( rule.getSupport() >= MINSUP && rule.getConfidence() >= MINCONF ) {
                    
                    if ( analyzer.satisfies( rule, sentence ) ) {
                        builder.append( "1," );
                    } else {
                        builder.append( "0," );
                    }
                }
            }
            builder.append( sentence.getCategory() + "\n" );
        }

        return builder.toString();
    }

    public String generateAttributes( List< ClassSequentialRule > rules ) {

        StringBuilder builder = new StringBuilder();
        builder.append( "@attribute id NUMERIC\n" );
        
        for ( ClassSequentialRule rule : rules ) {

            if ( rule.getSupport() >= MINSUP && rule.getConfidence() >= MINCONF ) {
                builder.append( "@attribute " );
                for ( String tag : rule.getTags() ) {

                    builder.append( tag + "_" );

                }
                builder.append( " {0,1}\n" );
            }
        }
        
        builder.append( "@attribute class {" + COMPARATIVE_CLASS + "," + NOT_COMPARATIVE_CLASS + "}\n" );
        return builder.toString();
    }

}

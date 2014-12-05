
package br.com.ufu.lsi.comparative.sequence.engine;

import java.util.List;

import br.com.ufu.lsi.comparative.model.Sentence;
import br.com.ufu.lsi.comparative.sequence.csr.ArffFileGenerator;
import br.com.ufu.lsi.comparative.sequence.csr.CSRAnalyzer;
import br.com.ufu.lsi.comparative.sequence.csr.CSRFormatter;
import br.com.ufu.lsi.comparative.sequence.csr.ClassSequentialRule;

public class CSRBuilder {

    public static void main( String... args ) throws Exception {

        Long init = System.currentTimeMillis();

        CSRBuilder csrBuilder = new CSRBuilder();

        List< Sentence > sentences = csrBuilder.getSentences();
        List< ClassSequentialRule > rules = csrBuilder.generateCSR( sentences );

        csrBuilder.generateFileForClassifier( rules, sentences );

        printResults( rules, init );
    }
    
    public void generateFileForClassifier( List<ClassSequentialRule> rules, List<Sentence> sentences ) {
        
        ArffFileGenerator generator = new ArffFileGenerator();
        generator.generateArffFile( rules, sentences );
    }

    
    /**
     * Decode prefix span file and analyze rules sup and conf
     * 
     * @param sentences
     * @return
     * @throws Exception
     */
    public List< ClassSequentialRule > generateCSR( List< Sentence > sentences ) throws Exception {

        CSRFormatter formatter = new CSRFormatter();
        List< ClassSequentialRule > rules = formatter.convertPrefixSpanToCSRFormat();
        
        CSRAnalyzer analyzer = new CSRAnalyzer();
        sentences = analyzer.analyze( rules, sentences );

        return rules;
    }

    
    public List<Sentence> getSentences() {
        CSRFormatter formatter = new CSRFormatter();
        List<Sentence> sentences = formatter.getSentences();        
        return sentences;
    }
    
    /**
     * Just print for debug purposes
     * 
     * @param rules
     * @param init
     */
    public static void printResults( List< ClassSequentialRule > rules, long init ) {

        System.out.println( "=== FINAL CSR === " );
        int i = 0;
        for ( ClassSequentialRule r : rules )
            System.out.println( ( i++ ) + "\t" + r );

        System.out.println( "STATS" );
        System.out.println( "Rules length = " + rules.size() );
        System.out.println( "Time = " + ( System.currentTimeMillis() - init ) + "ms" );
    }
}


package br.com.ufu.lsi.comparative.sequence.engine;

import java.util.List;

import br.com.ufu.lsi.comparative.main.Main;
import br.com.ufu.lsi.comparative.model.Sentence;
import br.com.ufu.lsi.comparative.sequence.csr.CSRFormatter;
import br.com.ufu.lsi.comparative.sequence.csr.ClassSequentialRule;
import br.com.ufu.lsi.comparative.util.PropertiesUtil;

public class SequenceBuilder {

    private static final List<String> COMPARISON_TAGS = PropertiesUtil.getListProperty( "COMPARISON_TAGS" );

    /**
     * Generate sequence file
     * 
     * @param sentences
     * @throws Exception
     */
    public void buildSequences( List< Sentence > sentences ) throws Exception {

        CSRFormatter formatter = new CSRFormatter();
        List< ClassSequentialRule > rules = formatter.convertSentenceToCSR( sentences, COMPARISON_TAGS );
        formatter.convertCSRToPrefixSpanFormat( rules );
    }

    public static void main( String... args ) throws Exception {

        Main.main( );
    }
    
}

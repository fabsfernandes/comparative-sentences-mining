
package br.com.ufu.lsi.comparative.sequence.csr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import br.com.ufu.lsi.comparative.model.Sentence;
import br.com.ufu.lsi.comparative.util.FileUtil;
import br.com.ufu.lsi.comparative.util.PropertiesUtil;
import edu.stanford.nlp.ling.TaggedWord;

public class CSRFormatter {

    private static final String SEQUENCE_INPUT_FILE = PropertiesUtil.getProperty( "SEQUENCE_INPUT_FILE" );

    private static final String SEQUENCE_OUTPUT_FILE = PropertiesUtil.getProperty( "SEQUENCE_OUTPUT_FILE" );

    private static final String CODE_FILE = PropertiesUtil.getProperty( "CODE_FILE" );

    private static final String SENTENCES_FILE = PropertiesUtil.getProperty( "SENTENCES_FILE" );

        
    /**
     * 
     * @param sentences
     * @param comparisonTags
     * @return
     */
    public List< ClassSequentialRule > convertSentenceToCSR( List< Sentence > sentences,
            List< String > comparisonTags ) {

        List< ClassSequentialRule > rules = new ArrayList< ClassSequentialRule >();

        for ( Sentence sentence : sentences ) {

            ClassSequentialRule csr = new ClassSequentialRule();
            csr.setCategory( sentence.getCategory() );

            List< String > tags = new ArrayList< String >();
            for ( TaggedWord taggedWord : sentence.getTaggedWords() ) {
                String tag = taggedWord.tag();
                if ( comparisonTags.contains( taggedWord.tag() ) ) {
                    tag = taggedWord.word() + tag;
                }
                tags.add( tag );
            }
            csr.setTags( tags );

            rules.add( csr );
        }

        FileUtil.serializeObject( sentences, SENTENCES_FILE );

        return rules;

    }

    
    /**
     * 
     * @param rules
     * @throws Exception
     */
    public void convertCSRToPrefixSpanFormat( List< ClassSequentialRule > rules ) throws Exception {

        Map< String, Integer > codes = new HashMap< String, Integer >();
        Map< Integer, String > invertCodes = new HashMap< Integer, String >();

        // build hashmap
        Integer code = 1;
        for ( ClassSequentialRule rule : rules ) {
            List< String > tags = rule.getTags();
            for ( String tag : tags ) {
                if ( ! codes.containsKey( tag ) ) {
                    codes.put( tag, code );
                    invertCodes.put( code, tag );
                    code++ ;
                }
            }
        }

        // serialize invertCodes
        FileUtil.serializeObject( invertCodes, CODE_FILE );

        // serialize input file
        BufferedWriter bw = FileUtil.openOutputFile( SEQUENCE_INPUT_FILE );

        for ( ClassSequentialRule rule : rules ) {
            List< String > tags = rule.getTags();
            for ( String tag : tags ) {
                bw.write( codes.get( tag ) + " -1 " );
            }
            bw.write( "-2\n" );
        }
        bw.close();
    }

    
    /**
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings( "unchecked" )
    public List< ClassSequentialRule > convertPrefixSpanToCSRFormat() throws Exception {

        List< ClassSequentialRule > rules = new ArrayList< ClassSequentialRule >();
        Map< Integer, String > codes = ( HashMap< Integer, String > ) FileUtil.deserializeObject( CODE_FILE );

        BufferedReader br = FileUtil.openInputFile( SEQUENCE_OUTPUT_FILE );

        String currentLine = null;
        while ( ( currentLine = br.readLine() ) != null ) {

            ClassSequentialRule csr = new ClassSequentialRule();
            List< String > tags = new ArrayList< String >();

            String[] tokens = currentLine.split( " " );
            for ( String token : tokens ) {
                if ( "#SUP:".equals( token ) )
                    break;
                if ( StringUtils.isNumeric( token ) ) {
                    Integer code = Integer.parseInt( token );
                    if ( code != - 1 ) {
                        String tag = codes.get( code );
                        tags.add( tag );
                    }
                }
            }
            csr.setTags( tags );
            rules.add( csr );
        }

        br.close();

        return rules;
    }

    
    @SuppressWarnings( "unchecked" )
    public List<Sentence> getSentences() {
        
        return ( List< Sentence > ) FileUtil.deserializeObject( SENTENCES_FILE );
    }
}

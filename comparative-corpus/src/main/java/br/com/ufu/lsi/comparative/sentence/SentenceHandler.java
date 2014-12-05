
package br.com.ufu.lsi.comparative.sentence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.SerializationUtils;

import br.com.ufu.lsi.comparative.model.Sentence;
import br.com.ufu.lsi.comparative.util.PropertiesUtil;
import edu.stanford.nlp.ling.TaggedWord;

public class SentenceHandler {

    /**
     * must be a file formatted as: id sentence <otherattributes>
     */
    private static final String INPUT_FILE = PropertiesUtil.getProperty( "INPUT_FILE" );

    private static int RADIUS = PropertiesUtil.getIntProperty( "RADIUS" );
    
    

    /**
     * Pivot operations: main generate pivot
     * 
     * @param sentences
     * @return
     * @throws Exception
     */
    public List< Sentence > generatePivot( List< Sentence > sentences, List<String> comparisonTags ) throws Exception {

        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream( "keywords" );
        properties.load( inputStream );

        List< Sentence > finalSentences = new ArrayList< Sentence >();
        for ( Sentence sentence : sentences ) {
            List< Sentence > pivotedSentences = getPivotedSentences( sentence, properties, comparisonTags );
            finalSentences.addAll( pivotedSentences );
        }

        return finalSentences;
    }

    public List< Sentence > getPivotedSentences( Sentence originalSentence, Properties properties, List<String> comparisonTags ) {
        
        List< Sentence > pivotedSentences = new ArrayList< Sentence >();
        
        for( int i = 0; i < originalSentence.getTaggedWords().size(); i++ ) {
            
            TaggedWord word = originalSentence.getTaggedWords().get( i );
            String token = word.word();
            String tag = word.tag();
            
            // check keywords
            if ( properties.containsKey( token ) || comparisonTags.contains( tag ) ) {
                
                Sentence pivotedSentence = buildPivotedSentence( originalSentence, i );
                pivotedSentences.add( pivotedSentence );
            } 
        }
        
        return pivotedSentences;
    }
    
    public Sentence buildPivotedSentence( Sentence originalSentence, int tokenPosition ) {

        Sentence pivotedSentence = SerializationUtils.clone( originalSentence );
        pivotedSentence.getTaggedWords().clear();
        pivotedSentence.setText( null );
        
        int i = ( tokenPosition - RADIUS ) > 0 ? tokenPosition - RADIUS : 0; 
        for( ; i < tokenPosition; i++ ) {
            pivotedSentence.getTaggedWords().add( originalSentence.getTaggedWords().get(i) );
        }

        pivotedSentence.getTaggedWords().add( originalSentence.getTaggedWords().get( tokenPosition ) );
        
        
        int cont = 0;
        for( i = tokenPosition + 1; cont < RADIUS && i < originalSentence.getTaggedWords().size(); i++, cont++ ) {
            pivotedSentence.getTaggedWords().add( originalSentence.getTaggedWords().get(i) );
        }
        
        return pivotedSentence;
    }

    /**
     * Pivot operation
     * 
     * @param sentence
     * @param properties
     * @return
     *
    public List< Sentence > getPivotedSentences( Sentence sentence, Properties properties ) {

        List< Sentence > pivotedSentences = new ArrayList< Sentence >();

        String str = sentence.getText();

        List< String > keywords = findKeywords( str, properties );

        for ( String keyword : keywords ) {

            // check manual keywords
            Matcher matcher = Pattern.compile( keyword ).matcher( str );

            while ( matcher.find() ) {

                String before = str.substring( 0, matcher.start() );
                String after = str.substring( matcher.end() );

                String[] beforeTokens = SentenceUtil.sentenceTokenize( before );
                before = "";
                int i;
                if ( beforeTokens.length < RADIUS )
                    i = beforeTokens.length;
                else
                    i = RADIUS;
                for ( int j = beforeTokens.length - 1; i > 0; i-- , j-- ) {
                    before = beforeTokens[ j ] + " " + before;
                }

                String[] afterTokens = SentenceUtil.sentenceTokenize( after );
                after = "";
                for ( i = 0; i < RADIUS && i < afterTokens.length; i++ ) {
                    after += " " + afterTokens[ i ];
                }

                String sentenceFound = before + keyword + after;

                // build pivoted sentence
                Sentence pivotedSentence = buildPivotedSentence( sentence, sentenceFound );
                pivotedSentences.add( pivotedSentence );
            }

        }

        // check postag keywords

        return pivotedSentences;
    }
*/
    

    /**
     * Pivot operation
     * 
     * @param sentenceText
     * @param properties
     * @return
     */
    public List< String > findKeywords( String sentenceText, Properties properties ) {

        List< String > keywords = new ArrayList< String >();

        String[] tokens = sentenceText.split( " " );
        for ( String token : tokens ) {

            token = token.toLowerCase().trim();

            if ( properties.containsKey( token ) )
                if ( ! keywords.contains( token ) )
                    keywords.add( token );
        }

        return keywords;
    }

    /**
     * Read sentences from corpus file
     * 
     * @return
     * @throws IOException
     */
    public List< Sentence > load() throws IOException {

        List< Sentence > sentences = new ArrayList< Sentence >();

        try (BufferedReader br = new BufferedReader( new FileReader( INPUT_FILE ) )) {

            String currentLine = null;
            while ( ( currentLine = br.readLine() ) != null ) {
                if ( ! currentLine.trim().equals( "" ) ) {
                    try {
                        String[] tokens = currentLine.split( "\\t" );
                        Sentence sentence = new Sentence();
                        sentence.setReviewId( Long.parseLong( tokens[ 0 ] ) );
                        sentence.setCategory( tokens[ 1 ] );
                        sentence.setText( tokens[ 2 ].toLowerCase() );
                        sentences.add( sentence );
                    } catch ( NumberFormatException e ) {
                        // does nothing
                    }
                }
            }
        }

        return sentences;
    }

}

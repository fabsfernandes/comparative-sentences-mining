
package br.com.ufu.lsi.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.SerializationUtils;

import br.com.ufu.lsi.model.Sentence;
import br.com.ufu.lsi.util.SentenceUtil;

public class SentenceHandler {

    /**
     * must be a file formatted as: id sentence <otherattributes>
     */
    private static final String INPUT_FILE = "/Users/fabiola/Desktop/Trabalho3/datasets/amazon_reviews/mp3_processed_classified.txt";

    private static int RADIUS = 3;

    /**
     * Pivot operations: main generate pivot
     * 
     * @param sentences
     * @return
     * @throws Exception
     */
    public List< Sentence > generatePivot( List< Sentence > sentences ) throws Exception {

        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream( "keywords" );
        properties.load( inputStream );

        List< Sentence > finalSentences = new ArrayList< Sentence >();
        for ( Sentence sentence : sentences ) {
            List< Sentence > pivotedSentences = getPivotedSentences( sentence, properties );
            finalSentences.addAll( pivotedSentences );
        }

        return finalSentences;
    }

    /**
     * Pivot operation
     * 
     * @param sentence
     * @param properties
     * @return
     */
    public List< Sentence > getPivotedSentences( Sentence sentence, Properties properties ) {

        List< Sentence > pivotedSentences = new ArrayList< Sentence >();

        String str = sentence.getText();

        List< String > keywords = findKeywords( str, properties );

        for ( String keyword : keywords ) {

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
                Sentence newSentence = SerializationUtils.clone( sentence );
                newSentence.setText( sentenceFound );
                pivotedSentences.add( newSentence );
            }
        }

        return pivotedSentences;

    }

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
                        sentence.setText( tokens[ 2 ] );
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

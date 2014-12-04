
package br.com.ufu.lsi.preprocess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import br.com.ufu.lsi.model.AmazonReview;
import br.com.ufu.lsi.sentence.HeavySentenceNLP;
import br.com.ufu.lsi.util.FileUtil;

public class AmazonReviewPreprocess {

    private static final String INPUT_FILE = "/Users/fabiola/Desktop/Trabalho3/datasets/amazon_reviews/amazon_mp3";

    private static final String OUTPUT_FILE = "/Users/fabiola/Desktop/Trabalho3/datasets/amazon_reviews/mp3_processed.csv";
    
    private static long id = 0;

    
    public static void main( String... args ) throws Exception {

        BufferedWriter bw = FileUtil.openOutputFile( OUTPUT_FILE );

        BufferedReader br = FileUtil.openInputFile( INPUT_FILE );

        try {

            String sCurrentLine;
            
            AmazonReview review = new AmazonReview( id++ );

            while ( ( sCurrentLine = br.readLine() ) != null ) {

                review = handleLine( sCurrentLine, review, bw );

            }

        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            try {
                if ( br != null )
                    br.close();
                if ( bw != null )
                    bw.close();
            } catch ( IOException ex ) {
                ex.printStackTrace();
            }
        }

    }

    public static AmazonReview handleLine( String line, AmazonReview review,
            BufferedWriter bw ) throws Exception {

        if ( line.equals( "" ) ) {
            writeReview( review, bw );
            return new AmazonReview( id++ );
        }

        int endIndex = line.indexOf( ":" );
        String attribute = endIndex != - 1 ? line.substring( 0, endIndex ) : null;

        if ( attribute != null ) {

            int initIndex;
            String text;

            switch ( attribute ) {

                case "[productName]":
                    initIndex = line.indexOf( ":" );
                    text = line.substring( initIndex + 1 );
                    review.setProductName( text );
                    break;
                case "[title]":
                    initIndex = line.indexOf( ":" );
                    text = line.substring( initIndex + 1 );
                    review.setTitle( text );
                    break;
                case "[author]":
                    initIndex = line.indexOf( ":" );
                    text = line.substring( initIndex + 1 );
                    review.setAuthor( text );
                    break;
                case "[fullText]":
                    initIndex = line.indexOf( ":" );
                    text = line.substring( initIndex + 1 );
                    review.setFullText( text );
                    break;
                case "[webUrl]":
                    initIndex = line.indexOf( ":" );
                    text = line.substring( initIndex + 1 );
                    review.setWebUrl( text );
                    break;
                default:
            }

        }
        return review;
    }

    public static void writeReview( AmazonReview review, BufferedWriter bw ) throws Exception {

        HeavySentenceNLP preprocess = new HeavySentenceNLP();
        List< String > sentences = preprocess.getSentences( review.getFullText() );
        review.setSentences( sentences );

        //System.out.println( review.toString() + "\n" );
        bw.write( review.toString() + "\n" );
    }

}


package br.com.ufu.lsi.comparative.sentence;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class HeavySentenceNLP {

    private static StanfordCoreNLP pipeline;

    static {

        Properties props = new Properties();
        props.put( "annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref" );

        pipeline = new StanfordCoreNLP( props );
    }

    
    public List< String > getSentences( String text ) {

        List< String > finalSentences = new ArrayList< String >();

        Annotation document = new Annotation( text );

        pipeline.annotate( document );

        List< CoreMap > sentences = document.get( SentencesAnnotation.class );

        for ( CoreMap sentence : sentences ) {

            finalSentences.add( sentence.toString() );
        }

        return finalSentences;
    }

}

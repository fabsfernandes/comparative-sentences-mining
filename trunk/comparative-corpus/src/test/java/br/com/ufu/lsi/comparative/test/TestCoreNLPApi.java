package br.com.ufu.lsi.comparative.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.international.arabic.process.IOBUtils;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.tagger.maxent.TestSentence;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class TestCoreNLPApi {

    //@Test
    public void preprocess() throws Exception {

        Properties props = new Properties();
        props.put( "annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref" );

        StanfordCoreNLP pipeline = new StanfordCoreNLP( props );

        // read some text in the text variable
        String text = "...the 'Windows' software to download songs to the device DOES NOT WORK WITH Windows NT."
                + "<p>Also, the headphones are low quality so you might want to get some better ones because the actual quality of the audio signal is  pretty good.  "
                + "The solid-state walkman has arrived.  ";

        File f = new File( "/Users/fabiola/Desktop/Trabalho3/datasets/test.txt" );
        String text2 = IOUtils.slurpFile( f );

        Annotation document = new Annotation( text2 );

        pipeline.annotate( document );

        List< CoreMap > sentences = document.get( SentencesAnnotation.class );

        for ( CoreMap sentence : sentences ) {

            System.out.println( "*" + sentence.toString() );

            for ( CoreLabel token : sentence.get( TokensAnnotation.class ) ) {
                // this is the text of the token
                String word = token.get( TextAnnotation.class );
                // this is the POS tag of the token
                String pos = token.get( PartOfSpeechAnnotation.class );
                // this is the NER label of the token
                String ne = token.get( NamedEntityTagAnnotation.class );
            }

            Tree tree = sentence.get( TreeAnnotation.class );

            SemanticGraph dependencies = sentence.get( CollapsedCCProcessedDependenciesAnnotation.class );
        }

        Map< Integer, CorefChain > graph = document.get( CorefChainAnnotation.class );
    }
    
    
    @Test
    public void postagTest() throws Exception {
        MaxentTagger tagger = new MaxentTagger( "english-bidirectional-distsim.tagger" );
        String sample = "Product A is better than B";
        String tagged = tagger.tagString( sample );
        String tagged2 = tagger.tagTokenizedString( sample );
        
        
        
        List<Word> sent = Sentence.toUntaggedList(Arrays.asList(sample.split("\\s+")));
        TestSentence testSentence = new TestSentence( tagger );
        List<TaggedWord> taggedWords = testSentence.tagSentence(sent, false);
        System.out.println( taggedWords );
    }
    
}

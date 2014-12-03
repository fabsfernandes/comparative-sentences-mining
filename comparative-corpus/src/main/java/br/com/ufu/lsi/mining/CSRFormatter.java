package br.com.ufu.lsi.mining;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ufu.lsi.model.Sentence;
import br.com.ufu.lsi.util.FileUtil;
import edu.stanford.nlp.ling.TaggedWord;

public class CSRFormatter {
    
    private static final String SEQUENCE_INPUT_FILE = "/Users/fabiola/Desktop/Trabalho3/files/sequence_input";
    
    private static final String CODE_FILE = "/Users/fabiola/Desktop/Trabalho3/files/codes_serialized";
    
    
    public List<ClassSequentialRule> convertSentenceToCSR( List<Sentence> sentences ) {
        
        List<ClassSequentialRule> rules = new ArrayList< ClassSequentialRule >();
        
        for( Sentence sentence : sentences ) {
            
            ClassSequentialRule csr = new ClassSequentialRule();
            csr.setCategory( sentence.getCategory() );
            List<TaggedWord> taggedWords = new ArrayList<TaggedWord>( sentence.getTaggedWords() );
            csr.setTaggedWords( taggedWords );
            
            rules.add( csr );
        }
        
        return rules;
        
    }
    
    public void convertCSRToPrefixSpanFormat( List<ClassSequentialRule> rules, List<String> comparisonTags ) throws Exception {
        
        Map<String,Integer> codes = new HashMap<String,Integer>();
        
        // build hashmap
        Integer code = 1;
        for( ClassSequentialRule rule : rules ) {
            List<TaggedWord> taggedWords = rule.getTaggedWords();
            for( TaggedWord word : taggedWords ) {
                String tag = word.tag();
                if( comparisonTags.contains( tag ) ) {
                    tag = word.word() + tag;
                }
                if( !codes.containsKey( tag ) ) {
                    codes.put( tag, code++ );
                }
            }
        }
        
        // serialize codes
        FileUtil.serializeObject( codes, CODE_FILE );
                
        // serialize input file
        BufferedWriter bw = FileUtil.openOutputFile( SEQUENCE_INPUT_FILE );
        
        for( ClassSequentialRule rule : rules ) {
            List<TaggedWord> taggedWords = rule.getTaggedWords();
            for( TaggedWord word : taggedWords ) {
                String tag = word.tag();
                if( comparisonTags.contains( tag ) ) {
                    tag = word.word() + tag;
                }
                bw.write( codes.get( tag ) + " -1 ");
            }
            bw.write( "-2\n" );
        }
        bw.close();
        
    }
    
    public void convertPrefixSpanToCSRFormat() {
        
        // convert prefixspan result to CSR format 
        
    }

}

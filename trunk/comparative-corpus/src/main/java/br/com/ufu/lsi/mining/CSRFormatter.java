package br.com.ufu.lsi.mining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ufu.lsi.model.Sentence;
import edu.stanford.nlp.ling.TaggedWord;

public class CSRFormatter {
    
    public List<ClassSequentialRule> convertFromSentenceToCSR( List<Sentence> sentences ) {
        
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
    
    public void convertFromCSRToPrefixSpanFormat( List<ClassSequentialRule> rules ) {
        
        Map<String,Integer> codes = new HashMap<String,Integer>();
        
        // build hashmap
        Integer code = 1;
        for( ClassSequentialRule rule : rules ) {
            List<TaggedWord> taggedWords = rule.getTaggedWords();
            for( TaggedWord word : taggedWords ) {
                if( !codes.containsKey( word.tag() ) ) {
                    codes.put( word.tag(), code++ );
                }
            }
        }
        
        // serialize codes
        
        // serialize input file
        for( ClassSequentialRule rule : rules ) {
            List<TaggedWord> taggedWords = rule.getTaggedWords();
            for( TaggedWord word : taggedWords ) {
                
            }
        }
        
    }
    
    public void convertFromPrefixSpanToCSRFormat() {
        
        // convert prefixspan result to CSR format 
        
    }

}

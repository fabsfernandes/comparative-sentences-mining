package br.com.ufu.lsi.csr;

import java.util.List;

import edu.stanford.nlp.ling.TaggedWord;

public class ClassSequentialRule {
    
    private List<TaggedWord> taggedWords;
    
    private String category;
    
    private double support;
    
    private double confidence;

    public List< TaggedWord > getTaggedWords() {
        return taggedWords;
    }

    public void setTaggedWords( List< TaggedWord > taggedWords ) {
        this.taggedWords = taggedWords;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory( String category ) {
        this.category = category;
    }

    public double getSupport() {
        return support;
    }

    public void setSupport( double support ) {
        this.support = support;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence( double confidence ) {
        this.confidence = confidence;
    }

}

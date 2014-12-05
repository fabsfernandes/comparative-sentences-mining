package br.com.ufu.lsi.comparative.sequence.csr;

import java.util.List;

public class ClassSequentialRule {
    
    private List<String> tags;
    
    private String category;
    
    private double support;
    
    private double confidence;

    
    public List< String > getTags() {
        return tags;
    }

    public void setTags( List< String > tags ) {
        this.tags = tags;
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

    @Override
    public String toString() {
        
        StringBuilder builder = new StringBuilder();
        
        builder.append( "\t" + category + "\t" + support + "\t" + confidence );
        builder.append( "\t" + tags );
        
        return builder.toString();
    }

}

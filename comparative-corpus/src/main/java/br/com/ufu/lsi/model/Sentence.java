package br.com.ufu.lsi.model;

import java.io.Serializable;
import java.util.List;

import edu.stanford.nlp.ling.TaggedWord;

public class Sentence implements Serializable {


    private static final long serialVersionUID = 1L;

    
    private long reviewId;
    
    private String category;

    private String text;
    
    private List<TaggedWord> taggedWords;
    
    
    public String getCategory() {
        return category;
    }

    public void setCategory( String category ) {
        this.category = category;
    }


    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId( long reviewId ) {
        this.reviewId = reviewId;
    }

    public String getText() {
        return text;
    }

    public void setText( String text ) {
        this.text = text;
    }
    

    public List< TaggedWord > getTaggedWords() {
        return taggedWords;
    }

    public void setTaggedWords( List< TaggedWord > taggedWords ) {
        this.taggedWords = taggedWords;
    }
    
    @Override
    public String toString() {
        
        StringBuilder builder = new StringBuilder();
        
        builder.append( "\t" + reviewId + "\t" + category + "\t" + text );
        builder.append( "\n" + taggedWords );
        
        return builder.toString();
    }
}

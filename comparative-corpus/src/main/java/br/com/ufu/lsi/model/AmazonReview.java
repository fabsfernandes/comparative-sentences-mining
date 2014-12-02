
package br.com.ufu.lsi.model;

import java.util.List;

public class AmazonReview {

    private long id;
    
    private String productName;

    private String title;

    private String author;

    private String fullText;

    private String webUrl;
    
    private List<String> sentences;
    
    public AmazonReview( long id ) {
        this.id = id;
    }
    
    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName( String productName ) {
        this.productName = productName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor( String author ) {
        this.author = author;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText( String fullText ) {
        this.fullText = fullText;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl( String webUrl ) {
        this.webUrl = webUrl;
    }

    public List< String > getSentences() {
        return sentences;
    }

    public void setSentences( List< String > sentences ) {
        this.sentences = sentences;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for( String sentence : sentences ) {
            builder.append( id + "\t" );
            builder.append( sentence + "\t" );
            builder.append( productName + "\t" );
            builder.append( title + "\t" );
            builder.append( author + "\t" );
            builder.append( webUrl + "\n" );
        }
        
        return builder.toString();
    }

}

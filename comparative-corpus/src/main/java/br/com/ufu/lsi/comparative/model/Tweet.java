package br.com.ufu.lsi.comparative.model;

import java.util.Date;

public class Tweet {
    
    private long id;
    
    private Date createdAt;
    
    private String user;
    
    private String text;
    
    private TweetType type;

    public String getUser() {
        return user;
    }

    public void setUser( String user ) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText( String text ) {
        this.text = text;
    }

    public TweetType getType() {
        return type;
    }

    public void setType( TweetType type ) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt( Date createdAt ) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        
        builder.append( id + "\t" );
        builder.append( text + "\t" );
        builder.append( user + "\t" );
        builder.append( createdAt + "\t" );
        builder.append( type + "\t" );
        
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( createdAt == null ) ? 0 : createdAt.hashCode() );
        result = prime * result + ( int ) ( id ^ ( id >>> 32 ) );
        result = prime * result + ( ( text == null ) ? 0 : text.hashCode() );
        result = prime * result + ( ( type == null ) ? 0 : type.hashCode() );
        result = prime * result + ( ( user == null ) ? 0 : user.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        
        Tweet other = ( Tweet ) obj;
        if( id == other.id )
            return true;
        return false;
        
        /*if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        Tweet other = ( Tweet ) obj;
        if ( createdAt == null ) {
            if ( other.createdAt != null )
                return false;
        } else if ( ! createdAt.equals( other.createdAt ) )
            return false;
        if ( id != other.id )
            return false;
        if ( text == null ) {
            if ( other.text != null )
                return false;
        } else if ( ! text.equals( other.text ) )
            return false;
        if ( type != other.type )
            return false;
        if ( user == null ) {
            if ( other.user != null )
                return false;
        } else if ( ! user.equals( other.user ) )
            return false;
        return true;*/
    }

}


package br.com.ufu.lsi.comparative.ga.model;

import java.io.Serializable;

public class Gene implements Serializable {

    private static final long serialVersionUID = 1L;

    private String value;

    private Boolean isClass;

    public String getValue() {
        return value;
    }

    public void setValue( String value ) {
        this.value = value;
    }

    public Boolean getIsClass() {
        return isClass;
    }

    public void setIsClass( Boolean isClass ) {
        this.isClass = isClass;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( isClass == null ) ? 0 : isClass.hashCode() );
        result = prime * result + ( ( value == null ) ? 0 : value.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        Gene other = ( Gene ) obj;
        if ( isClass == null ) {
            if ( other.isClass != null )
                return false;
        } else if ( ! isClass.equals( other.isClass ) )
            return false;
        if ( value == null ) {
            if ( other.value != null )
                return false;
        } else if ( ! value.equals( other.value ) )
            return false;
        return true;
    }
}

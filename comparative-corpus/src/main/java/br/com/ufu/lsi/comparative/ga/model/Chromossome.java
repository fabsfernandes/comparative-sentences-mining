package br.com.ufu.lsi.comparative.ga.model;

import java.io.Serializable;
import java.util.Arrays;


public class Chromossome implements Serializable, Comparable {
    
    private static final long serialVersionUID = 1L;

    private double fitness;
    
    private Gene [] genes;

    public double getFitness() {
        return fitness;
    }

    public void setFitness( double fitness ) {
        this.fitness = fitness;
    }

    public Gene[] getGenes() {
        return genes;
    }

    public void setGenes( Gene[] genes ) {
        this.genes = genes;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        
        for( Gene gene : genes  ) {
            builder.append( gene.getValue() + "\t");
        }
        builder.append( fitness );
        
        return builder.toString();
    }

    @Override
    public int compareTo( Object o ) {
        Double objectFitness = ((Chromossome) o).getFitness();
        if( objectFitness > this.fitness )
            return 1;
        else if( objectFitness < this.fitness )
            return -1;
        return 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode( genes );
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
        Chromossome other = ( Chromossome ) obj;
        if ( ! Arrays.equals( genes, other.genes ) )
            return false;
        return true;
    }

}

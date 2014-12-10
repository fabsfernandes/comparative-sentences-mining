package br.com.ufu.lsi.comparative.ga.model;

public class Stats {
    
    private Double truePositive;
    
    private Double trueNegative;
    
    private Double falsePositive;
    
    private Double falseNegative;

    
    public Stats( Double truePositive, Double trueNegative, Double falsePositive, Double falseNegative ) {
        super();
        this.truePositive = truePositive;
        this.trueNegative = trueNegative;
        this.falsePositive = falsePositive;
        this.falseNegative = falseNegative;
    }

    public Double getPrecision() {
        return truePositive / ( truePositive + falsePositive );
    }

    public Double getRecall() {
        return truePositive / ( truePositive + falseNegative );
    }

    public Double getAccuracy() {
        return (truePositive + trueNegative) / ( truePositive + trueNegative + falsePositive + falseNegative );
    }

    public Double getfScore() {
        return 2* truePositive / (2*truePositive + falsePositive + falseNegative );
    }

    public Double getTruePositive() {
        return truePositive;
    }

    public void setTruePositive( Double truePositive ) {
        this.truePositive = truePositive;
    }

    public Double getTrueNegative() {
        return trueNegative;
    }

    public void setTrueNegative( Double trueNegative ) {
        this.trueNegative = trueNegative;
    }

    public Double getFalsePositive() {
        return falsePositive;
    }

    public void setFalsePositive( Double falsePositive ) {
        this.falsePositive = falsePositive;
    }

    public Double getFalseNegative() {
        return falseNegative;
    }

    public void setFalseNegative( Double falseNegative ) {
        this.falseNegative = falseNegative;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        
        builder.append( "Precisao = " + getPrecision() + "\n" );
        builder.append( "Recall = " + getRecall() + "\n" );
        builder.append( "F Score = " + getfScore() + "\n" );
        builder.append( "Accuracy = " + getAccuracy() + "\n" );
        
        return builder.toString();
    }

    

}

package br.com.ufu.lsi.comparative.ga.model;

public class Stats {
    
    private Double precision;
    
    private Double recall;
    
    private Double accuracy;
    
    private Double fScore;

    public Double getPrecision() {
        return precision;
    }

    public void setPrecision( Double precision ) {
        this.precision = precision;
    }

    public Double getRecall() {
        return recall;
    }

    public void setRecall( Double recall ) {
        this.recall = recall;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy( Double accuracy ) {
        this.accuracy = accuracy;
    }

    public Double getfScore() {
        return fScore;
    }

    public void setfScore( Double fScore ) {
        this.fScore = fScore;
    }

}

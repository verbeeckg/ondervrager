package be.verbeeck.ondervrager.model;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Embeddable
public class Probability {
    private int probability;

    public Probability(){

    }
    public Probability(int probability){
        this.probability = probability;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    @Transient
    public void increase(){
        probability++;
    }

    @Transient
    public void decrease(){
        probability--;
    }
}

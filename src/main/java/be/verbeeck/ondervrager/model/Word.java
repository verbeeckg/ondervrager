package be.verbeeck.ondervrager.model;

import javax.persistence.*;

@Entity
@Table(name="wordValue")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String wordValue;

    @Embedded
    private Probability probability = new Probability();

    @ManyToOne
    @JoinColumn(name = "wordListId")
    private WordList wordList;

    private int nbErrors;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWordValue() {
        return wordValue;
    }

    public void setWordValue(String wordValue) {
        this.wordValue = wordValue;
    }

    public int getNbErrors() {
        return nbErrors;
    }

    public void setNbErrors(int nbErrors) {
        this.nbErrors = nbErrors;
    }

    public Probability getProbability() {
        return probability;
    }

    public void setProbability(Probability probability) {
        this.probability = probability;
    }

    public WordList getWordList() {
        return wordList;
    }

    public void setWordList(WordList wordList) {
        this.wordList = wordList;
    }

    @Transient
    public void increaseProbability(){
        getProbability().increase();
    }

    @Transient
    public void decreaseProbability(){
        getProbability().decrease();
    }
}

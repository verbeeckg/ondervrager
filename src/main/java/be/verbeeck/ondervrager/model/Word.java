package be.verbeeck.ondervrager.model;

import javax.persistence.*;

@Entity
@Table(name="wordValue")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String wordValue;

    @ManyToOne
    @JoinColumn(name = "wordListId")
    private WordList wordList;

    private int nbErrors;
    private int nbCorrect;

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

    public int getNbCorrect() {
        return nbCorrect;
    }

    public void setNbCorrect(int nbCorrect) {
        this.nbCorrect = nbCorrect;
    }

    public WordList getWordList() {
        return wordList;
    }

    public void setWordList(WordList wordList) {
        this.wordList = wordList;
    }

    @Transient
    public void increaseNbErrors() {
        nbErrors++;
    }

    @Transient
    public void increaseNbCorrect(){
        nbCorrect++;
    }

    @Transient
    public int getNbQuestioned(){
        return getNbErrors() + getNbCorrect();
    }

    @Transient
    public int getGoodMinusWrong(){
        return getNbCorrect() - getNbErrors();
    }

    @Transient
    public int getGoodDevidedByWrong(){
        if (getNbErrors() != 0){
            return getNbCorrect() / getNbErrors();
        }
        return getNbCorrect();
    }
}

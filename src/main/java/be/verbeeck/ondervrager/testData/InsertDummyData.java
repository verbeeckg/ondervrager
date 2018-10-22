package be.verbeeck.ondervrager.testData;

import be.verbeeck.ondervrager.WordListRepository;
import be.verbeeck.ondervrager.WordRepository;
import be.verbeeck.ondervrager.model.Probability;
import be.verbeeck.ondervrager.model.Word;
import be.verbeeck.ondervrager.model.WordList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Profile("loc")
@Service
public class InsertDummyData {

    @Autowired
    private WordListRepository wordListRepository;
    @Autowired
    private WordRepository wordRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeTestData() {
        generateWordList("Lijstje 1", 30);
        generateWordList("Lijstje 2", 20);
    }

    public void generateWordList(String title, int nbWords){
        WordList wordList = new WordList();
        wordList.setTitle(title);
        wordListRepository.save(wordList);
        for (int i = 1 ; i<=nbWords ; i++){
            Word word = new Word();
            word.setWordValue("Woord"+i);
            word.setProbability(new Probability(100%i));
            word.setWordList(wordList);
            wordRepository.save(word);
        }

    }
}

package be.verbeeck.ondervrager;

import be.verbeeck.ondervrager.model.Word;
import be.verbeeck.ondervrager.model.WordList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class OndervraagService {

    @Autowired
    private WordRepository wordRepository;

    public List<Word> getWordsToOndervraag(WordList wordList, int nbWords){
        List<Word> words = wordList.getWords();
        if (words.size() <= nbWords){
            return words;
        }
        return pickwordsByProbability(words, nbWords);
    }

    private List<Word> pickwordsByProbability(List<Word> words, int nbWords) {
        List<Word> chosenWords = new ArrayList<>();
        int totalSum = 0;
        Random random = new Random();
        for(Word word : words) {
            totalSum = totalSum + word.getProbability().getProbability();
        }

        while (chosenWords.size() < nbWords){
            int index = random.nextInt(totalSum);
            int sum = 0;
            int i=0;
            while(sum < index ) {
                sum = sum + words.get(i++).getProbability().getProbability();
            }
            Word chosenWord = words.get(Math.max(0, i - 1));
            if (!chosenWords.contains(chosenWord)) {
                chosenWords.add(chosenWord);
            }
        }
        return chosenWords;
    }

    public Word getWordById(Long id){
        return wordRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void updateWord(Word word) {
        wordRepository.save(word);
    }
}

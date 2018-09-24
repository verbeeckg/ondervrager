package be.verbeeck.ondervrager;

import be.verbeeck.ondervrager.model.Word;
import be.verbeeck.ondervrager.model.WordList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;
import java.util.Optional;

@Controller
public class WordController {

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordListRepository wordListRepository;

    @RequestMapping("/words")
    public String getWords(Map<String, Object> model){
        model.put("words", wordRepository.findAll());
        return "words";
    }

    @RequestMapping("/lists/{listId}/word/add")
    public String addWord(Map<String,Object> model, @PathVariable(required = true,name="listId") Long listId){
        Word word = new Word();
        word.setWordList(wordListRepository.findById(listId).orElseThrow());
        model.put("word", word);
        return "addOrEditWord";
    }

    @RequestMapping("/word/edit/{id}")
    public String editWord(Map<String,Object> model, @PathVariable(required = true,name="id") Long id){
        model.put("word", wordRepository.findById(id).orElse(new Word()));
        return "addOrEditWord";
    }

    @RequestMapping(value="/lists/saveWord", method = RequestMethod.POST)
    public String saveWord(Map<String, Object> model, Word word){
        wordRepository.save(word);
        return "redirect:/lists/edit/"+word.getWordList().getId();
    }

    @RequestMapping(value="/word/delete/{id}", method = RequestMethod.GET)
    public String deleteWord(Map<String, Object> model, @PathVariable(required = true, name = "id") Long id){
        Word word = wordRepository.findById(id).orElse(null);
        if (word != null) {
            wordRepository.delete(word);
            return "redirect:/lists/edit/"+word.getWordList().getId();
        }

        return "redirect:/lists";
    }
}

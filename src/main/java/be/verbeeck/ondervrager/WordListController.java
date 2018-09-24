package be.verbeeck.ondervrager;

import be.verbeeck.ondervrager.model.WordList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class WordListController {

    @Autowired
    private WordListRepository wordListRepository;

    @RequestMapping("/lists")
    public String getLists(Map<String, Object> model){
        model.put("wordLists", wordListRepository.findAll());
        return "lists";
    }

    @RequestMapping("/lists/add")
    public String addWordList(Map<String,Object> model){
        model.put("wordList", new WordList());
        return "addOrEditList";
    }

    @RequestMapping("/lists/edit/{id}")
    public String editWordList(Map<String,Object> model, @PathVariable(required = true,name="id") Long id){
        model.put("wordList", wordListRepository.findById(id).orElse(new WordList()));
        return "addOrEditList";
    }

    @RequestMapping(value="/lists/saveWordList", method = RequestMethod.POST)
    public String saveWordList(Map<String, Object> model, WordList wordList){
        wordListRepository.save(wordList);
        return "redirect:/lists";
    }

    @RequestMapping(value="/lists/delete/{id}", method = RequestMethod.GET)
    public String deleteList(Map<String, Object> model, @PathVariable(required = true, name = "id") Long id){
        wordListRepository.deleteById(id);
        return "redirect:/lists";
    }
}

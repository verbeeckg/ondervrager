package be.verbeeck.ondervrager;

import be.verbeeck.ondervrager.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
public class OndervraagController {

    @Autowired
    private WordListRepository wordListRepository;

    @Autowired
    private OndervraagService ondervraagService;

    @GetMapping("/")
    public String choseList(Map<String, Object> model){
        model.put("wordLists", wordListRepository.findAll());
        return "choseWordList";
    }

    @RequestMapping("/ondervraag/list/{id}")
    public String ondervraag(Map<String, Object> model, @PathVariable(required = true,name="id") Long id, @PathVariable(required = false ,name="nbWords") Optional<Integer> nbWords){
        model.put("words", ondervraagService.getWordsToOndervraag(wordListRepository.findById(id).orElseThrow(), nbWords.orElse(20)));
        return "ondervraag";
    }

    @RequestMapping( value = "/ondervraag/word/succes", method = RequestMethod.POST)
    public @ResponseBody String wordCorrect(@RequestBody String  response, Long id){
        Word word = ondervraagService.getWordById(id);
        word.increaseProbability();
        ondervraagService.updateWord(word);
        return "OK";
    }

    @RequestMapping( value = "/ondervraag/word/failed", method = RequestMethod.POST)
    public @ResponseBody String wordIncorrect(@RequestBody String  response, Long id){
        Word word = ondervraagService.getWordById(id);
        word.decreaseProbability();
        ondervraagService.updateWord(word);
        return "NOK";
    }
// @RequestMapping( value = "/ondervraag/word/succes", method = RequestMethod.POST)
//    public String wordCorrect(Map<String, Object> model, Long id){
//        Word word = ondervraagService.getWordById(id);
//        word.increaseProbability();
//        ondervraagService.updateWord(word);
//        return "redirect:/ondervraag/list/"+word.getWordList().getId();
//    }
//
//    @RequestMapping(value = "/ondervraag/word/failed", method = RequestMethod.POST)
//    public String wordIncorrect(Map<String, Object> model, Long id){
//        Word word = ondervraagService.getWordById(id);
//        word.decreaseProbability();
//        ondervraagService.updateWord(word);
//        return "redirect:/ondervraag/list/"+word.getWordList().getId();
//    }
}

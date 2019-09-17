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
    private WordRepository wordRepository;

    @Autowired
    private OndervraagService ondervraagService;

    @GetMapping("/")
    public String choseList(Map<String, Object> model){
        model.put("wordLists", wordListRepository.findAll());
        return "choseWordList";
    }

    @RequestMapping(path = {"/ondervraag/list/{id}", "/ondervraag/list/{id}/nbWords/{nbWords}"})
    public String ondervraag(Map<String, Object> model, @PathVariable(required = true,name="id") Long id, @PathVariable(required = false ,name="nbWords") Optional<Integer> nbWords){
        model.put("words", ondervraagService.getWordsToOndervraag(wordListRepository.findById(id).orElseThrow(RuntimeException::new), nbWords.orElse(20)));
        return "ondervraag";
    }

    @RequestMapping(path = {"/ondervraag/leastQuestioned", "/ondervraag/leastQuestioned/nbWords/{nbWords}"})
    public String ondervraagLeastQuestionedWords(Map<String, Object> model, @PathVariable(required = false ,name="nbWords") Optional<Integer> nbWords) {
        model.put("words", ondervraagService.getWordsLeastQuestioned(wordRepository.findAll(), nbWords.orElse(15)));
        return "ondervraag";
    }

    @RequestMapping(path = {"/ondervraag/leastGoodMinusWrong", "/ondervraag/leastGoodMinusWrong/nbWords/{nbWords}"})
    public String ondervraagLeastGoodMinusWrongWords(Map<String, Object> model, @PathVariable(required = false ,name="nbWords") Optional<Integer> nbWords) {
        model.put("words", ondervraagService.getWordsSmallestGoodMinusWrong(wordRepository.findAll(), nbWords.orElse(15)));
        return "ondervraag";
    }

    @RequestMapping(path = {"/ondervraag/leastGoodDividedByWrong", "/ondervraag/leastGoodDividedByWrong/nbWords/{nbWords}"})
    public String ondervraagLeastGoodDividedByWrongWords(Map<String, Object> model, @PathVariable(required = false ,name="nbWords") Optional<Integer> nbWords) {
        model.put("words", ondervraagService.getWordsSmallestGoodDevidedByWrong(wordRepository.findAll(), nbWords.orElse(15)));
        return "ondervraag";
    }

    @RequestMapping( value = "/ondervraag/word/succes", method = RequestMethod.POST)
    public @ResponseBody String wordCorrect(@RequestBody String  response, Long id){
        Word word = ondervraagService.getWordById(id);
        word.increaseNbCorrect();
        ondervraagService.updateWord(word);
        return "OK";
    }

    @RequestMapping( value = "/ondervraag/word/failed", method = RequestMethod.POST)
    public @ResponseBody String wordIncorrect(@RequestBody String  response, Long id){
        Word word = ondervraagService.getWordById(id);
        word.increaseNbErrors();
        ondervraagService.updateWord(word);
        return "NOK";
    }
}

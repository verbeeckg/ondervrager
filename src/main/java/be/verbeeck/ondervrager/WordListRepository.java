package be.verbeeck.ondervrager;

import be.verbeeck.ondervrager.model.WordList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordListRepository extends JpaRepository<WordList, Long> {
}

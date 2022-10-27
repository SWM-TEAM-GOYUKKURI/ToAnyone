package anyone.to.soma.letter.domain;

import anyone.to.soma.letter.domain.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {

    List<Letter> findLettersByReceiverId(Long receiverId);

    List<Letter> findLettersBySenderId(Long senderId);

}

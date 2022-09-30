package anyone.to.soma.letter.domain;

import anyone.to.soma.letter.ReplyLetter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLetterRepository extends JpaRepository<ReplyLetter, Long> {
}

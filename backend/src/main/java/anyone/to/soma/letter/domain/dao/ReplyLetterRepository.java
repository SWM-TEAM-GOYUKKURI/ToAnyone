package anyone.to.soma.letter.domain.dao;

import anyone.to.soma.letter.domain.ReplyLetter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLetterRepository extends JpaRepository<ReplyLetter, Long> {
}

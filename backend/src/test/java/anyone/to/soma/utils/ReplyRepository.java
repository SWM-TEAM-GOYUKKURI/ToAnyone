package anyone.to.soma.utils;


import anyone.to.soma.letter.domain.ReplyLetter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<ReplyLetter, Long> {
}

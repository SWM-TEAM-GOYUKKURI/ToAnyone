package anyone.to.soma.letter.domain.dao;

import anyone.to.soma.letter.domain.ReplyLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyLetterRepository extends JpaRepository<ReplyLetter, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update ReplyLetter r set r.receiverRead = true where r.id in :replyLetterId")
    void updateIsRead(@Param("replyLetterId") List<Long> replyLetterId);
}

package anyone.to.soma.letter;

import anyone.to.soma.letter.domain.Letter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReplyLetter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

    private LocalDate sendDate;

    private String senderName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Letter letter;

    public ReplyLetter(String content, LocalDate sendDate, Letter letter, String senderName) {
        this(null, content, sendDate, letter, senderName);
    }

    private ReplyLetter(Long id, String content, LocalDate sendDate, Letter letter, String senderName) {
        this.id = id;
        this.content = content;
        this.sendDate = sendDate;
        this.letter = letter;
        this.senderName = senderName;
    }
}

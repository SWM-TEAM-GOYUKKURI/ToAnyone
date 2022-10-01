package anyone.to.soma.letter.domain;

import anyone.to.soma.decoration.DecorationType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReplyLetter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_letter_id")
    private Long id;

    @Lob
    private String content;

    private LocalDate sendDate;

    private String senderName;

    @ElementCollection
    @CollectionTable(
            name = "reply_letter_decoration",
            joinColumns = @JoinColumn(name = "reply_letter_id")
    )
    private List<DecorationType> decorations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Letter letter;

    public ReplyLetter(String content, LocalDate sendDate, Letter letter, String senderName, List<DecorationType> decorations) {
        this(null, content, sendDate, letter, senderName, decorations);
    }

//    public ReplyLetter(String content, LocalDate sendDate, Letter letter, String senderName) {
//        this(null, content, sendDate, letter, senderName);
//    }

    private ReplyLetter(Long id, String content, LocalDate sendDate, Letter letter, String senderName, List<DecorationType> decorations) {
        this.id = id;
        this.content = content;
        this.sendDate = sendDate;
        this.letter = letter;
        this.senderName = senderName;
        this.decorations = decorations;
    }
}
package anyone.to.soma.letter.domain;

import anyone.to.soma.decoration.DecorationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private boolean senderRead = true;
    private boolean receiverRead = false;

    private String senderName;
    private String senderImageUrl;
    private String receiverName;
    private String receiverImageUrl;

    @ElementCollection
    @CollectionTable(
            name = "reply_letter_decoration",
            joinColumns = @JoinColumn(name = "reply_letter_id")
    )
    private List<DecorationType> decorations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Letter letter;

    public ReplyLetter(String content, LocalDate sendDate, Letter letter, String senderName, String senderImageUrl, String receiverName, String receiverImageUrl, List<DecorationType> decorations) {
        this(null, content, sendDate, letter, senderName, senderImageUrl, receiverName, receiverImageUrl, decorations);
    }

    private ReplyLetter(Long id, String content, LocalDate sendDate, Letter letter, String senderName, String senderImageUrl, String receiverName, String receiverImageUrl, List<DecorationType> decorations) {
        this.id = id;
        this.content = content;
        this.sendDate = sendDate;
        this.letter = letter;
        this.senderName = senderName;
        this.senderImageUrl = senderImageUrl;
        this.decorations = decorations;
        this.receiverName = receiverName;
        this.receiverImageUrl = receiverImageUrl;
    }

    public void read() {
        this.receiverRead = true;
    }

    public boolean isReceiver(String nickName) {
        return this.receiverName.equals(nickName);
    }

    public boolean isSender(String nickname) {
        return this.senderName.equals(nickname);
    }
}

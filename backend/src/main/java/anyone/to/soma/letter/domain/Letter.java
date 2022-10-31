package anyone.to.soma.letter.domain;

import anyone.to.soma.decoration.DecorationType;
import anyone.to.soma.exception.ApplicationException;
import anyone.to.soma.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Letter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

    private LocalDate sendDate;

    private boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private User receiver;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "letter_id")
    private List<ReplyLetter> replyLetters = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "letter_id")
    private final List<LetterDecoration> letterDecorations = new ArrayList<>();

    public Letter(String content, User sender) {
        this(null, content, sender);
    }

    private Letter(Long id, String content, User sender) {
        this.id = id;
        this.content = content;
        this.sendDate = LocalDate.now();
        this.sender = sender;
        this.isRead = false;
    }

    public void send(User receiver) {
        if (this.sender.getEmail().equals(receiver.getEmail())) {
            throw new ApplicationException("자기 자신에게는 송신할 수 없습니다.");
        }
        this.receiver = receiver;
    }


    public void attachDecorations(List<DecorationType> letterDecorations) {
        letterDecorations.stream()
                .map(LetterDecoration::new)
                .forEach(this.letterDecorations::add);
    }

    public void reply(ReplyLetter replyLetter) {
        this.replyLetters.add(replyLetter);
    }

    public void read() {
        this.isRead = true;
    }

    public void checkCorrectReceiver(Long userId) {
        if (!this.receiver.getId().equals(userId)) {
            throw new ApplicationException("올바른 수신자가 아닙니다.");
        }
    }
}

package anyone.to.soma.letter.domain;

import anyone.to.soma.decoration.DecorationType;
import anyone.to.soma.exception.ApplicationException;
import anyone.to.soma.letter.domain.event.LetterCreatedEvent;
import anyone.to.soma.letter.domain.event.LetterReadEvent;
import anyone.to.soma.letter.domain.event.ReplyCreatedEvent;
import anyone.to.soma.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Letter extends AbstractAggregateRoot<Letter> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

    private LocalDateTime sendDate;

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
        this.sendDate = LocalDateTime.now();
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

    public void reply(ReplyLetter replyLetter, User replySender) {
        this.replyLetters.add(replyLetter);
        this.registerEvent(new ReplyCreatedEvent(replyLetter.getId(), replySender.getId(), replySender.getUserAchievement().getSendReplyLetterCount()));
    }

    public void read() {
        this.isRead = true;
        this.registerEvent(new LetterReadEvent(this.id, this.receiver.getId(), this.isRead));
    }

    public void checkValidReader(Long reader) {
        if (!sender.getId().equals(reader) && !receiver.getId().equals(reader)) {
            throw new ApplicationException("읽을 수 없는 사용자입니다.");
        }
    }

    public User findReplyLetterReceiver(User user) {
        if (this.sender.getId().equals(user.getId())) {
            return this.receiver;
        }

        if (this.receiver.getId().equals(user.getId())) {
            return this.sender;
        }

        throw new ApplicationException("편지를 전달할 사람이 없습니다.");
    }

    @PostPersist
    public void created() {
        this.registerEvent(new LetterCreatedEvent(this.id, this.sender.getId(), this.receiver.getId()));
    }
}

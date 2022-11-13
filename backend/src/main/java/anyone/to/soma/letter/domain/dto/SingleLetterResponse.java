package anyone.to.soma.letter.domain.dto;

import anyone.to.soma.decoration.DecorationType;
import anyone.to.soma.letter.domain.Letter;
import anyone.to.soma.letter.domain.LetterDecoration;
import anyone.to.soma.letter.domain.ReplyLetter;
import anyone.to.soma.user.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SingleLetterResponse {

    private String content;
    private LocalDateTime sendDate;
    private String receiverName;
    private String receiverImageUrl;
    private String senderName;
    private String senderImageUrl;
    private boolean isRead;
    private boolean replied;
    private List<DecorationType> decorations;
    private List<ReplyLetter> replyLetters;

    public static SingleLetterResponse of(Letter letter, User receiver, List<ReplyLetter> replyLetters) {
        List<DecorationType> letterDecorations = letter.getLetterDecorations().stream().map(LetterDecoration::getDecorationType).collect(Collectors.toList());
        User sender = letter.getSender();
        boolean replied = replyLetters.size() > 0;
        return new SingleLetterResponse(letter.getContent(), letter.getSendDate(), receiver.getNickname(), receiver.getUserImageUrl(), sender.getNickname(), sender.getUserImageUrl(), letter.isRead(), replied, letterDecorations, replyLetters);
    }
}

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
public class InboxLetterResponse {

    private static final int MAX_CONTENT_LENGTH = 101;

    private Long id;
    private String content;
    private LocalDateTime sendDate;
    private String receiverName;
    private String receiverImageUrl;
    private String senderName;
    private String senderImageUrl;
    private boolean isRead;
    private boolean replied;
    private List<DecorationType> decorations;

    public static List<InboxLetterResponse> sentLetterListOf(List<Letter> letters, User sender) {
        return letters.stream()
                .map(s -> InboxLetterResponse.sentLetterOf(s, sender))
                .collect(Collectors.toList());
    }

    private static InboxLetterResponse sentLetterOf(Letter letter, User sender) {
        List<DecorationType> letterDecorations = letter.getLetterDecorations().stream().map(LetterDecoration::getDecorationType).collect(Collectors.toList());
        User receiver = letter.getReceiver();
        boolean replied = letter.getReplyLetters().size() > 0;
        return new InboxLetterResponse(letter.getId(), contentPolicy(letter), letter.getSendDate(), receiver.getNickname(), receiver.getUserImageUrl(), sender.getNickname(), sender.getUserImageUrl(), true, replied, letterDecorations);
    }

    public static List<InboxLetterResponse> listOf(List<Letter> letters, User receiver) {
        return letters.stream()
                .map(s -> InboxLetterResponse.of(s, receiver))
                .collect(Collectors.toList());
    }

    private static InboxLetterResponse of(Letter letter, User receiver) {
        List<DecorationType> letterDecorations = letter.getLetterDecorations().stream().map(LetterDecoration::getDecorationType).collect(Collectors.toList());
        User sender = letter.getSender();
        boolean replied = letter.getReplyLetters().size() > 0;
        return new InboxLetterResponse(letter.getId(), contentPolicy(letter), letter.getSendDate(), receiver.getNickname(), receiver.getUserImageUrl(), sender.getNickname(), sender.getUserImageUrl(),readPolicy(letter), replied, letterDecorations);
    }


    private static boolean readPolicy(Letter letter) {
        List<ReplyLetter> replyLetters = letter.getReplyLetters();
        if (replyLetters.isEmpty()) {
            return letter.isRead();
        }

        replyLetters.sort((a, b) -> b.getId().compareTo(a.getId()));
        return replyLetters.get(0).isRead();

    }

    private static String contentPolicy(Letter letter) {
        List<ReplyLetter> replyLetters = letter.getReplyLetters();
        if (replyLetters.isEmpty()) {
            String content = letter.getContent();
            return content.substring(0, Math.min(content.length(), MAX_CONTENT_LENGTH));
        }

        replyLetters.sort((a, b) -> b.getId().compareTo(a.getId()));
        String replyContent = replyLetters.get(0).getContent();
        return replyContent.substring(0, Math.min(replyContent.length(), MAX_CONTENT_LENGTH));
    }
}

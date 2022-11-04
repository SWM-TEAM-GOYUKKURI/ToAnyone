package anyone.to.soma.letter.domain.dto;

import anyone.to.soma.decoration.DecorationType;
import anyone.to.soma.letter.domain.Letter;
import anyone.to.soma.letter.domain.LetterDecoration;
import anyone.to.soma.letter.domain.ReplyLetter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class InboxLetterResponse {

    private static final int MAX_CONTENT_LENGTH = 101;

    private Long id;
    private String content;
    private LocalDate sendDate;
    private String receiverName;
    private String senderName;
    private boolean isRead;
    private List<DecorationType> decorations;

    public static List<InboxLetterResponse> sentLetterListOf(List<Letter> letters, String senderName) {
        return letters.stream()
                .map(s -> InboxLetterResponse.sentLetterOf(s, senderName))
                .collect(Collectors.toList());
    }

    private static InboxLetterResponse sentLetterOf(Letter letter, String senderName) {
        List<DecorationType> letterDecorations = letter.getLetterDecorations().stream().map(LetterDecoration::getDecorationType).collect(Collectors.toList());
        String content = letter.getContent();
        return new InboxLetterResponse(letter.getId(), content.substring(0, Math.min(content.length(), MAX_CONTENT_LENGTH)), letter.getSendDate(), letter.getReceiver().getNickname(), senderName, true, letterDecorations);
    }

    public static List<InboxLetterResponse> listOf(List<Letter> letters, String receiverName) {
        return letters.stream()
                .map(s -> InboxLetterResponse.of(s, receiverName))
                .collect(Collectors.toList());
    }

    private static InboxLetterResponse of(Letter letter, String receiverName) {
        List<DecorationType> letterDecorations = letter.getLetterDecorations().stream().map(LetterDecoration::getDecorationType).collect(Collectors.toList());
        String content = letter.getContent();
        return new InboxLetterResponse(letter.getId(), content.substring(0, Math.min(content.length(), MAX_CONTENT_LENGTH)), letter.getSendDate(), receiverName, letter.getSender().getNickname(), readPolicy(letter), letterDecorations);
    }


    private static boolean readPolicy(Letter letter) {
        List<ReplyLetter> replyLetters = letter.getReplyLetters();
        if (replyLetters.isEmpty()) {
            return letter.isRead();
        }

        replyLetters.sort((a, b) -> b.getId().compareTo(a.getId()));
        return replyLetters.get(0).isRead();
    }
}

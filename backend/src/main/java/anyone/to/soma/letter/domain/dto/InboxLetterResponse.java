package anyone.to.soma.letter.domain.dto;

import anyone.to.soma.decoration.DecorationType;
import anyone.to.soma.letter.domain.Letter;
import anyone.to.soma.letter.domain.LetterDecoration;
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

    private Long id;
    private String content;
    private LocalDate sendDate;
    private String receiverName;
    private List<DecorationType> decorations;

    public static InboxLetterResponse of(Letter letter, String receiverName) {
        List<DecorationType> letterDecorations = letter.getLetterDecorations().stream().map(LetterDecoration::getDecorationType).collect(Collectors.toList());
        return new InboxLetterResponse(letter.getId(), letter.getContent(), letter.getSendDate(), receiverName, letterDecorations);
    }

    public static List<InboxLetterResponse> listOf(List<Letter> letters, String receiverName) {
        return letters.stream()
                .map(s -> InboxLetterResponse.of(s, receiverName))
                .collect(Collectors.toList());
    }
}

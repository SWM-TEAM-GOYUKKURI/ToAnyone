package anyone.to.soma.letter.dto;

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
    private List<LetterDecoration> decorations;

    public static InboxLetterResponse of(Letter letter, String receiverName) {
        return new InboxLetterResponse(letter.getId(), letter.getContent(), letter.getSendDate(), receiverName, letter.getLetterDecorations());
    }

    public static List<InboxLetterResponse> listOf(List<Letter> letters, String receiverName) {
        return letters.stream()
                .map(s -> InboxLetterResponse.of(s, receiverName))
                .collect(Collectors.toList());
    }
}

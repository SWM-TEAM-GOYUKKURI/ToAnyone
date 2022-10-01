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
public class SingleLetterResponse {

    private String content;
    private LocalDate sendDate;
    private String receiverName;
    private List<DecorationType> decorations;
    private List<ReplyLetter> replyLetters;


    public static SingleLetterResponse of(Letter letter, String receiverName, List<ReplyLetter> replyLetters) {
        List<DecorationType> letterDecorations = letter.getLetterDecorations().stream().map(LetterDecoration::getDecorationType).collect(Collectors.toList());
        return new SingleLetterResponse(letter.getContent(), letter.getSendDate(), receiverName, letterDecorations, replyLetters);
    }
}
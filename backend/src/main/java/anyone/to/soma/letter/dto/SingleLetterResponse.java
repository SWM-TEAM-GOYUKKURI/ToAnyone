package anyone.to.soma.letter.dto;

import anyone.to.soma.letter.domain.LetterDecoration;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SingleLetterResponse {

    private String content;
    private LocalDate sendDate;
    private String receiverName;
    private List<LetterDecoration> decorations;



}

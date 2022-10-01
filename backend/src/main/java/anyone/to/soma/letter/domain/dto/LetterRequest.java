package anyone.to.soma.letter.domain.dto;

import anyone.to.soma.decoration.DecorationType;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class LetterRequest {
    @NotNull
    private String content;

    private List<DecorationType> decorations;

    public LetterRequest(String content, List<DecorationType> decorations) {
        validateRequest(content);
        this.content = content;
        this.decorations = decorations;
    }

    private void validateRequest(String content) {
        if (content.isBlank()) {
            throw new IllegalArgumentException();
        }
    }
}
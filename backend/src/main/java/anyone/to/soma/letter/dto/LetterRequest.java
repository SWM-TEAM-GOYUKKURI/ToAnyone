package anyone.to.soma.letter.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LetterRequest {
    @NotNull
    private String content;

    public LetterRequest(String content) {
        validateRequest(content);
        this.content = content;
    }

    private void validateRequest(String content) {
        if (content.isBlank()) {
            throw new IllegalArgumentException();
        }
    }
}
package anyone.to.soma.letter.domain.dto;

import anyone.to.soma.decoration.DecorationType;
import anyone.to.soma.exception.ApplicationException;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Getter
public class ReplyLetterRequest {
    @NotNull
    private String content;


    public ReplyLetterRequest(String content, List<DecorationType> decorations) {
        validateRequest(content);
        this.content = content;
    }

    private void validateRequest(String content) {
        if (Objects.isNull(content) || content.isBlank()) {
            throw new ApplicationException("내용을 입력해주세요.");
        }
    }
}
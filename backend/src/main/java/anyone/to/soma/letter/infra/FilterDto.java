package anyone.to.soma.letter.infra;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class FilterDto {

    private String content;

    public FilterDto(String content) {
        this.content = content;
    }




}

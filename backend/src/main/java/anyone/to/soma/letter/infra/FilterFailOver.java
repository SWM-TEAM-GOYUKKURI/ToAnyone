package anyone.to.soma.letter.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FilterFailOver {

    @Value("${filter.abuse:욕설}")
    private String[] abuses;

    public FilterDto filter(FilterDto filterDto) {
        String content = filterDto.getContent();
        for (String abuse : abuses) {
            if (content.contains(abuse)) {
                content = content.replaceAll(abuse, "****");
            }
        }
        return new FilterDto(content);
    }
}

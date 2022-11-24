package anyone.to.soma.letter.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class FilterApiClient {

    private final FilterFailOver filterFailOver;

    public FilterDto filter(FilterDto filterDto) {
        return WebClient.create("http://localhost:5000/predict")
                .post()
                .bodyValue(filterDto)
                .retrieve()
                .bodyToMono(FilterDto.class)
                .onErrorReturn(filterFailOver.filter(filterDto))
                .block();
    }
}

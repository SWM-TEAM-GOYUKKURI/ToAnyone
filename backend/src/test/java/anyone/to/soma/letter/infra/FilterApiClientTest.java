package anyone.to.soma.letter.infra;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@Profile("test")
class FilterApiClientTest {

    @Autowired
    private FilterApiClient filterApiClient;

    @Test
    void test() {
        FilterDto filterDto = new FilterDto("안녕하세요 욕설입니다.");
        FilterDto filter = filterApiClient.filter(filterDto);
        System.out.println(filter.getContent());
    }

}
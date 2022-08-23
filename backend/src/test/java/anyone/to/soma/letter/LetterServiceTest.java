package anyone.to.soma.letter;

import anyone.to.soma.base.IntegrationTest;
import anyone.to.soma.letter.dto.LetterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class LetterServiceTest extends IntegrationTest {


    @Autowired
    private LetterService letterService;
    private final LetterRequest letterRequest = new LetterRequest("content");

    @Autowired
    private LetterRepository letterRepository;

    @Test
    void writeLetter() {
        // when
        Long letterId = letterService.writeLetter(letterRequest, user1);
        Letter letter = letterRepository.findById(letterId).orElseThrow(IllegalArgumentException::new);
        assertThat(letterRequest.getContent()).isEqualTo(letter.getContent());
    }


}
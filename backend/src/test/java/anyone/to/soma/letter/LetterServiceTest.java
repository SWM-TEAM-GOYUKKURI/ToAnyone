package anyone.to.soma.letter;

import anyone.to.soma.IntegrationTest;
import anyone.to.soma.decoration.DecorationRepository;
import anyone.to.soma.exception.ApplicationException;
import anyone.to.soma.letter.application.LetterService;
import anyone.to.soma.letter.domain.Letter;
import anyone.to.soma.letter.domain.LetterRepository;
import anyone.to.soma.letter.domain.dto.InboxLetterResponse;
import anyone.to.soma.letter.domain.dto.LetterRequest;
import anyone.to.soma.user.User;
import anyone.to.soma.user.UserRepository;
import anyone.to.soma.utils.LetterDecorationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LetterServiceTest extends IntegrationTest {

    @Autowired
    private LetterService letterService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DecorationRepository decorationRepository;

    @Autowired
    private LetterDecorationRepository letterDecorationRepository;

    @Autowired
    private LetterRepository letterRepository;

    private final LetterRequest letterRequest = new LetterRequest("content", List.of());


    @Test
    void writeLetter() {
        // when
        Long letterId = letterService.writeLetter(letterRequest, user1);
        Letter letter = letterRepository.findById(letterId).orElseThrow(IllegalArgumentException::new);
        assertThat(letterRequest.getContent()).isEqualTo(letter.getContent());
    }

    @Test
    void noReceiver() {
        User user = userRepository.save(new User("eungi6850@gmail.com", "eungi"));
        assertThatThrownBy(() -> letterService.writeLetter(letterRequest, user)).isInstanceOf(ApplicationException.class);
    }

    @Test
    void retrieveInboxLetterTest() {
        Letter letter = new Letter("content", user);
        letter.send(user1);
        Long id = letterRepository.save(letter).getId();
        InboxLetterResponse inboxLetterResponse = letterService.retrieveInboxSingleLetter(id, user1.getId());
        assertThat(inboxLetterResponse.getReceiverName()).isEqualTo(user1.getName());
        assertThat(inboxLetterResponse.getContent()).isEqualTo(letter.getContent());
    }

    @Test
    void retrieveInboxAllLettersTest() {
        for (int i = 0; i < 3; i++) {
            Letter letter = new Letter("content", user);
            letter.send(user1);
            letterRepository.save(letter);
        }
        List<InboxLetterResponse> inboxLetterResponses = letterService.retrieveInboxAllLetters(user1.getId());
        assertThat(inboxLetterResponses.size()).isEqualTo(3);
    }

    @AfterEach
    void tearDown() {
        letterDecorationRepository.deleteAllInBatch();
        decorationRepository.deleteAllInBatch();
        letterRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }


}
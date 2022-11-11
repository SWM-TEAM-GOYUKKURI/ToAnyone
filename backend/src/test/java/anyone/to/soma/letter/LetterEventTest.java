package anyone.to.soma.letter;

import anyone.to.soma.letter.application.LetterService;
import anyone.to.soma.letter.domain.dto.LetterRequest;
import anyone.to.soma.letter.domain.event.LetterCreatedEvent;
import anyone.to.soma.user.domain.User;
import anyone.to.soma.user.domain.dao.UserRepository;
import anyone.to.soma.utils.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RecordApplicationEvents
@SpringBootTest
@ActiveProfiles("test")
public class LetterEventTest {

    @Autowired
    private ApplicationEvents applicationEvents;

    @Autowired
    private LetterService letterService;

    @Autowired
    private UserRepository userRepository;

    private User sender;
    private User receiver;


    @BeforeEach
    void setup(){
        sender = userRepository.save(Fixtures.UserStub.defaultGoogleUser("gmail@gmail.com"));
        receiver = userRepository.save(Fixtures.UserStub.defaultGoogleUser("receiver@gmail.com"));
    }


    @Test
    void letter_created_event_test(){
        letterService.writeLetter(new LetterRequest("content", List.of()), sender);
        letterService.writeLetter(new LetterRequest("content", List.of()), sender);
        assertThat(applicationEvents.stream(LetterCreatedEvent.class).count()).isEqualTo(2);
    }
}

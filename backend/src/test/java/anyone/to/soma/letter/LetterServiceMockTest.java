package anyone.to.soma.letter;

import anyone.to.soma.letter.application.LetterService;
import anyone.to.soma.letter.domain.Letter;
import anyone.to.soma.letter.domain.LetterRepository;
import anyone.to.soma.letter.domain.dto.LetterRequest;
import anyone.to.soma.user.domain.dao.UserRepository;
import anyone.to.soma.utils.Fixtures;
import anyone.to.soma.utils.Fixtures.UserStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LetterServiceMockTest {

    @Mock
    private LetterRepository letterRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LetterService letterService;

    @Test
    void write_Letter() {
        LetterRequest request = new LetterRequest("content", List.of());
        Letter letter = Fixtures.LetterStub.defaultLetterWithReceiver(request.getContent());

        when(userRepository.findUsersByMinReceiveCount(any())).thenReturn(List.of(UserStub.defaultGoogleUser("receiver@gmail.com")));
        when(letterRepository.save(any(Letter.class))).thenReturn(letter);

        Long letterId = letterService.writeLetter(request, letter.getSender());

        assertEquals(letterId, letter.getId());
    }
}

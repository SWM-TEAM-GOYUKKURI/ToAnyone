package anyone.to.soma;

import anyone.to.soma.exception.repository.NoSuchRecordException;
import anyone.to.soma.letter.Letter;
import anyone.to.soma.letter.LetterRepository;
import anyone.to.soma.letter.LetterService;
import anyone.to.soma.letter.dto.InboxLetterResponse;
import anyone.to.soma.letter.dto.LetterRequest;
import anyone.to.soma.user.User;
import anyone.to.soma.user.UserRepository;
import io.swagger.models.auth.In;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@Profile("local | dev")
@RestController
@RequiredArgsConstructor
public class DevController {

    private final UserRepository userRepository;
    private final LetterService letterService;
    private final LetterRepository letterRepository;


    @GetMapping("/dev/user")
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @GetMapping("/dev/letter")
    public List<InboxLetterResponse> findAllLetter() {
        return letterService.retrieveInboxAllLetters(2L);
    }

    @PostMapping("/dev/letter")
    public ResponseEntity<Void> writeLetterToSomeUser(@RequestBody SpecificLetter request) {
        User sender = userRepository.findUserByEmail(request.getSenderEmail()).orElseThrow(() -> new NoSuchRecordException("존재하지 않는 사용자입니다."));
        User receiver = userRepository.findUserByEmail(request.getReceiverEmail()).orElseThrow(() -> new NoSuchRecordException("존재하지 않는 사용자입니다."));

        Letter letter = new Letter(request.getContent(), sender);
        letter.send(receiver);
        Long letterId = letterRepository.save(letter).getId();
        return ResponseEntity.created(URI.create("/letter/inbox/" + letterId)).build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PUBLIC)
    static class SpecificLetter {
        private String content;
        private String senderEmail;
        private String receiverEmail;

        public SpecificLetter(String content, String senderEmail, String receiverEmail) {
            this.content = content;
            this.senderEmail = senderEmail;
            this.receiverEmail = receiverEmail;
        }
    }
}

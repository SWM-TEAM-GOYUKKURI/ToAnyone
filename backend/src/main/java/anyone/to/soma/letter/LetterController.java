package anyone.to.soma.letter;

import anyone.to.soma.config.annotation.LoginRequired;
import anyone.to.soma.config.annotation.LoginUser;
import anyone.to.soma.letter.dto.InboxLetterResponse;
import anyone.to.soma.letter.dto.LetterRequest;
import anyone.to.soma.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/letter")
public class LetterController {

    private final LetterService letterService;

    @PostMapping("")
    @LoginRequired
    public ResponseEntity<Void> writeLetterToAnonymous(@RequestBody LetterRequest request, @LoginUser User user) {
        Long letterId = letterService.writeLetter(request, user);
        return ResponseEntity.created(URI.create("/letter/" + letterId)).build();
    }

    @GetMapping("/inbox")
    @LoginRequired
    public ResponseEntity<List<InboxLetterResponse>> retrieveInboxAllLetters(@LoginUser User user) {
        List<InboxLetterResponse> inboxLetterResponses = letterService.retrieveInboxAllLetters(user.getId());
        return ResponseEntity.ok(inboxLetterResponses);
    }

    @GetMapping("/inbox/{id}")
    @LoginRequired
    public ResponseEntity<InboxLetterResponse> retrieveInboxLetter(@PathVariable Long id, @LoginUser User user) {
        InboxLetterResponse inboxLetterResponses = letterService.retrieveInboxSingleLetter(id, user.getId());
        return ResponseEntity.ok(inboxLetterResponses);
    }

}

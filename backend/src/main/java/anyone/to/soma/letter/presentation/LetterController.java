package anyone.to.soma.letter.presentation;

import anyone.to.soma.config.annotation.LoginRequired;
import anyone.to.soma.config.annotation.LoginUser;
import anyone.to.soma.letter.application.LetterService;
import anyone.to.soma.letter.domain.dto.InboxLetterResponse;
import anyone.to.soma.letter.domain.dto.LetterRequest;
import anyone.to.soma.letter.domain.dto.SingleLetterResponse;
import anyone.to.soma.user.domain.User;
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

    @GetMapping("/sent")
    @LoginRequired
    public ResponseEntity<List<InboxLetterResponse>> retrieveSentLetters(@LoginUser User user) {
        List<InboxLetterResponse> inboxLetterResponses = letterService.retrieveSentLetters(user);
        return ResponseEntity.ok(inboxLetterResponses);
    }

    @GetMapping("/inbox/{id}")
    @LoginRequired
    public ResponseEntity<SingleLetterResponse> retrieveInboxLetter(@PathVariable Long id, @LoginUser User user) {
        SingleLetterResponse letterResponse = letterService.retrieveInboxSingleLetter(id, user.getId());
        return ResponseEntity.ok(letterResponse);
    }

    @PostMapping("/inbox/{id}")
    @LoginRequired
    public ResponseEntity<Void> replyLetter(
            @PathVariable Long id,
            @RequestBody LetterRequest request,
            @LoginUser User user) {
        letterService.writeReplyLetter(id, request, user);
        return ResponseEntity.created(URI.create("/inbox/" + id)).build();
    }

}

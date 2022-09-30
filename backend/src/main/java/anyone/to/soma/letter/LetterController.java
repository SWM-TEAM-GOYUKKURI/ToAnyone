package anyone.to.soma.letter;

import anyone.to.soma.config.annotation.LoginRequired;
import anyone.to.soma.config.annotation.LoginUser;
import anyone.to.soma.letter.dto.InboxLetterResponse;
import anyone.to.soma.letter.dto.LetterRequest;
import anyone.to.soma.letter.dto.ReplyLetterRequest;
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
        // TODO Response가 replyLetter을 포함하도록 수정
        InboxLetterResponse inboxLetterResponses = letterService.retrieveInboxSingleLetter(id, user.getId());
        return ResponseEntity.ok(inboxLetterResponses);
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

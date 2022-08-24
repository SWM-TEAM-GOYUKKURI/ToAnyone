package anyone.to.soma.letter;

import anyone.to.soma.letter.dto.LetterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/letter")
public class LetterController {

    private final LetterService letterService;

//    @PostMapping("")
//    public ResponseEntity<Void> writeLetterToAnonymous(@RequestBody LetterRequest request){
//        letterService.writeLetter(request, )
//    }


}

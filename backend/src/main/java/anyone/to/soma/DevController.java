package anyone.to.soma;

import anyone.to.soma.letter.Letter;
import anyone.to.soma.letter.LetterRepository;
import anyone.to.soma.user.User;
import anyone.to.soma.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Profile("local | dev")
@RestController
@RequiredArgsConstructor
public class DevController {

    private final UserRepository userRepository;
    private final LetterRepository letterRepository;


    @GetMapping("/dev/user")
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @GetMapping("/dev/letter")
    public List<Letter> findAllLetter() {
        return letterRepository.findAll();
    }


}

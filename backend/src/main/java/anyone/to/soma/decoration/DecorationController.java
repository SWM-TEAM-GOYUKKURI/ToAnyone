package anyone.to.soma.decoration;

import anyone.to.soma.config.annotation.LoginRequired;
import anyone.to.soma.config.annotation.LoginUser;
import anyone.to.soma.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DecorationController {


    @LoginRequired
    @GetMapping("/decorations")
    public List<DecorationType> findDecorationsOfUser(@LoginUser User user) {
        return List.of(DecorationType.FONT, DecorationType.LETTER_PAPER, DecorationType.STAMP);
    }

}

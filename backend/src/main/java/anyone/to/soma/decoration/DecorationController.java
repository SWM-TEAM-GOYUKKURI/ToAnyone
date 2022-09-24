package anyone.to.soma.decoration;

import anyone.to.soma.config.annotation.LoginRequired;
import anyone.to.soma.config.annotation.LoginUser;
import anyone.to.soma.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DecorationController {

    private final DecorationService decorationService;

    @RequestMapping("/decorations")
    @LoginRequired
    public List<Decoration> findDecorationsOfUser(@LoginUser User user){
        return decorationService.findDecorationsOfUser(user.getId());
    }

}

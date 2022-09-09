package anyone.to.soma.user;

import anyone.to.soma.user.dto.LoginResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login/google")
    public ResponseEntity<LoginResponse> doGoogleLogin(HttpServletRequest request) {
        String token = request.getHeader("credential");
        if (token.isBlank()) {
            throw new IllegalArgumentException();
        }
        return ResponseEntity.ok(userService.signInGoogleAuthUser(token));
    }


}

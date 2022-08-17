package anyone.to.soma.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login/google")
    public ResponseEntity<String> doGoogleLogin(HttpServletRequest request) {
        String token = request.getHeader("credential");
        if (token.isBlank()) {
            throw new IllegalArgumentException();
        }
        return ResponseEntity.ok(userService.signInUser(token));
    }
}

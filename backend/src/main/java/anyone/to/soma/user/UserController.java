package anyone.to.soma.user;

import anyone.to.soma.config.annotation.LoginRequired;
import anyone.to.soma.config.annotation.LoginUser;
import anyone.to.soma.user.domain.User;
import anyone.to.soma.user.dto.LoginResponse;
import anyone.to.soma.user.dto.ProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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


    @PutMapping("/user")
    @LoginRequired
    public ResponseEntity<Void> updateUserProfile(
            @LoginUser User user,
            @RequestBody ProfileRequest request
    ) {
        userService.updateUserProfile(user, request);
        return ResponseEntity.ok().build();
    }


}

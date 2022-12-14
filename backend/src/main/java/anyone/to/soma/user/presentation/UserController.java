package anyone.to.soma.user.presentation;

import anyone.to.soma.config.annotation.LoginRequired;
import anyone.to.soma.config.annotation.LoginUser;
import anyone.to.soma.user.application.UserService;
import anyone.to.soma.user.domain.Achievement;
import anyone.to.soma.user.domain.User;
import anyone.to.soma.user.domain.UserItem;
import anyone.to.soma.user.domain.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PutMapping("/user")
    @LoginRequired
    public ResponseEntity<Void> updateUserProfile(
            @LoginUser User user,
            @RequestBody ProfileRequest request
    ) {
        userService.updateUserProfile(user, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/me")
    @LoginRequired
    public ResponseEntity<User> retrieveUserProfileData(@LoginUser User user) {
        userService.retrieveUserData(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("user")
    @LoginRequired
    public ResponseEntity<Void> deleteUser(@LoginUser User user) {
        userService.deleteUser(user);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/user/me/achievement")
    @LoginRequired
    public ResponseEntity<List<Achievement>> retrieveUserAchievement(@LoginUser User user) {
        List<Achievement> achievements = userService.retrieveUserAchievement(user);
        return ResponseEntity.ok(achievements);
    }

    @PutMapping("/user/me/image")
    @LoginRequired
    public ResponseEntity<Void> updateUserProfileImage(
            @LoginUser User user,
            @RequestBody ImageUrlRequest request
    ) {
        userService.updateUserImage(user, request.getUserImageUrl());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/me/items")
    @LoginRequired
    public ResponseEntity<List<UserItem>> retrieveUserItems(@LoginUser User user) {
        List<UserItem> items = userService.retrieveUserItems(user.getId());
        return ResponseEntity.ok(items);
    }

    @PostMapping("/items")
    @LoginRequired
    public ResponseEntity<PurchaseResponse> purchaseItem(
            @LoginUser User user,
            @RequestBody ItemRequest request
    ) {
        Long point = userService.purchaseItem(user, request);
        return ResponseEntity.ok(new PurchaseResponse(point));
    }

}

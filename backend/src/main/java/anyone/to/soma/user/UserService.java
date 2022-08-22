package anyone.to.soma.user;

import anyone.to.soma.auth.JWTProvider;
import anyone.to.soma.user.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;

    @Transactional
    public LoginResponse signInUser(String token) {
        User decodedUser = jwtProvider.googleOAuthJwtToUser(token);
        String id = decodedUser.getId();

        if (!userRepository.existsById(id)) {
            id = userRepository.save(decodedUser).getId();
        }

        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        String accessToken = jwtProvider.createAccessToken(user.getEmail());
        return new LoginResponse(user.getName(), user.getEmail(), accessToken);
    }


}

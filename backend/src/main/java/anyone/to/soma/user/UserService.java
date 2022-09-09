package anyone.to.soma.user;

import anyone.to.soma.auth.JWTProvider;
import anyone.to.soma.exception.repository.NoSuchRecordException;
import anyone.to.soma.user.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    public static final String EMAIL = "email";
    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;

    @Transactional
    public LoginResponse signInGoogleAuthUser(String token) {
        User decodedUser = jwtProvider.googleOAuthJwtToUser(token);
        String uniqueId = decodedUser.getUniqueId();

        if (!userRepository.existsUserByUniqueId(uniqueId)) {
            uniqueId = userRepository.save(decodedUser).getUniqueId();
        }

        User user = userRepository.findUserByUniqueId(uniqueId).orElseThrow(IllegalArgumentException::new);
        String accessToken = jwtProvider.createAccessToken(user.getEmail());
        return new LoginResponse(user.getName(), user.getEmail(), accessToken);
    }

    public User loginUser(String token){
        String email = jwtProvider.decodeJWT(token).getClaim(EMAIL).asString();
        return userRepository.findUserByEmail(email).orElseThrow(NoSuchRecordException::new);
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }
}

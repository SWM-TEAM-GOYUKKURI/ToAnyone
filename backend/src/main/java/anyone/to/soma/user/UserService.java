package anyone.to.soma.user;

import anyone.to.soma.auth.JWTProvider;
import anyone.to.soma.exception.repository.NoSuchRecordException;
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
        String uniqueId = jwtProvider.googleOAuthJwtToUser(token).getUniqueId();
        return userRepository.findUserByUniqueId(uniqueId).orElseThrow(NoSuchRecordException::new);
    }


}

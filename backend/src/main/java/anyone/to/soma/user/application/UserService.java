package anyone.to.soma.user.application;

import anyone.to.soma.auth.JWTProvider;
import anyone.to.soma.exception.repository.NoSuchRecordException;
import anyone.to.soma.user.domain.Profile;
import anyone.to.soma.user.domain.User;
import anyone.to.soma.user.domain.UserRepository;
import anyone.to.soma.user.dto.LoginResponse;
import anyone.to.soma.user.dto.ProfileRequest;
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
        return new LoginResponse(user.getName(), user.getEmail(), accessToken, user.isRegistrationFormFilled());
    }

    public User loginUser(String token) {
        String email = jwtProvider.decodeJWTToSubject(token);
        return userRepository.findUserByEmail(email).orElseThrow(NoSuchRecordException::new);
    }

    @Transactional
    public void updateUserProfile(User user, ProfileRequest request) {
        Profile profile = new Profile(request.getNickname(), request.getGender(), request.getAge(), request.getJob(), user);
        profile.addPsychologicalExam(request.getPsychologicalExams());
        user.updateProfile(profile);
    }
}

package anyone.to.soma.user.application;

import anyone.to.soma.auth.JWTProvider;
import anyone.to.soma.exception.repository.NoSuchRecordException;
import anyone.to.soma.user.domain.Achievement;
import anyone.to.soma.user.domain.Profile;
import anyone.to.soma.user.domain.User;
import anyone.to.soma.user.domain.dao.AchievementRepository;
import anyone.to.soma.user.domain.dao.UserRepository;
import anyone.to.soma.user.domain.dto.LoginResponse;
import anyone.to.soma.user.domain.dto.ProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;
    private final JWTProvider jwtProvider;

    @Transactional
    public LoginResponse signInGoogleAuthUser(String token) {
        User decodedUser = jwtProvider.googleOAuthJwtToUser(token);
        String uniqueId = decodedUser.getUniqueId();

        if (!userRepository.existsUserByUniqueId(uniqueId)) {
            uniqueId = userRepository.save(decodedUser).getUniqueId();
        }

        User user = userRepository.findUserByUniqueId(uniqueId).orElseThrow(IllegalArgumentException::new);
        user.recordLastLogin();
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
    @Transactional(readOnly = true)
    public User retrieveUserData(User user) {
        User foundUser = userRepository.findById(user.getId()).orElseThrow(NoSuchRecordException::new);
        return foundUser;
    }

    @Transactional(readOnly = true)
    public List<Achievement> retrieveUserAchievement(User user) {
        User foundUser = userRepository.findById(user.getId()).orElseThrow(NoSuchRecordException::new);
        List<Achievement> userAchievments = achievementRepository.findAllByUserId(foundUser.getId());
        return userAchievments;
    }

    @Transactional
    public void updateUserImage(User user, String imageUrl) {
        User foundUser = userRepository.findById(user.getId()).orElseThrow(NoSuchRecordException::new);
        foundUser.updateImage(imageUrl);
    }

    @Transactional
    public void deleteUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new NoSuchRecordException();
        }
        userRepository.delete(user);
    }
}


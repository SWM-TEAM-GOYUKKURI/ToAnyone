package anyone.to.soma.user;

import anyone.to.soma.auth.JWTProvider;
import anyone.to.soma.user.application.UserService;
import anyone.to.soma.user.domain.PsychologicalExam;
import anyone.to.soma.user.domain.User;
import anyone.to.soma.user.domain.dao.UserRepository;
import anyone.to.soma.user.domain.type.Age;
import anyone.to.soma.user.domain.type.Gender;
import anyone.to.soma.user.domain.type.Job;
import anyone.to.soma.user.domain.type.LoginType;
import anyone.to.soma.user.domain.dto.LoginResponse;
import anyone.to.soma.user.domain.dto.ProfileRequest;
import anyone.to.soma.utils.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceMockTest {

    private static final String ACCESS_TOKEN = "accessToken";
    private static final String EMAIL = "swm.team.goyukkuri@gmail.com";
    private static final String TOKEN = "token";

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTProvider jwtProvider;

    @InjectMocks
    private UserService userService;

    private User googleUser;

    @BeforeEach
    void setup() {
        googleUser = Fixtures.UserStub.defaultGoogleUser(EMAIL);
    }

    @Test
    void user_google_login_test() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(googleUser));
        when(jwtProvider.decodeJWTToSubject(TOKEN)).thenReturn(EMAIL);

        User user = userService.loginUser(TOKEN);

        assertThat(user.getEmail()).isEqualTo(EMAIL);
        verify(userRepository, times(1)).findUserByEmail(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void user_google_login_in_test() {
        when(jwtProvider.googleOAuthJwtToUser(TOKEN)).thenReturn(googleUser);
        when(userRepository.existsUserByUniqueId(googleUser.getUniqueId())).thenReturn(true);
        when(userRepository.findUserByUniqueId(googleUser.getUniqueId())).thenReturn(Optional.of(googleUser));
        when(jwtProvider.createAccessToken(googleUser.getEmail())).thenReturn(ACCESS_TOKEN);

        LoginResponse loginResponse = userService.signInGoogleAuthUser(TOKEN);

        assertAll(
                () -> assertThat(loginResponse.getToken()).isEqualTo(ACCESS_TOKEN),
                () -> assertThat(loginResponse.getEmail()).isEqualTo(EMAIL)
        );
        verify(userRepository, times(1)).existsUserByUniqueId(anyString());
        verify(userRepository, times(1)).findUserByUniqueId(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void user_sign_in_test() {
        when(jwtProvider.googleOAuthJwtToUser(TOKEN)).thenReturn(googleUser);
        when(userRepository.existsUserByUniqueId(googleUser.getUniqueId())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(googleUser);
        when(userRepository.findUserByUniqueId(googleUser.getUniqueId())).thenReturn(Optional.of(googleUser));

        when(jwtProvider.createAccessToken(googleUser.getEmail())).thenReturn(ACCESS_TOKEN);

        LoginResponse loginResponse = userService.signInGoogleAuthUser(TOKEN);

        assertAll(
                () -> assertThat(loginResponse.getToken()).isEqualTo(ACCESS_TOKEN),
                () -> assertThat(loginResponse.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(loginResponse.getName()).isEqualTo(googleUser.getName())
        );
        verify(userRepository, times(1)).existsUserByUniqueId(anyString());
        verify(userRepository, times(1)).findUserByUniqueId(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }


    @Test
    void update_user_profile_test() {
        User user = new User(EMAIL, "name", LoginType.GOOGLE, "uniqueId");
        List<PsychologicalExam> psychologicalExams = List.of(new PsychologicalExam(1L, 1L), new PsychologicalExam(2L, 2L));
        ProfileRequest request = new ProfileRequest("nickName", Gender.MALE, Age.TEN, Job.STUDENT, psychologicalExams);

        userService.updateUserProfile(user, request);

        assertAll(
                () -> assertThat(user.getProfile().getNickname()).isEqualTo(request.getNickname()),
                () -> assertThat(user.getProfile().getGender()).isEqualTo(request.getGender()),
                () -> assertThat(user.getProfile().getAge()).isEqualTo(request.getAge()),
                () -> assertThat(user.getProfile().getJob()).isEqualTo(request.getJob())
        );

    }


}

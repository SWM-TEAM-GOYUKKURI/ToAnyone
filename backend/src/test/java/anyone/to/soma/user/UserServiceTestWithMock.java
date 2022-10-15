package anyone.to.soma.user;

import anyone.to.soma.auth.JWTProvider;
import anyone.to.soma.user.domain.PsychologicalExam;
import anyone.to.soma.user.domain.User;
import anyone.to.soma.user.domain.UserRepository;
import anyone.to.soma.user.domain.type.Age;
import anyone.to.soma.user.domain.type.Gender;
import anyone.to.soma.user.domain.type.Job;
import anyone.to.soma.user.dto.LoginResponse;
import anyone.to.soma.user.dto.ProfileRequest;
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
public class UserServiceTestWithMock {

    private static final String JWT = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjE3MjdiNmI0OTQwMmI5Y2Y5NWJlNGU4ZmQzOGFhN2U3YzExNjQ0YjEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJuYmYiOjE2NjA2MzUxNDksImF1ZCI6IjY3NDE3OTgwNzQ2MS01Nms2MW9vOWkzaTVlb2cyZmNpbWo2czFuNDhtc2xiYS5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjEwMzk4NDYxNTk5OTY2OTAxOTA2NiIsImVtYWlsIjoic3dtLnRlYW0uZ295dWtrdXJpQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhenAiOiI2NzQxNzk4MDc0NjEtNTZrNjFvbzlpM2k1ZW9nMmZjaW1qNnMxbjQ4bXNsYmEuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJuYW1lIjoiVEVBTSBHT1lVS0tVUkkiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUl0YnZta2xaNXJhSExlN2N4OUJtZXpJX0xHMkk5SzBSRk44SUU2SDBSRlg9czk2LWMiLCJnaXZlbl9uYW1lIjoiVEVBTSBHT1lVS0tVUkkiLCJpYXQiOjE2NjA2MzU0NDksImV4cCI6MTY2MDYzOTA0OSwianRpIjoiMWM2MmYxM2E1N2IzNmQ3ZjhjNTE3MGM5ZDhkMTdiZmExMWY5NTdiNCJ9.o34KxH_yShUSp8qqC8hfQcNqH3ZFcBjcKuQUldfcgKegunairnNrQks3CVlJ8zzRS5g80RpqUyk6KTWjYUVMLlfuPHfZx0ZSh4rWu2OLc6kFfV6GvtkWTOnqjw326F8I276v_CI1oBgRx0jP9Mj1ye030-IGqZNFErYlUHCMTDeJRIRalhFGvWUEYXerjXHngbQ0qUMLkx3KYM-NyNojUNB3me70YAVDBlUI0nYf2i662_jvQq4iBWwNxzPb7zAbrVOdkaTpEOZKgmiSOEzeHBDNgBSswceQyg3aVj7NWIVgPa-dYll_qhmpnuPsDuyjkKzmxAFkc_KGaJ0roLFZ7w";
    private static final String EMAIL = "swm.team.goyukkuri@gmail.com";

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTProvider jwtProvider;

    @InjectMocks
    private UserService userService;

    @Test
    void user_login_test() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(new User(EMAIL, "name")));
        when(jwtProvider.decodeJWTToSubject(anyString())).thenReturn(EMAIL);

        User user = userService.loginUser(JWT);

        assertThat(user.getEmail()).isEqualTo(EMAIL);
        verify(userRepository, times(1)).findUserByEmail(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void user_google_login_in_test() {
        User user = new User(EMAIL, "name", LoginType.GOOGLE, "uniqueId");
        when(jwtProvider.googleOAuthJwtToUser(anyString())).thenReturn(user);
        when(userRepository.existsUserByUniqueId(user.getUniqueId())).thenReturn(true);
        when(userRepository.findUserByUniqueId(user.getUniqueId())).thenReturn(Optional.of(user));
        String accessToken = "accessToken";
        when(jwtProvider.createAccessToken(user.getEmail())).thenReturn(accessToken);

        LoginResponse loginResponse = userService.signInGoogleAuthUser("token");

        assertAll(
                () -> assertThat(loginResponse.getToken()).isEqualTo(accessToken),
                () -> assertThat(loginResponse.getEmail()).isEqualTo(EMAIL)
        );
        verify(userRepository, times(1)).existsUserByUniqueId(anyString());
        verify(userRepository, times(1)).findUserByUniqueId(anyString());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void user_sign_in_test() {
        User user = new User(EMAIL, "name", LoginType.GOOGLE, "uniqueId");
        when(jwtProvider.googleOAuthJwtToUser(anyString())).thenReturn(user);
        when(userRepository.existsUserByUniqueId(user.getUniqueId())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findUserByUniqueId(user.getUniqueId())).thenReturn(Optional.of(user));
        String accessToken = "accessToken";
        when(jwtProvider.createAccessToken(user.getEmail())).thenReturn(accessToken);

        LoginResponse loginResponse = userService.signInGoogleAuthUser("token");

        assertAll(
                () -> assertThat(loginResponse.getToken()).isEqualTo(accessToken),
                () -> assertThat(loginResponse.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(loginResponse.getName()).isEqualTo(user.getName())
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

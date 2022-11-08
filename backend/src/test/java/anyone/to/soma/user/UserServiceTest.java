package anyone.to.soma.user;

import anyone.to.soma.IntegrationTest;
import anyone.to.soma.auth.JWTProvider;
import anyone.to.soma.letter.domain.LetterRepository;
import anyone.to.soma.user.application.UserService;
import anyone.to.soma.user.domain.PsychologicalExam;
import anyone.to.soma.user.domain.User;
import anyone.to.soma.user.domain.dao.UserRepository;
import anyone.to.soma.user.domain.type.Age;
import anyone.to.soma.user.domain.type.Gender;
import anyone.to.soma.user.domain.type.Job;
import anyone.to.soma.user.domain.dto.LoginResponse;
import anyone.to.soma.user.domain.dto.ProfileRequest;
import anyone.to.soma.utils.LetterDecorationRepository;
import anyone.to.soma.utils.ReplyRepository;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class UserServiceTest extends IntegrationTest {

    private static final String JWT = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjE3MjdiNmI0OTQwMmI5Y2Y5NWJlNGU4ZmQzOGFhN2U3YzExNjQ0YjEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJuYmYiOjE2NjA2MzUxNDksImF1ZCI6IjY3NDE3OTgwNzQ2MS01Nms2MW9vOWkzaTVlb2cyZmNpbWo2czFuNDhtc2xiYS5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjEwMzk4NDYxNTk5OTY2OTAxOTA2NiIsImVtYWlsIjoic3dtLnRlYW0uZ295dWtrdXJpQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhenAiOiI2NzQxNzk4MDc0NjEtNTZrNjFvbzlpM2k1ZW9nMmZjaW1qNnMxbjQ4bXNsYmEuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJuYW1lIjoiVEVBTSBHT1lVS0tVUkkiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUl0YnZta2xaNXJhSExlN2N4OUJtZXpJX0xHMkk5SzBSRk44SUU2SDBSRlg9czk2LWMiLCJnaXZlbl9uYW1lIjoiVEVBTSBHT1lVS0tVUkkiLCJpYXQiOjE2NjA2MzU0NDksImV4cCI6MTY2MDYzOTA0OSwianRpIjoiMWM2MmYxM2E1N2IzNmQ3ZjhjNTE3MGM5ZDhkMTdiZmExMWY5NTdiNCJ9.o34KxH_yShUSp8qqC8hfQcNqH3ZFcBjcKuQUldfcgKegunairnNrQks3CVlJ8zzRS5g80RpqUyk6KTWjYUVMLlfuPHfZx0ZSh4rWu2OLc6kFfV6GvtkWTOnqjw326F8I276v_CI1oBgRx0jP9Mj1ye030-IGqZNFErYlUHCMTDeJRIRalhFGvWUEYXerjXHngbQ0qUMLkx3KYM-NyNojUNB3me70YAVDBlUI0nYf2i662_jvQq4iBWwNxzPb7zAbrVOdkaTpEOZKgmiSOEzeHBDNgBSswceQyg3aVj7NWIVgPa-dYll_qhmpnuPsDuyjkKzmxAFkc_KGaJ0roLFZ7w";
    private static final String EMAIL = "swm.team.goyukkuri@gmail.com";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LetterRepository letterRepository;

    @Autowired
    private LetterDecorationRepository letterDecorationRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private JWTProvider jwtProvider;

    @Test
    void signInUser() {
        LoginResponse response = userService.signInGoogleAuthUser(JWT);
        DecodedJWT decodedJWT = jwtProvider.decodeJWT(response.getToken());
        assertThat(decodedJWT.getSubject()).isEqualTo(response.getEmail());
    }

    @Test
    void findMinReceiveCountTest() {
        List<User> usersByMinReceieveCount = userRepository.findUsersByMinReceiveCount(user.getId());
        assertThat(usersByMinReceieveCount.size()).isEqualTo(1);
        assertThat(usersByMinReceieveCount.get(0).getReceiveCount()).isEqualTo(1);
    }

    @Test
    @Transactional
    void user_profile_update_test() {
        ProfileRequest profileRequest = new ProfileRequest("nickname", Gender.MALE, Age.TWENTY, Job.STUDENT_UNIV, List.of(new PsychologicalExam(1L, 1L)));
        userService.updateUserProfile(user, profileRequest);
        User updatedUser = userRepository.findById(user.getId()).orElseThrow();

        assertAll(
                () -> assertThat(updatedUser.getProfile().getNickname()).isEqualTo(profileRequest.getNickname()),
                () -> assertThat(updatedUser.getProfile().getAge()).isEqualTo(profileRequest.getAge()),
                () -> assertThat(updatedUser.getProfile().getGender()).isEqualTo(profileRequest.getGender()),
                () -> assertThat(updatedUser.getProfile().getJob()).isEqualTo(profileRequest.getJob()),
                () -> assertThat(updatedUser.getProfile().getPsychologicalExams().size()).isEqualTo(profileRequest.getPsychologicalExams().size())
        );
    }


    @AfterEach
    void tearDown() {
        replyRepository.deleteAllInBatch();
        letterDecorationRepository.deleteAllInBatch();
        letterRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

}
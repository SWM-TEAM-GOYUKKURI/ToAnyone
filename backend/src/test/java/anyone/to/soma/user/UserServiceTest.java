package anyone.to.soma.user;

import anyone.to.soma.auth.JWTProvider;
import anyone.to.soma.base.IntegrationTest;
import anyone.to.soma.user.dto.LoginResponse;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest extends IntegrationTest {

    private static final String JWT = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjE3MjdiNmI0OTQwMmI5Y2Y5NWJlNGU4ZmQzOGFhN2U3YzExNjQ0YjEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJuYmYiOjE2NjA2MzUxNDksImF1ZCI6IjY3NDE3OTgwNzQ2MS01Nms2MW9vOWkzaTVlb2cyZmNpbWo2czFuNDhtc2xiYS5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjEwMzk4NDYxNTk5OTY2OTAxOTA2NiIsImVtYWlsIjoic3dtLnRlYW0uZ295dWtrdXJpQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhenAiOiI2NzQxNzk4MDc0NjEtNTZrNjFvbzlpM2k1ZW9nMmZjaW1qNnMxbjQ4bXNsYmEuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJuYW1lIjoiVEVBTSBHT1lVS0tVUkkiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUl0YnZta2xaNXJhSExlN2N4OUJtZXpJX0xHMkk5SzBSRk44SUU2SDBSRlg9czk2LWMiLCJnaXZlbl9uYW1lIjoiVEVBTSBHT1lVS0tVUkkiLCJpYXQiOjE2NjA2MzU0NDksImV4cCI6MTY2MDYzOTA0OSwianRpIjoiMWM2MmYxM2E1N2IzNmQ3ZjhjNTE3MGM5ZDhkMTdiZmExMWY5NTdiNCJ9.o34KxH_yShUSp8qqC8hfQcNqH3ZFcBjcKuQUldfcgKegunairnNrQks3CVlJ8zzRS5g80RpqUyk6KTWjYUVMLlfuPHfZx0ZSh4rWu2OLc6kFfV6GvtkWTOnqjw326F8I276v_CI1oBgRx0jP9Mj1ye030-IGqZNFErYlUHCMTDeJRIRalhFGvWUEYXerjXHngbQ0qUMLkx3KYM-NyNojUNB3me70YAVDBlUI0nYf2i662_jvQq4iBWwNxzPb7zAbrVOdkaTpEOZKgmiSOEzeHBDNgBSswceQyg3aVj7NWIVgPa-dYll_qhmpnuPsDuyjkKzmxAFkc_KGaJ0roLFZ7w";
    private static final String EMAIL = "swm.team.goyukkuri@gmail.com";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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
        List<User> usersByMinReceieveCount = userRepository.findUsersByMinReceieveCount();
        System.out.println(usersByMinReceieveCount);
    }
}
package anyone.to.soma.auth;

import anyone.to.soma.user.domain.User;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JWTProviderTest {

    private static final String JWT = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjE3MjdiNmI0OTQwMmI5Y2Y5NWJlNGU4ZmQzOGFhN2U3YzExNjQ0YjEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJuYmYiOjE2NjA2MzUxNDksImF1ZCI6IjY3NDE3OTgwNzQ2MS01Nms2MW9vOWkzaTVlb2cyZmNpbWo2czFuNDhtc2xiYS5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjEwMzk4NDYxNTk5OTY2OTAxOTA2NiIsImVtYWlsIjoic3dtLnRlYW0uZ295dWtrdXJpQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhenAiOiI2NzQxNzk4MDc0NjEtNTZrNjFvbzlpM2k1ZW9nMmZjaW1qNnMxbjQ4bXNsYmEuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJuYW1lIjoiVEVBTSBHT1lVS0tVUkkiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUl0YnZta2xaNXJhSExlN2N4OUJtZXpJX0xHMkk5SzBSRk44SUU2SDBSRlg9czk2LWMiLCJnaXZlbl9uYW1lIjoiVEVBTSBHT1lVS0tVUkkiLCJpYXQiOjE2NjA2MzU0NDksImV4cCI6MTY2MDYzOTA0OSwianRpIjoiMWM2MmYxM2E1N2IzNmQ3ZjhjNTE3MGM5ZDhkMTdiZmExMWY5NTdiNCJ9.o34KxH_yShUSp8qqC8hfQcNqH3ZFcBjcKuQUldfcgKegunairnNrQks3CVlJ8zzRS5g80RpqUyk6KTWjYUVMLlfuPHfZx0ZSh4rWu2OLc6kFfV6GvtkWTOnqjw326F8I276v_CI1oBgRx0jP9Mj1ye030-IGqZNFErYlUHCMTDeJRIRalhFGvWUEYXerjXHngbQ0qUMLkx3KYM-NyNojUNB3me70YAVDBlUI0nYf2i662_jvQq4iBWwNxzPb7zAbrVOdkaTpEOZKgmiSOEzeHBDNgBSswceQyg3aVj7NWIVgPa-dYll_qhmpnuPsDuyjkKzmxAFkc_KGaJ0roLFZ7w";
    private static final String EMAIL = "swm.team.goyukkuri@gmail.com";

    private JWTProvider jwtProvider;


    @BeforeEach
    void setup() {
        jwtProvider = new JWTProvider();
    }

    @Test
    void decodeJWTTest() {
        DecodedJWT decodedJWT = jwtProvider.decodeJWT(JWT);
        System.out.println(decodedJWT.getClaim("sub"));
        System.out.println(decodedJWT.getClaim("email"));
        System.out.println(decodedJWT.getClaim("name"));
    }

    @Test
    void createJWTToken() {
        String accessToken = jwtProvider.createAccessToken(EMAIL);
        assertThat(jwtProvider.decodeJWT(accessToken).getSubject()).isEqualTo(EMAIL);
    }

    @Test
    void googleOAuthJwtToUserTest() {
        DecodedJWT decodedJWT = jwtProvider.decodeJWT(JWT);
        String sub = decodedJWT.getClaim("sub").asString();
        String email = decodedJWT.getClaim("email").asString();

        User user = jwtProvider.googleOAuthJwtToUser(JWT);

        assertThat(user.getUniqueId()).isEqualTo(sub);
        assertThat(user.getEmail()).isEqualTo(email);
    }

}
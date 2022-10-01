package anyone.to.soma.auth;

import anyone.to.soma.user.LoginType;
import anyone.to.soma.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTProvider {


    private final String secretKey = "secret_temp";
    private final long accessTokenDurationTime = 10 * 60 * 1000;

    private final Algorithm algorithm = Algorithm.HMAC256(secretKey);

    public String createAccessToken(String email) {
        return createJWT(email, accessTokenDurationTime);
    }

    private String createJWT(String email, long tokenDurationTime) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + tokenDurationTime);

        return JWT.create()
                .withSubject(email)
                .withIssuedAt(now)
                .withExpiresAt(expirationTime)
                .sign(algorithm);
    }

    public User googleOAuthJwtToUser(String token) {
        DecodedJWT jwt = decodeJWT(token);
        String id = jwt.getClaim("sub").asString();
        String email = jwt.getClaim("email").asString();
        String name = jwt.getClaim("name").asString();
        return new User(email, name, LoginType.GOOGLE, id);
    }

    public DecodedJWT decodeJWT(String token) {
        return JWT.decode(token);
    }

}

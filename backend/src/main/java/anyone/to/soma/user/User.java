package anyone.to.soma.user;

import anyone.to.soma.decoration.Decoration;
import anyone.to.soma.decoration.DecorationType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Nullable
    private String uniqueId;

    private int receiveCount = 0;

    public void receiveLetter() {
        receiveCount++;
    }

    public User(String email, String name, LoginType loginType, String uniqueId) {
        this(null, email, name, loginType, uniqueId);
    }

    public User(String email, String name) {
        this(null, email, name, LoginType.COMMON, null);
    }

    private User(Long id, String email, String name, LoginType loginType, String uniqueId) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.loginType = loginType;
        this.uniqueId = uniqueId;
    }


    public void useDecoration(List<DecorationType> decorations) {

    }
}

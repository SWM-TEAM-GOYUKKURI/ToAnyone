package anyone.to.soma.user.domain;

import anyone.to.soma.user.domain.event.UserCreatedEvent;
import anyone.to.soma.user.domain.type.LoginType;
import anyone.to.soma.user.domain.vo.Point;
import anyone.to.soma.user.domain.vo.UserAchievement;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.Instant;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends AbstractAggregateRoot<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Nullable
    private String uniqueId;

    @JsonUnwrapped
    UserAchievement userAchievement = new UserAchievement();

    private int receiveCount = 0;

    private boolean registrationFormFilled;

    private Instant createdAt = Instant.now();

    private Instant lastLogin;

    private Point point = new Point();


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public void receiveLetter() {
        this.receiveCount++;
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
        this.registrationFormFilled = false;
    }

    @PostPersist
    public void created() {
        this.registerEvent(new UserCreatedEvent(this.id, this.name, this.email));
    }

    public void updateProfile(Profile profile) {
        this.profile = profile;
        this.registrationFormFilled = true;
    }

    public String getNickname() {
        if (profile == null) {
            return "익명";
        }

        return profile.getNickname();
    }

    public void recordLastLogin() {
        this.lastLogin = Instant.now();
    }


    public Long getPoint() {
        return point.getValue();
    }
}

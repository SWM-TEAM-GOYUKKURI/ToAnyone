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
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends AbstractAggregateRoot<User> {

    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Tokyo");

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

    private Instant lastLogin = Instant.now();
    private int loginCount = 1;

    private Point point = new Point();

    private String userImageUrl = "";

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
        Instant now = Instant.now();
        if (this.lastLogin != null && instantToDay(now) != instantToDay(lastLogin)) {
            loginCount++;
        }

        this.lastLogin = now;

    }

    private int instantToDay(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZONE_ID).getDayOfYear();
    }

    public Long getPoint() {
        return point.getValue();
    }

    public void increasePoint(Long point) {
        this.point.increase(point);
    }

    public void updateImage(String imageUrl) {
        this.userImageUrl = imageUrl;
    }

    public void purchaseItem(Long price) {
        point.minus(price);
    }
}

package anyone.to.soma.user.domain;

import anyone.to.soma.user.domain.type.LoginType;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.valueextraction.UnwrapByDefault;
import java.time.Instant;

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

    @JsonUnwrapped
    UserAchievement userAchievement = new UserAchievement();

    private int receiveCount = 0 ;

    private boolean registrationFormFilled;

    @CreatedDate
    private Instant createdAt;

    private Instant lastLogin;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public void receiveLetter() {
        this.receiveCount++;
    }

    public void sendLetter() {
        userAchievement.increaseSendLetterCount();
    }

    public void achieve() {
        userAchievement.increaseAchievementCount();
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

    public void recordLastLogin(){
        this.lastLogin = Instant.now();
    }

}

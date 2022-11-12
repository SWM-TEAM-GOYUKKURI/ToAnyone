package anyone.to.soma.user.domain.vo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class UserAchievement {

    @Column(name = "achievement_count")
    private int achievementCount = 0;
    @Column(name = "send_letter_count")
    private int sendLetterCount = 0;

    @Column(name = "login_count")
    private int loginCount = 0;

    public int getAchievementCountValue() {
        return achievementCount;
    }

    public int getSendLetterCountValue() {
        return sendLetterCount;
    }

    public int getLoginCountValue() {
        return loginCount;
    }

    public void increaseAchievementCount() {
        this.achievementCount++;
    }

    public void increaseSendLetterCount() {
        this.sendLetterCount++;
    }

}

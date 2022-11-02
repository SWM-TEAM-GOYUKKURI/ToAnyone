package anyone.to.soma.user.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAchievement {

    @Column(name = "achievement_count")
    private int achievementCount = 0;
    @Column(name = "send_letter_count")
    private int sendLetterCount = 0;

    public int getAchievementCountValue() {
        return achievementCount;
    }

    public int getSendLetterCountValue() {
        return sendLetterCount;
    }

    public void increaseAchievementCount(){
        this.achievementCount++;
    }

    public void increaseSendLetterCount(){
        this.sendLetterCount++;
    }

}

package anyone.to.soma.user.application;

import anyone.to.soma.user.domain.Achievement;
import anyone.to.soma.user.domain.dao.AchievementRepository;
import anyone.to.soma.user.domain.dao.UserRepository;
import anyone.to.soma.user.domain.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static anyone.to.soma.user.domain.type.DefaultAchievement.LEVEL_ONE;

@Component
@RequiredArgsConstructor
public class AchievementPolicy {

    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;

    @EventListener
    public void achieveLevelOne(UserCreatedEvent userCreatedEvent) {
        Long userId = userCreatedEvent.getUserId();
        if (userRepository.existsById(userId)) {
            achievementRepository.save(new Achievement(LEVEL_ONE.getLevel(), LEVEL_ONE.getName(), LEVEL_ONE.getTag(), userId));
            userRepository.increaseUserPoint(userId, LEVEL_ONE.getPoint());
        }
    }
}

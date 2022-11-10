package anyone.to.soma.user.application;

import anyone.to.soma.letter.domain.LetterRepository;
import anyone.to.soma.letter.domain.event.LetterCreatedEvent;
import anyone.to.soma.letter.domain.event.LetterReadEvent;
import anyone.to.soma.user.domain.Achievement;
import anyone.to.soma.user.domain.dao.AchievementRepository;
import anyone.to.soma.user.domain.dao.UserRepository;
import anyone.to.soma.user.domain.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static anyone.to.soma.user.domain.type.DefaultAchievement.LEVEL_ONE;
import static anyone.to.soma.user.domain.type.DefaultAchievement.LEVEL_THREE;

@Component
@RequiredArgsConstructor
public class AchievementPolicy {

    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;
    private final LetterRepository letterRepository;

    @EventListener
    public void achieveLevelOne(UserCreatedEvent userCreatedEvent) {
        Long userId = userCreatedEvent.getUserId();
        if (userRepository.existsById(userId)) {
            achievementRepository.save(new Achievement(LEVEL_ONE.getLevel(), LEVEL_ONE.getName(), LEVEL_ONE.getTag(), userId));
            userRepository.increaseUserPoint(userId, LEVEL_ONE.getPoint());
        }
    }

    @EventListener
    public void achieveLevelTwo(LetterCreatedEvent letterCreatedEvent) {
        Long userId = letterCreatedEvent.getUserId();
        if (userRepository.existsById(userId) && letterRepository.existsById(letterCreatedEvent.getId())) {
            achievementRepository.save(new Achievement(LEVEL_ONE.getLevel(), LEVEL_ONE.getName(), LEVEL_ONE.getTag(), userId));
            userRepository.increaseUserPoint(userId, LEVEL_ONE.getPoint());
        }
    }

    @EventListener(condition = "#letterReadEvent.read == true")
    public void achieveLevelThree(LetterReadEvent letterReadEvent) {
        Long userId = letterReadEvent.getUserId();
        if (userRepository.existsById(userId) && letterRepository.existsById(letterReadEvent.getId())) {
            achievementRepository.save(new Achievement(LEVEL_THREE.getLevel(), LEVEL_THREE.getName(), LEVEL_THREE.getTag(), userId));
            userRepository.increaseUserPoint(userId, LEVEL_THREE.getPoint());
        }
    }
}

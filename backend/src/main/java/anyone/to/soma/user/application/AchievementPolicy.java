package anyone.to.soma.user.application;

import anyone.to.soma.letter.domain.dao.LetterRepository;
import anyone.to.soma.letter.domain.event.LetterCreatedEvent;
import anyone.to.soma.letter.domain.event.LetterReadEvent;
import anyone.to.soma.user.domain.Achievement;
import anyone.to.soma.user.domain.dao.AchievementRepository;
import anyone.to.soma.user.domain.dao.UserRepository;
import anyone.to.soma.user.domain.event.UserCreatedEvent;
import anyone.to.soma.user.domain.type.DefaultAchievement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import static anyone.to.soma.user.domain.type.DefaultAchievement.*;

@Component
@RequiredArgsConstructor
public class AchievementPolicy {

    private final UserRepository userRepository;
    private final AchievementRepository achievementRepository;
    private final LetterRepository letterRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void achieveLevelOne(UserCreatedEvent userCreatedEvent) {
        Long userId = userCreatedEvent.getUserId();
        if (userRepository.existsById(userId)) {
            achieve(LEVEL_ONE, userId);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(condition = "#letterCreatedEvent.sentCount==0")
    public void achieveLevelTwo(LetterCreatedEvent letterCreatedEvent) {
        Long userId = letterCreatedEvent.getUserId();
        if (userRepository.existsById(userId) && letterRepository.countLetterBySenderId(userId) == 1) {
            achieve(LEVEL_TWO, userId);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(condition = "#letterReadEvent.read == true")
    public void achieveLevelThree(LetterReadEvent letterReadEvent) {
        Long userId = letterReadEvent.getUserId();
        if (userRepository.existsById(userId) && letterRepository.existsById(letterReadEvent.getId())) {
            achieve(LEVEL_THREE, userId);
        }
    }

    public void achieveLevelFour() {


    }

    private void achieve(DefaultAchievement levelThree, Long userId) {
        achievementRepository.save(new Achievement(levelThree.getLevel(), levelThree.getName(), levelThree.getTag(), userId));
        userRepository.increaseUserPointWithAchievement(userId, levelThree.getPoint());
    }


}

package anyone.to.soma.user.application;

import anyone.to.soma.letter.domain.dao.LetterRepository;
import anyone.to.soma.letter.domain.dao.ReplyLetterRepository;
import anyone.to.soma.letter.domain.event.LetterCreatedEvent;
import anyone.to.soma.letter.domain.event.LetterReadEvent;
import anyone.to.soma.letter.domain.event.ReplyCreatedEvent;
import anyone.to.soma.user.domain.Achievement;
import anyone.to.soma.user.domain.User;
import anyone.to.soma.user.domain.dao.AchievementRepository;
import anyone.to.soma.user.domain.dao.UserItemRepository;
import anyone.to.soma.user.domain.dao.UserRepository;
import anyone.to.soma.user.domain.event.UserCreatedEvent;
import anyone.to.soma.user.domain.event.UserItemCreatedEvent;
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
    private final ReplyLetterRepository replyLetterRepository;
    private final LetterRepository letterRepository;
    private final UserItemRepository userItemRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void achieveLevelOne(UserCreatedEvent userCreatedEvent) {
        Long userId = userCreatedEvent.getUserId();
        if (userRepository.existsById(userId)) {
            achieve(LEVEL_ONE, userId);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void achieveLevelTwo(LetterCreatedEvent letterCreatedEvent) {
        User user = userRepository.findById(letterCreatedEvent.getSenderId()).orElseThrow();
        if (user.getUserAchievement().getSendLetterCountValue() == 1) {
            achieve(LEVEL_TWO, user.getId());
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(condition = "#replyCreatedEvent.sendReplyCount>0")
    public void achieveReply(ReplyCreatedEvent replyCreatedEvent) {
        Long userId = replyCreatedEvent.getUserId();
        User user = userRepository.findById(userId).orElseThrow();
        if (replyLetterRepository.existsById(replyCreatedEvent.getReplyLetterId())) {
            achieveReply(user.getUserAchievement().getSendReplyLetterCount(), userId);
        }
    }

    public void achieveReply(int replyCount, Long userId) {
        if (replyCount == 1) achieve(LEVEL_FOUR, userId);
        if (replyCount == 3) achieve(LEVEL_FIVE, userId);
        if (replyCount == 10) achieve(LEVEL_SIX, userId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void achieveUserItem(UserItemCreatedEvent userItemCreatedEvent) {
        Long userId = userItemCreatedEvent.getUserId();
        int itemCount = userItemRepository.countUserItemByUserId(userId);
        if (userRepository.existsById(userId)) {
            achieveUserItem(itemCount, userId);
        }
    }

    public void achieveUserItem(int itemCount, Long userId) {
        if (itemCount == 1) achieve(LEVEL_SEVEN, userId);
        if (itemCount == 3) achieve(LEVEL_EIGHT, userId);
        if (itemCount == 10) achieve(LEVEL_NINE, userId);
        if (itemCount == 30) achieve(LEVEL_TEN, userId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void achieveReceiveLetter(LetterCreatedEvent letterCreatedEvent) {
        User user = userRepository.findById(letterCreatedEvent.getReceiverId()).orElseThrow();
        achieveReceiveLetter(user.getReceiveCount(), user.getId());
    }

    public void achieveReceiveLetter(int receiveCount, Long userId) {
        if (receiveCount == 1) achieve(LEVEL_ELEVEN, userId);
        if (receiveCount == 3) achieve(LEVEL_TWELVE, userId);
        if (receiveCount == 10) achieve(LEVEL_THIRTEEN, userId);
        if (receiveCount == 25) achieve(LEVEL_FOURTEEN, userId);
    }


    public void achieve(DefaultAchievement level, Long userId) {
        achievementRepository.save(new Achievement(level.getLevel(), level.getName(), level.getTag(), userId));
        userRepository.increaseUserPointWithAchievement(userId, level.getPoint());
    }


}

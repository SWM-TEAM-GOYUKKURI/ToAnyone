package anyone.to.soma.letter.application;

import anyone.to.soma.letter.domain.dao.LetterRepository;
import anyone.to.soma.user.application.UserDeleteEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LetterDeleteEventListener implements ApplicationListener<UserDeleteEvent> {

    private final LetterRepository letterRepository;

    @Override
    public void onApplicationEvent(UserDeleteEvent userDeleteEvent) {
        Long userId = userDeleteEvent.getUserId();
        letterRepository.deleteAllBySenderId(userId);
        letterRepository.deleteAllByReceiverId(userId);
    }
}

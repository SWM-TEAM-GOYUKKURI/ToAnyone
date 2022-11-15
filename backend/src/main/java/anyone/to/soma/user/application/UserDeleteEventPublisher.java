package anyone.to.soma.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDeleteEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(Long userId) {
        UserDeleteEvent userDeleteEvent = new UserDeleteEvent(this, userId);
        applicationEventPublisher.publishEvent(userDeleteEvent);
    }

}

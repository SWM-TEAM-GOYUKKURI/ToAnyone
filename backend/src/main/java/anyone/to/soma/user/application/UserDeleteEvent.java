package anyone.to.soma.user.application;

import org.springframework.context.ApplicationEvent;

public class UserDeleteEvent extends ApplicationEvent {

    private Long userId;

    public UserDeleteEvent(Object source, Long userId) {
        super(source);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}

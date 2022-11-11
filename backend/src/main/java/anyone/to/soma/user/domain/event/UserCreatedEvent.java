package anyone.to.soma.user.domain.event;

public class UserCreatedEvent {

    private Long userId;
    private String name;
    private String email;


    public UserCreatedEvent(Long userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

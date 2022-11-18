package anyone.to.soma.user.domain.event;

public class UserItemCreatedEvent {

    public Long getItemId() {
        return itemId;
    }

    public Long getUserId() {
        return userId;
    }

    private Long itemId;
    private Long userId;

    public UserItemCreatedEvent(Long itemId, Long userId) {
        this.itemId = itemId;
        this.userId = userId;
    }
}

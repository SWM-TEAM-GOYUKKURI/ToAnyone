package anyone.to.soma.letter.domain.event;

public class LetterReadEvent {
    private Long id;
    private Long userId;
    private boolean isRead;

    public LetterReadEvent(Long id, Long userId, boolean isRead) {
        this.id = id;
        this.userId = userId;
        this.isRead = isRead;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isRead() {
        return isRead;
    }
}

package anyone.to.soma.letter.domain.event;

public class LetterCreatedEvent {

    private Long id;

    private Long userId;

    private int sentCount;

    private int receiveCount;

    public LetterCreatedEvent(Long id, Long userId, int sentCount, int receiveCount) {
        this.id = id;
        this.userId = userId;
        this.sentCount = sentCount;
        this.receiveCount = receiveCount;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public int getSentCount() {
        return sentCount;
    }

    public int getReceiveCount() {
        return receiveCount;
    }
}

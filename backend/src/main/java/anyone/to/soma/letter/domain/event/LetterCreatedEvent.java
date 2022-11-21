package anyone.to.soma.letter.domain.event;

public class LetterCreatedEvent {

    private Long letterId;

    private Long senderId;

    private Long receiverId;

    public LetterCreatedEvent(Long letterId, Long senderId, Long receiverId) {
        this.letterId = letterId;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public Long getLetterId() {
        return letterId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }
}

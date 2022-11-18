package anyone.to.soma.letter.domain.event;

public class ReplyCreatedEvent {
    private Long replyLetterId;
    private Long userId;
    private int sendReplyCount;

    public ReplyCreatedEvent(Long replyLetterId, Long userId, int sendReplyCount) {
        this.replyLetterId = replyLetterId;
        this.userId = userId;
        this.sendReplyCount = sendReplyCount;
    }

    public Long getReplyLetterId() {
        return replyLetterId;
    }

    public Long getUserId() {
        return userId;
    }

    public int getSendReplyCount() {
        return sendReplyCount;
    }
}

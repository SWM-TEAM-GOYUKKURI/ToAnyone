package anyone.to.soma.letter.application;

import anyone.to.soma.exception.ApplicationException;
import anyone.to.soma.exception.repository.NoSuchRecordException;
import anyone.to.soma.letter.domain.Letter;
import anyone.to.soma.letter.domain.ReplyLetter;
import anyone.to.soma.letter.domain.dao.LetterRepository;
import anyone.to.soma.letter.domain.dao.ReplyLetterRepository;
import anyone.to.soma.letter.domain.dto.InboxLetterResponse;
import anyone.to.soma.letter.domain.dto.LetterRequest;
import anyone.to.soma.letter.domain.dto.SingleLetterResponse;
import anyone.to.soma.user.domain.User;
import anyone.to.soma.user.domain.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;
    private final ReplyLetterRepository replyLetterRepository;
    private final UserRepository userRepository;

    @Transactional
    public SingleLetterResponse retrieveInboxSingleLetter(Long letterId, Long userId) {
        Letter letter = letterRepository.findById(letterId).orElseThrow(NoSuchRecordException::new);
        letter.checkValidReader(userId);

        return SingleLetterResponse.of(letter, letter.getReceiver(), letter.getReplyLetters());
    }

    @Transactional(readOnly = true)
    public List<InboxLetterResponse> retrieveInboxAllLetters(Long receiverId) {
        List<Letter> letter = letterRepository.findLettersByReceiverId(receiverId);
        User receiver = userRepository.findById(receiverId).orElseThrow(NoSuchRecordException::new);
        return InboxLetterResponse.listOf(letter, receiver);
    }

    @Transactional
    public Long writeLetter(LetterRequest request, User sender) {
        Letter letter = new Letter(request.getContent(), sender);
        User randomReceiver = findRandomReceiver(sender);
        letter.attachDecorations(request.getDecorations());
        letter.send(randomReceiver);
        Long letterId = letterRepository.save(letter).getId();
        userRepository.increaseReceiveCount(randomReceiver.getId());
        userRepository.increaseSendCount(sender.getId());
        return letterId;
    }

    private User findRandomReceiver(User sender) {
        List<User> userList = userRepository.findUsersByMinReceiveCount(sender.getId());

        if (userList.isEmpty()) {
            throw new ApplicationException("편지를 보낼 사람이 없습니다.");
        }

        Random random = new Random();
        return userList.get(random.nextInt(userList.size()));
    }

    @Transactional
    public void writeReplyLetter(Long letterId, LetterRequest request, User replySender) {
        Letter letter = letterRepository.findById(letterId).orElseThrow(NoSuchRecordException::new);

        User replyLetterReceiver = letter.findReplyLetterReceiver(replySender);

        if (!letter.getReceiver().getId().equals(replyLetterReceiver.getId())) {
            throw new ApplicationException("잘못된 권한입니다.");
        }

        ReplyLetter replyLetter = new ReplyLetter(request.getContent(), LocalDate.now(), letter, replySender.getNickname(), replySender.getUserImageUrl(), replyLetterReceiver.getNickname(), replyLetterReceiver.getUserImageUrl(), request.getDecorations());
        letter.reply(replyLetter, replySender);
        userRepository.increaseSendReplyLetterCount(replySender.getId());
    }

    public List<InboxLetterResponse> retrieveSentLetters(User sender) {
        List<Letter> sentLetters = letterRepository.findLettersBySenderId(sender.getId());
        return InboxLetterResponse.sentLetterListOf(sentLetters, sender);
    }

    @Transactional
    public void readLetter(Long letterId, User reader) {
        Letter letter = letterRepository.findById(letterId).orElseThrow(NoSuchRecordException::new);
        letter.checkValidReader(reader.getId());

        if (reader.getEmail().equals(letter.getReceiver().getEmail())) {
            letter.read();
            letterRepository.save(letter);
            return;
        }

        List<ReplyLetter> replyLetters = letter.getReplyLetters();
        replyLetters.stream()
                .filter(replyLetter -> replyLetter.isReceiver(reader.getNickname()))
                .forEach(ReplyLetter::read);
        replyLetterRepository.saveAll(replyLetters);
    }
}

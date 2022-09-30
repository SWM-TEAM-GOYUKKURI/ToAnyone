package anyone.to.soma.letter;

import anyone.to.soma.exception.ApplicationException;
import anyone.to.soma.exception.repository.NoSuchRecordException;
import anyone.to.soma.letter.domain.Letter;
import anyone.to.soma.letter.domain.LetterRepository;
import anyone.to.soma.letter.domain.ReplyLetterRepository;
import anyone.to.soma.letter.dto.InboxLetterResponse;
import anyone.to.soma.letter.dto.LetterRequest;
import anyone.to.soma.letter.dto.ReplyLetterRequest;
import anyone.to.soma.user.User;
import anyone.to.soma.user.UserRepository;
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

    @Transactional(readOnly = true)
    public InboxLetterResponse retrieveInboxSingleLetter(Long letterId, Long userId) {
        Letter letter = letterRepository.findById(letterId).orElseThrow(NoSuchRecordException::new);

        if (!letter.getReceiver().getId().equals(userId)){
            throw new ApplicationException("잘못된 권한입니다.");
        }

        return InboxLetterResponse.of(letter, letter.getReceiver().getName());
    }

    @Transactional(readOnly = true)
    public List<InboxLetterResponse> retrieveInboxAllLetters(Long receiverId) {
        List<Letter> letter = letterRepository.findLettersByReceiverId(receiverId);
        User receiver = userRepository.findById(receiverId).orElseThrow(NoSuchRecordException::new);
        return InboxLetterResponse.listOf(letter, receiver.getName());
    }

    @Transactional
    public Long writeLetter(LetterRequest request, User sender) {
        Letter letter = new Letter(request.getContent(), sender);
        User randomReceiver = findRandomReceiver(sender.getId());
        letter.send(randomReceiver);
        letter.attachDecorations(request.getDecorations());

        Long letterId = letterRepository.save(letter).getId();
        randomReceiver.receiveLetter();
        return letterId;
    }

    private User findRandomReceiver(Long senderId) {
        List<User> userList = userRepository.findUsersByMinReceiveCount(senderId);

        if (userList.isEmpty()) {
            throw new ApplicationException("편지를 보낼 사람이 없습니다.");
        }

        Random random = new Random();
        return userList.get(random.nextInt(userList.size()));
    }

    @Transactional
    public void writeReplyLetter(Long letterId, LetterRequest request, User sender){
        Letter letter = letterRepository.findById(letterId).orElseThrow(NoSuchRecordException::new);

        if (!letter.getReceiver().getId().equals(sender.getId())){
            throw new ApplicationException("잘못된 권한입니다.");
        }

        ReplyLetter replyLetter = new ReplyLetter(request.getContent(), LocalDate.now(), letter, sender.getName());
        letter.reply(replyLetter);

    }
}

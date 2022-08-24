package anyone.to.soma.letter;

import anyone.to.soma.letter.dto.LetterRequest;
import anyone.to.soma.user.User;
import anyone.to.soma.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long writeLetter(LetterRequest request, User sender) {
        Letter letter = new Letter(request.getContent(), sender);
        User randomReceiver = findRandomReceiver(sender.getId());
        letter.send(randomReceiver);
        Long letterId = letterRepository.save(letter).getId();
        randomReceiver.receiveLetter();
        return letterId;
    }

    private User findRandomReceiver(Long senderId) {
        List<User> userList = userRepository.findUsersByMinReceiveCount(senderId);

        if (userList.isEmpty()) {
            throw new IllegalArgumentException("편지를 보낼 사람이 없습니다.");
        }

        Random random = new Random();
        return userList.get(random.nextInt(userList.size()));

    }
}

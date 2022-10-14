package anyone.to.soma.config.loader;

import anyone.to.soma.decoration.DecorationType;
import anyone.to.soma.letter.application.LetterService;
import anyone.to.soma.letter.domain.dto.LetterRequest;
import anyone.to.soma.user.domain.User;
import anyone.to.soma.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
@Profile("local | dev")
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final LetterService letterService;

    @Override
    public void run(String... args) {
        User user1 = new User("sender@gmail.com", "김삿갓");
        User user2 = new User("test2@gmail.com", "심첨지");
        User user3 = new User("test3@gmail.com", "기러기");
        User user4 = new User("test4@gmail.com", "김철수");
        User user5 = new User("test5@gmail.com", "김영희");
        List<User> users = userRepository.saveAll(List.of(user1, user2, user3, user4, user5));

        for (int i = 0; i < 10; i++) {
            letterService.writeLetter(new LetterRequest("안녕하세요" + i, List.of(DecorationType.STAMP, DecorationType.FONT)), users.get(0));
        }

    }


}

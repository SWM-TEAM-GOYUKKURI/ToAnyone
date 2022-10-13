package anyone.to.soma;

import anyone.to.soma.user.domain.User;
import anyone.to.soma.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    protected User user;
    protected User user1;
    protected User user2;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        if (notExecutingBeforeEachTest(testInfo)) {
            return;
        }

        User eungi = new User("eungi6850@gmail.com", "eungi");
        eungi.receiveLetter();
        eungi.receiveLetter();
        user2 = userRepository.save(eungi);

        User somni = new User("somni@gmail.com", "somni");
        somni.receiveLetter();
        user1 = userRepository.save(somni);
        user = userRepository.save(new User("receiver@gmail.com", "receiver"));
    }

    private boolean notExecutingBeforeEachTest(TestInfo testInfo) {
        List<String> testNames = List.of("noReceiver()");
        return testNames.contains(testInfo.getDisplayName());
    }
}

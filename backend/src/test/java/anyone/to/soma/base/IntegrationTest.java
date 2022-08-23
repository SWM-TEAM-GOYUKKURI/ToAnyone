package anyone.to.soma.base;

import anyone.to.soma.user.User;
import anyone.to.soma.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    protected User user1;
    protected User user2;
    protected User user3;

    @BeforeEach
    void setUp() {
        user1 = userRepository.save(new User("eungi6850@gmail.com", "eungi"));
        user2 = userRepository.save(new User("somni@gmail.com", "somni"));
        user3 = userRepository.save(new User("test@gmail.com", "test"));
    }
}

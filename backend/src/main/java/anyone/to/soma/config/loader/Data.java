package anyone.to.soma.config.loader;

import anyone.to.soma.user.User;

import java.util.List;

public class Data {

    public static List<User> getUsers() {
        User user = new User("test1@gmail.com", "김삿갓");
        User user2 = new User("test2@gmail.com", "심첨지");
        User user3 = new User("test3@gmail.com", "기러기");
        User user4 = new User("test4@gmail.com", "김철수");
        User user5 = new User("test5@gmail.com", "김영희");
        return List.of(user, user2, user3, user4, user5);
    }


}

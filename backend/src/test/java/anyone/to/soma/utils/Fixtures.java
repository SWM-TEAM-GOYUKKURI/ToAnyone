package anyone.to.soma.utils;

import anyone.to.soma.letter.domain.Letter;
import anyone.to.soma.user.LoginType;
import anyone.to.soma.user.domain.User;

public class Fixtures {


    public static class UserStub {

        public static User defaultGoogleUser(String email) {
            return customUser(email, "name", LoginType.GOOGLE, "uniqueId");
        }

        private static User customUser(String email, String name, LoginType loginType, String uniqueId) {
            return new User(email, name, loginType, uniqueId);
        }
    }

    public static class LetterStub {
        public static Letter defaultLetterWithReceiver(String content) {
            Letter letter = customLetter(content, UserStub.defaultGoogleUser("sender@gmail.com"));
            letter.send(UserStub.defaultGoogleUser("receiver@gmail.com"));
            return letter;
        }

        private static Letter customLetter(String content, User user) {
            return new Letter(content, user);
        }


    }
}

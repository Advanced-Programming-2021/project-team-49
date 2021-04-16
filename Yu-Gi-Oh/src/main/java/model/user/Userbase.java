package model.user;

import java.util.ArrayList;
import java.util.List;

public class Userbase {
    private final List<User> users = new ArrayList<>();
    private static final int INIT_COINS = 10;

    public void addUser(String username, String nickname, String password) {
        User user = new User(username, nickname, password, INIT_COINS);
        users.add(user);
    }

    public User getUserByUsername(String username) {
        return users.stream().filter(user -> username.equals(user.getUsername()))
                .findFirst().orElse(null);
    }

    public User getUserByNickname(String nickname) {
        return users.stream().filter(user -> nickname.equals(user.getNickname()))
                .findFirst().orElse(null);
    }
}

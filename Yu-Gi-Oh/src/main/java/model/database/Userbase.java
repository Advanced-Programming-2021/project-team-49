package model.database;

import model.user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Userbase {

    private static final int INIT_COINS = 10;
    private final List<User> users = new ArrayList<>();

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

    public List<User> getUsersSortedByScore() {
        users.sort(Comparator.comparing(User::getScore).thenComparing(User::getNickname));
        return users;
    }
}
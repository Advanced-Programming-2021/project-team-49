package model.database;

import model.user.User;

// TODO: implement loading from file
public class Database {
    private final Userbase userbase = new Userbase();
    private final Shop shop = new Shop();

    public Userbase getUserbase() {
        return userbase;
    }

    public Shop getShop() {
        return shop;
    }

    public void saveUserbase(Userbase userbase) {

    }

    public void saveUser(User user) {

    }
}

package model.game;

import model.user.User;

public class Player {

    private final User user;
    private int winCount = 0;
    private int maxLifePoints = 0;
    private int lifePoints;

    public Player(User user, int lifePoints) {
        this.user = user;
        this.lifePoints = lifePoints;
    }

    public User getUser() {
        return user;
    }

    public int getWinCount() {
        return winCount;
    }

    public void incrementWinCount() {
        winCount++;
    }

    public int getMaxLifePoints() {
        return maxLifePoints;
    }

    public void setCurrentMaxLifePoints() {
        this.maxLifePoints = Math.max(maxLifePoints, lifePoints);
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public void addLifePoints(int amount) {
        lifePoints += amount;
    }

    public void removeLifePoints(int amount) {
        lifePoints -= amount;
    }
}

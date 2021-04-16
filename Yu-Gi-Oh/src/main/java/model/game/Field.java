package model.game;

public class Field {

    private GameMat hostMat;
    private GameMat guestMat;
    private int hostLifePoint;
    private int guestLifePoint;

    Field(GameMat hostMat, GameMat guestMat, int hostLifePoint, int guestLifePoint) {
        this.hostMat = hostMat;
        this.guestMat = guestMat;
        this.hostLifePoint = hostLifePoint;
        this.guestLifePoint = guestLifePoint;
    }
}

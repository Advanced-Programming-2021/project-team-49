package model.game;

public class Field {

    private GameMat hostMat;
    private GameMat guestMat;

    Field(GameMat hostMat, GameMat guestMat) {
        this.hostMat = hostMat;
        this.guestMat = guestMat;
    }
}

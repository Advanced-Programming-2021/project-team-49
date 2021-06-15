package model.cardtemplate;

public enum CardType {
    NORMAL("Normal"),
    EFFECT("Effect"),
    RITUAL("Ritual");

    private final String cardType;

    CardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardType() {
        return cardType;
    }
}
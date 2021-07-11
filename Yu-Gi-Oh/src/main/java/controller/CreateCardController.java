package controller;

import exception.GameErrorException;
import model.cardtemplate.*;

public class CreateCardController extends Controller {

    private int price = 0;
    private String cardType;
    private String cardName;

    public CreateCardController() {
    }

    public void cardType(String type) {
        switch (type) {
            case "Monster":
                price = 700;
                cardType = "Monster";
                break;
            case "Spell":
                price = 400;
                cardType = "Spell";
                break;
            case "Trap":
                price = 400;
                cardType = "Trap";
                break;
            default:
                throw new GameErrorException("card type is wrong");
        }
    }

    public void cardName(String name) {
        this.cardName = name;
    }

    public int getPrice() {
        return this.price;
    }

    public void createCard() {
        switch (cardType) {
            case "Monster":
                MonsterCard monsterCard = new MonsterCard(this.cardName, " ", Effect.NONE, 1, null,
                        CardType.NORMAL, MonsterType.BEAST_WARRIOR, 300, 300,
                        getClass().getResource("image/rawCard.png").toExternalForm(), this.price);
                user.removeCoins(70);
                break;
            case "Spell": {
                SpellTrapCard spellTrapCard = new SpellTrapCard(this.cardName, " ", Effect.NONE,
                        EffectType.NORMAL, Status.LIMITED, SpellTrapType.SPELL,
                        getClass().getResource("image/rawCard.png").toExternalForm(), this.price);
                user.removeCoins(40);
                break;
            }
            case "Trap": {
                SpellTrapCard spellTrapCard = new SpellTrapCard(this.cardName, " ", Effect.NONE,
                        EffectType.NORMAL, Status.LIMITED, SpellTrapType.TRAP,
                        getClass().getResource("image/rawCard.png").toExternalForm(), this.price);
                user.removeCoins(40);
                break;
            }
        }
    }
}

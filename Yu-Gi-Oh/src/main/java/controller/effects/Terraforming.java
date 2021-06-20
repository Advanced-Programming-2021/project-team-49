package controller.effects;

import controller.DuelController;
import controller.EffectController;
import exception.GameErrorException;
import model.cardtemplate.EffectType;
import model.cardtemplate.SpellTrapType;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.SpellTrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Terraforming extends EffectController {

    public Terraforming(Field field, DuelController controller) {
        super(field, controller);
    }

    public void action() {
        List<Card> fieldSpells = new ArrayList<>();
        for (Card card : field.getAttackerMat().getCardList(Location.DECK)) {
            if (card instanceof SpellTrap) {
                if (((SpellTrap) card).getType().equals(SpellTrapType.SPELL)
                        && ((SpellTrap) card).getEffectType().equals(EffectType.FIELD))
                    fieldSpells.add(card);
            }
        }
        if (fieldSpells.isEmpty())
            throw new GameErrorException("You don't have any field spell");

        int random = new Random().nextInt(fieldSpells.size() - 1);
        field.getAttackerMat().addCard(fieldSpells.get(random), Location.HAND);
        field.getAttackerMat().removeCard(fieldSpells.get(random), Location.DECK);
    }
}

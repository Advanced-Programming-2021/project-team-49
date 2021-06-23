package model.game.card;

import model.cardtemplate.EffectType;
import model.cardtemplate.SpellTrapCard;
import model.cardtemplate.SpellTrapType;
import model.cardtemplate.Status;

public class SpellTrap extends Card {

    private final SpellTrapCard spellTrapCard;

    public SpellTrap(SpellTrapCard card) {
        super(card);
        this.spellTrapCard = card;
    }

    public EffectType getEffectType() {
        return spellTrapCard.getEffectType();
    }

    public Status getStatus() {
        return spellTrapCard.getStatus();
    }

    public SpellTrapType getType() {
        return spellTrapCard.getType();
    }
}

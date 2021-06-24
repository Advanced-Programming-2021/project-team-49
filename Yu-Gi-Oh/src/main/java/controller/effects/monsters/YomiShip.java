package controller.effects.monsters;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;

public class YomiShip extends EffectHandler {

    private Card attacker;

    public YomiShip(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        activationRequirement();
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        int damage = ((Monster) attacker).getTotalAttack() - ((Monster) this.card).getTotalDefense();
        if (damage > 0)
            field.getAttackerMat().moveCard(Location.MONSTER_ZONE, attacker, Location.GRAVEYARD);
        //TODO handle removing the card if it suppose not to be handled in attack method
    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }

    public void setAttacker(Card card) {
        attacker = card;
        // TODO handle calling this method in attack
    }

}

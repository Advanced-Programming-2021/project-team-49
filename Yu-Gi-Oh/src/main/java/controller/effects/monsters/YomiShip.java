package controller.effects.monsters;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import exception.GameErrorException;
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
        if (!((Monster) card).isAttacker()) {
            if (damage > 0) {
                field.getAttackerMat().moveCard(Location.MONSTER_ZONE, attacker, Location.GRAVEYARD);
                field.getDefenderMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);
                throw new GameErrorException("The card has been destroyed by the effect of YomiShip");
            } else
                field.getAttackerMat().getPlayer().removeLifePoints(-damage);
        } else {
            if (damage >= 0) {
                field.getDefenderMat().getPlayer().removeLifePoints(damage);
                field.getAttackerMat().moveCard(Location.MONSTER_ZONE, attacker, Location.GRAVEYARD);
                field.getDefenderMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);
                throw new GameErrorException("The card has been destroyed by the effect of YomiShip");
            } else {
                field.getAttackerMat().moveCard(Location.MONSTER_ZONE, attacker, Location.GRAVEYARD);
                field.getAttackerMat().getPlayer().removeLifePoints(-damage);
            }
        }
    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }

    public void setAttacker(Card card) {
        attacker = card;
    }

}

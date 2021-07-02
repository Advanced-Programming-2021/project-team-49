package controller.effects.monsters;

import controller.DuelController;
import controller.effects.EffectHandler;
import controller.effects.Event;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;

public class Marshmallon extends EffectHandler {

    private Card attacker;

    public Marshmallon(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
       if (!((Monster) card).isAttacker()) {
           if (!card.isFaceUp()) {
               field.getAttackerMat().getPlayer().removeLifePoints(1000);
               card.setFaceUp();
           }
           int damage = ((Monster) attacker).getTotalAttack() - ((Monster) card).getTotalAttack();
           if (damage < 0)
               field.getAttackerMat().getPlayer().removeLifePoints(-damage);
       } else {
           if (!card.isFaceUp()) {
               card.setFaceUp();
               field.getAttackerMat().getPlayer().removeLifePoints(1000);
           }
           int damage = ((Monster) attacker).getTotalAttack() - ((Monster) card).getTotalAttack();
           if (damage < 0) {
               field.getAttackerMat().getPlayer().removeLifePoints(-damage);
               field.getAttackerMat().moveCard(Location.MONSTER_ZONE, attacker, Location.GRAVEYARD);
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
        this.attacker = attacker;
    }
}

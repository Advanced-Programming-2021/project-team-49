package controller.effects.monsters;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import exception.GameErrorException;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;

public class ExploderDragon extends EffectHandler {

    private Card enemy;
    private Card attacker;

    public ExploderDragon(int speed, Card card, Field field, DuelController controller) {
        super(speed, card, field, controller);
        activationRequirement();
    }

    @Override
    public void activationRequirement() {

    }

    @Override
    public void action() {
        if (enemy.getName().equals(attacker.getName()) && !((Monster)card).isAttacker()) {
            card.setFaceUp();
            field.getAttackerMat().moveCard(Location.MONSTER_ZONE, enemy, Location.GRAVEYARD);
            field.getDefenderMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);
            throw new GameErrorException("The card has been destroyed by the effect of Exploder Dragon");
        } else if (enemy.getName().equals(attacker.getName()) && ((Monster)card).isAttacker()){
            int damage = ((Monster) attacker).getTotalAttack() - ((Monster) card).getTotalAttack();
            card.setFaceUp();
            if (damage >= 0) {
                field.getAttackerMat().moveCard(Location.MONSTER_ZONE, enemy, Location.GRAVEYARD);
                field.getDefenderMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);
                throw new GameErrorException("The card has been destroyed by the effect of Exploder Dragon");
            } else {
                field.getAttackerMat().moveCard(Location.MONSTER_ZONE, attacker, Location.GRAVEYARD);
                field.getAttackerMat().getPlayer().removeLifePoints(-damage);
                throw new GameErrorException("The card has been destroyed by the effect of Exploder Dragon");
            }
        } else {
            int damage = ((Monster) card).getTotalAttack() - ((Monster) enemy).getTotalAttack();
            if (damage >= 0) {
                field.getDefenderMat().moveCard(Location.MONSTER_ZONE, enemy, Location.GRAVEYARD);
                field.getDefenderMat().getPlayer().removeLifePoints(damage);
                throw new GameErrorException("The card has been destroyed by the effect of Exploder Dragon");
            } else {
                field.getAttackerMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);
                field.getDefenderMat().moveCard(Location.MONSTER_ZONE, enemy, Location.GRAVEYARD);
                throw new GameErrorException("The card has been destroyed by the effect of Exploder Dragon");
            }
        }

    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }

    public void setCards(Card enemy, Card attacker) {
        this.enemy= enemy;
        this.attacker = attacker;

    }
}

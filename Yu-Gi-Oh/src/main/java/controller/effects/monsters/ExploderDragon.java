package controller.effects.monsters;

import controller.DuelController;
import controller.EffectHandler;
import controller.effects.Event;
import model.game.Field;
import model.game.Location;
import model.game.card.Card;
import model.game.card.Monster;

public class ExploderDragon extends EffectHandler {

    private Card enemyToBeDestroyed;
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
        if (enemyToBeDestroyed.getName().equals(attacker.getName()) && !((Monster)card).isAttacker()) {
            card.setFaceUp();
            field.getAttackerMat().moveCard(Location.MONSTER_ZONE, enemyToBeDestroyed, Location.GRAVEYARD);
            field.getDefenderMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);
        } else if (enemyToBeDestroyed.getName().equals(attacker.getName()) && ((Monster)card).isAttacker()){
            int damage = ((Monster) attacker).getTotalAttack() - ((Monster) card).getTotalAttack();
            card.setFaceUp();
            if (damage >= 0) {
                field.getAttackerMat().moveCard(Location.MONSTER_ZONE, enemyToBeDestroyed, Location.GRAVEYARD);
                field.getDefenderMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);
                //TODO show in duel view
            } else {
                field.getAttackerMat().moveCard(Location.MONSTER_ZONE, attacker, Location.GRAVEYARD);
                field.getAttackerMat().getPlayer().removeLifePoints(-damage);
                //TODO show in duel view
            }
        } else {
            int damage = ((Monster) card).getTotalAttack() - ((Monster) enemyToBeDestroyed).getTotalAttack();
            if (damage >= 0) {
                field.getDefenderMat().moveCard(Location.MONSTER_ZONE, enemyToBeDestroyed, Location.GRAVEYARD);
                field.getDefenderMat().getPlayer().removeLifePoints(damage);
                //TODO show in duel view
            } else {
                field.getAttackerMat().moveCard(Location.MONSTER_ZONE, card, Location.GRAVEYARD);
                field.getDefenderMat().moveCard(Location.MONSTER_ZONE, enemyToBeDestroyed, Location.GRAVEYARD);
                //TODO show in duel view
            }
        }

    }

    @Override
    public void notifier(Event event) {

    }

    @Override
    public void deActivate() {

    }

    public void getRequirements(Card enemy, Card attacker) {
        enemyToBeDestroyed = enemy;
        this.attacker = attacker;

    }
}

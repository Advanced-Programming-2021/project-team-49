package model.card;

public class Trap extends CardTemplate {

    private final EffectType effectType;

    public Trap(String name, String number, String description, EffectType effectType) {
        super(name, number, description);
        this.effectType = effectType;
    }
}

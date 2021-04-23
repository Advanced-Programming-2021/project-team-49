package model.card;

public class Trap extends CardTemplate {

    private final EffectType effectType;

    public Trap(String name, int number, String description, Effect effect, EffectType effectType) {
        super(name, number, description, effect);
        this.effectType = effectType;
    }

    public EffectType getEffectType() {
        return effectType;
    }
}

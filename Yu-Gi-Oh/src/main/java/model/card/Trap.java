package model.card;

public class Trap extends CardTemplate {

    private final EffectType effectType;

    public Trap(String name, String description, Effect effect, EffectType effectType) {
        super(name, description, effect);
        this.effectType = effectType;
    }

    public EffectType getEffectType() {
        return effectType;
    }
}

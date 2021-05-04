package model.card;

public class Spell extends CardTemplate {

    private final EffectType effectType;

    public Spell(String name, String description, Effect effect, EffectType effectType) {
        super(name, description, effect);
        this.effectType = effectType;
    }

    public EffectType getEffectType() {
        return effectType;
    }
}
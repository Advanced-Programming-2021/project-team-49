package model.card;

public class Spell extends CardTemplate {

    private final EffectType effectType;

    public Spell(String name, String number, String description, Effect effect, EffectType effectType) {
        super(name, number, description, effect);
        this.effectType = effectType;
    }

    public EffectType getEffectType() {
        return effectType;
    }
}
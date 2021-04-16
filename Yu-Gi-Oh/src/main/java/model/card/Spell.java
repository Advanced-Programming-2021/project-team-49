package model.card;

public class Spell extends CardTemplate {

    private final EffectType effectType;

    public Spell(String name, String number, String description, EffectType effectType) {
        super(name, number, description);
        this.effectType = effectType;
    }

    public EffectType getEffectType() {
        return effectType;
    }
}
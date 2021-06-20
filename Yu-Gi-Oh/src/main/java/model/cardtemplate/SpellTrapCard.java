package model.cardtemplate;

public class SpellTrapCard extends Card {

    private final EffectType effectType;
    private final Status status;
    private final Type type;

    public SpellTrapCard(String name, String description, Effect effect, EffectType effectType,
                         Status status, Type type, int price) {
        super(name, description, effect, price);
        this.effectType = effectType;
        this.status = status;
        this.type = type;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    public Status getStatus() {
        return status;
    }

    public Type getType() {
        return type;
    }
}
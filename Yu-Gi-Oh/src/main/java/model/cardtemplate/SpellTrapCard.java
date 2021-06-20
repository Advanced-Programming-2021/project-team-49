package model.cardtemplate;

public class SpellTrapCard extends CardTemplate {

    private final EffectType effectType;
    private final Status status;
    private final SpellTrapType type;

    public SpellTrapCard(String name, String description, Effect effect, EffectType effectType,
                         Status status, SpellTrapType type, int price) {
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

    public SpellTrapType getType() {
        return type;
    }
}
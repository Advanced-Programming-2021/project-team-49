package model.card;

public class Trap extends CardTemplate {

    private final EffectType effectType;
    private final Status status;

    public Trap(String name, String description, Effect effect, EffectType effectType, Status status) {
        super(name, description, effect);
        this.effectType = effectType;
        this.status = status;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    public Status getStatus() {
        return status;
    }
}

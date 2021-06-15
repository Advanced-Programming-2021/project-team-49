package model.cardtemplate;

public enum EffectType {
    NORMAL("Normal"),
    EQUIP("Equip"),
    FIELD("Field"),
    QUICK_PLAY("Quick-play"),
    RITUAL("Ritual"),
    CONTINUOUS("Continuous"),
    COUNTER("Counter");

    private final String effectType;

    EffectType(String effectType) {
        this.effectType = effectType;
    }

    public String getEffectType() {
        return effectType;
    }
}

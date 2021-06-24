package controller.effects;

public enum Event {
    STANDBY_PHASE,
    END_TURN,
    A_MONSTER_DESTROYED,
    A_SPELL_ACTIVATED,
    DECLARED_ATTACK
    // TODO call the fieldZoneSpell.action() each standby phase
    // TODO need to call notifyEffects in effectController for Events
}

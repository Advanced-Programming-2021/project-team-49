package controller.effects;

public enum Event {
    STANDBY_PHASE,
    END_TURN,
    A_MONSTER_DESTROYED,
    A_SPELL_ACTIVATED
    // TODO need to call notifyEffects in effectController for Events
}

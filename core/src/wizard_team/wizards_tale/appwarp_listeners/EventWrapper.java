package wizard_team.wizards_tale.appwarp_listeners;

public class EventWrapper {
    public final EventType type;
    public final Object event;

    public EventWrapper(EventType type, Object event) {
        this.type = type;
        this.event = event;
    }
}

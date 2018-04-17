package wizard_team.wizards_tale;

import wizard_team.wizards_tale.appwarp_listeners.WTChatRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTConnectionListener;
import wizard_team.wizards_tale.appwarp_listeners.WTLobbyRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTNotificationListener;
import wizard_team.wizards_tale.appwarp_listeners.WTRoomRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTUpdateRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTZoneRequestListener;

public class AWListeners {
    public final WTChatRequestListener chatRequestListener;
    public final WTConnectionListener connectionListener;
    public final WTLobbyRequestListener lobbyRequestListener;
    public final WTNotificationListener notificationListener;
    public final WTRoomRequestListener roomRequestListener;
    public final WTUpdateRequestListener updateRequestListener;
    public final WTZoneRequestListener zoneRequestListener;

    public AWListeners() {
        this.chatRequestListener = new WTChatRequestListener();
        this.connectionListener = new WTConnectionListener();
        this.lobbyRequestListener = new WTLobbyRequestListener();
        this.notificationListener = new WTNotificationListener();
        this.roomRequestListener = new WTRoomRequestListener();
        this.updateRequestListener = new WTUpdateRequestListener();
        this.zoneRequestListener = new WTZoneRequestListener();
    }

}

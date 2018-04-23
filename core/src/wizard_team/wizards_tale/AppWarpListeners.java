package wizard_team.wizards_tale;

import com.shephertz.app42.gaming.multiplayer.client.listener.NotifyListener;

import wizard_team.wizards_tale.appwarp_listeners.WTChatRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTConnectionRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTLobbyRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTNotificationListener;
import wizard_team.wizards_tale.appwarp_listeners.WTRoomRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTUpdateRequestListener;
import wizard_team.wizards_tale.appwarp_listeners.WTZoneRequestListener;

public class AppWarpListeners {
    public WTChatRequestListener chatRequestListener;
    public WTConnectionRequestListener connectionRequestListener;
    public WTLobbyRequestListener lobbyRequestListener;
    public WTNotificationListener notifyListener;
    public WTRoomRequestListener roomRequestListener;
    public WTZoneRequestListener zoneRequestListener;
    public WTUpdateRequestListener updateRequestListener;


    public AppWarpListeners() {
        this.chatRequestListener = new WTChatRequestListener();
        this.connectionRequestListener = new WTConnectionRequestListener();
        this.lobbyRequestListener = new WTLobbyRequestListener();
        this.notifyListener = new WTNotificationListener();
        this.roomRequestListener = new WTRoomRequestListener();
        this.zoneRequestListener = new WTZoneRequestListener();
        this.updateRequestListener = new WTUpdateRequestListener();
    }

}

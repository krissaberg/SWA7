package wizard_team.wizards_tale.appwarp_listeners;

import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

import java.util.Observable;

public class WTConnectionRequestListener extends Observable implements ConnectionRequestListener {
    @Override
    public void onConnectDone(ConnectEvent connectEvent) {

    }

    @Override
    public void onDisconnectDone(ConnectEvent connectEvent) {

    }

    @Override
    public void onInitUDPDone(byte b) {

    }
}

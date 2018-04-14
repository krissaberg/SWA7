package wizard_team.wizards_tale.appwarp_listeners;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

public class WTConnectionListener implements ConnectionRequestListener {
    @Override
    public void onConnectDone(ConnectEvent connectEvent) {
        if(connectEvent.getResult() == WarpResponseResultCode.SUCCESS) {
            System.out.println("yay woo stuff works");
        }
    }

    @Override
    public void onDisconnectDone(ConnectEvent connectEvent) {
        System.out.println("asdqwqwdqwd :(");
    }

    @Override
    public void onInitUDPDone(byte b) {

    }
}

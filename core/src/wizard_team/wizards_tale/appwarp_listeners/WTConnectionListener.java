package wizard_team.wizards_tale.appwarp_listeners;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WTConnectionListener implements ConnectionRequestListener {
    @Override
    public void onConnectDone(ConnectEvent connectEvent) {
        if(connectEvent.getResult() == WarpResponseResultCode.SUCCESS) {
            System.out.println("Successfully connected");
        } else {
            System.out.print("Connection error. Error code: ");
            System.out.println(connectEvent.getResult());
        }
    }

    @Override
    public void onDisconnectDone(ConnectEvent connectEvent) {
        if(connectEvent.getResult() == WarpResponseResultCode.SUCCESS) {
            System.out.println("Successfully disconnected");
        } else {
            System.out.print("Disconnection error. Error code: ");
            System.out.println(connectEvent.getResult());
        }
    }

    @Override
    public void onInitUDPDone(byte b) {
        throw new NotImplementedException();
    }
}

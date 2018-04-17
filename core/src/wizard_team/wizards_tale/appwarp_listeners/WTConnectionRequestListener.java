package wizard_team.wizards_tale.appwarp_listeners;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

import java.util.ArrayList;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WTConnectionRequestListener implements ConnectionRequestListener {
    private ArrayList<ConnectionEventListener> observers = new ArrayList<ConnectionEventListener>();

    public void registerObserver(ConnectionEventListener observer) {
        observers.add(observer);
    }

    public void unregisterObserver(ConnectionEventListener observer) {
        observers.remove(observer);
    }

    private void notify(ConnectEvent event) {
        System.out.println("notifying about " + event.toString());
        for(ConnectionEventListener o : observers) {
            o.handle(event);
        }
    }

    @Override
    public void onConnectDone(ConnectEvent connectEvent) {
        notify(connectEvent);
    }

    @Override
    public void onDisconnectDone(ConnectEvent connectEvent) {
        notify(connectEvent);
    }

    @Override
    public void onInitUDPDone(byte b) {
        throw new NotImplementedException();
    }
}

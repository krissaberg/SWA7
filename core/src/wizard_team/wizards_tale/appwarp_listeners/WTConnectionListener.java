package wizard_team.wizards_tale.appwarp_listeners;

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

import java.util.ArrayList;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WTConnectionListener implements ConnectionRequestListener {
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
//        switch (connectEvent.getResult()) {
//            case WarpResponseResultCode.SUCCESS:
//                System.out.println("Successfully connected");
//                break;
//            case WarpResponseResultCode.AUTH_ERROR:
//                System.out.println("Auth error");
//                break;
//            case WarpResponseResultCode.AUTO_RECOVERING:
//                System.out.println("Auto recovering");
//                break;
//            case WarpResponseResultCode.BAD_REQUEST:
//                System.out.println("Bad request");
//                break;
//            case WarpResponseResultCode.CONNECTION_ERROR:
//                System.out.println("Connection error");
//                break;
//            default:
//                System.out.println("Other error: " + connectEvent.getResult());
//        }
        notify(connectEvent);
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

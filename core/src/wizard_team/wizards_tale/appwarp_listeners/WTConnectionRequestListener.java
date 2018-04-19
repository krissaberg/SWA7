package wizard_team.wizards_tale.appwarp_listeners;

import com.badlogic.gdx.Gdx;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

import org.javatuples.Pair;
import org.javatuples.Unit;

import java.util.Observable;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WTConnectionRequestListener extends Observable implements ConnectionRequestListener {

    @Override
    public void onConnectDone(ConnectEvent connectEvent) {
        setChanged();
        notifyObservers(Pair.with(ConnectionRequestEventType.CONNECT_DONE, connectEvent));
    }

    @Override
    public void onDisconnectDone(ConnectEvent connectEvent) {
        setChanged();
        notifyObservers(Pair.with(ConnectionRequestEventType.DISCONNECT_DONE, connectEvent));
    }

    @Override
    public void onInitUDPDone(byte b) {
        throw new NotImplementedException();
    }
}

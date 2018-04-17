package wizard_team.wizards_tale.appwarp_listeners;

import com.badlogic.gdx.Gdx;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

import java.util.ArrayList;
import java.util.Observable;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WTConnectionRequestListener extends Observable implements ConnectionRequestListener  {

    @Override
    public void onConnectDone(ConnectEvent connectEvent) {
        Gdx.app.log("CRL", "onConnectDone");
        setChanged();
        notifyObservers(connectEvent);
    }

    @Override
    public void onDisconnectDone(ConnectEvent connectEvent) {
        Gdx.app.log("CRL", "onDisconnectDone");
        setChanged();
        notifyObservers(connectEvent);
    }

    @Override
    public void onInitUDPDone(byte b) {
        throw new NotImplementedException();
    }
}

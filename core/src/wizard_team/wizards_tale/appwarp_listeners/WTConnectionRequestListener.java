package wizard_team.wizards_tale.appwarp_listeners;

import com.badlogic.gdx.Gdx;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;

import org.javatuples.Pair;

import java.util.Observable;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WTConnectionRequestListener extends Observable implements ConnectionRequestListener {
    private String tag = "ConnReqL";

    @Override
    public void onConnectDone(ConnectEvent connectEvent) {
        setChanged();
        notifyObservers(Pair.with(ConnReqEvent.CONNECT_DONE, connectEvent));

        if(connectEvent.getResult() == WarpResponseResultCode.SUCCESS) {
            try {
                WarpClient.getInstance().joinLobby();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDisconnectDone(ConnectEvent connectEvent) {
        setChanged();
        notifyObservers(Pair.with(ConnReqEvent.DISCONNECT_DONE, connectEvent));
        throw new NotImplementedException();
    }

    @Override
    public void onInitUDPDone(byte b) {

        throw new NotImplementedException();
    }
}

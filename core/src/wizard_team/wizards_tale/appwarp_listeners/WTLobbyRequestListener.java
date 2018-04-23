package wizard_team.wizards_tale.appwarp_listeners;

import com.badlogic.gdx.Gdx;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.LobbyRequestListener;

import java.util.Observable;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WTLobbyRequestListener extends Observable implements LobbyRequestListener {
    private String tag = "LobbyReqL";

    @Override
    public void onJoinLobbyDone(LobbyEvent lobbyEvent) {
        Gdx.app.log(tag, lobbyEvent.getInfo().toString());
        try {
            WarpClient.getInstance().subscribeLobby();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLeaveLobbyDone(LobbyEvent lobbyEvent) {

        throw new NotImplementedException();
    }

    @Override
    public void onSubscribeLobbyDone(LobbyEvent lobbyEvent) {

    }

    @Override
    public void onUnSubscribeLobbyDone(LobbyEvent lobbyEvent) {

        throw new NotImplementedException();
    }

    @Override
    public void onGetLiveLobbyInfoDone(LiveRoomInfoEvent liveRoomInfoEvent) {

        throw new NotImplementedException();
    }
}

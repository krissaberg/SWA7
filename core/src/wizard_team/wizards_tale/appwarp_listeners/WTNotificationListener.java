package wizard_team.wizards_tale.appwarp_listeners;

import com.badlogic.gdx.Gdx;
import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyData;
import com.shephertz.app42.gaming.multiplayer.client.events.MoveEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.UpdateEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.NotifyListener;

import org.javatuples.Pair;

import java.util.HashMap;
import java.util.Observable;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WTNotificationListener extends Observable implements NotifyListener {
    private String tag = "NotifyList";

    @Override
    public void onRoomCreated(RoomData roomData) {

        throw new NotImplementedException();
    }

    @Override
    public void onRoomDestroyed(RoomData roomData) {

        throw new NotImplementedException();
    }

    @Override
    public void onUserLeftRoom(RoomData roomData, String s) {

        throw new NotImplementedException();
    }

    @Override
    public void onUserJoinedRoom(RoomData roomData, String s) {

        throw new NotImplementedException();
    }

    @Override
    public void onUserLeftLobby(LobbyData lobbyData, String s) {

        throw new NotImplementedException();
    }

    @Override
    public void onUserJoinedLobby(LobbyData lobbyData, String s) {
        Gdx.app.log(tag, "User joined " + s);
        throw new NotImplementedException();
    }

    @Override
    public void onChatReceived(ChatEvent chatEvent) {
        Gdx.app.log(tag, chatEvent.getSender() + ": " + chatEvent.getMessage());
        setChanged();
        notifyObservers(Pair.with(NotifyEvent.CHAT_RECEIVED,
                chatEvent));
    }

    @Override
    public void onPrivateChatReceived(String s, String s1) {

        throw new NotImplementedException();
    }

    @Override
    public void onPrivateUpdateReceived(String s, byte[] bytes, boolean b) {

        throw new NotImplementedException();
    }

    @Override
    public void onUpdatePeersReceived(UpdateEvent updateEvent) {

        throw new NotImplementedException();
    }

    @Override
    public void onUserChangeRoomProperty(RoomData roomData, String s, HashMap<String, Object> hashMap, HashMap<String, String> hashMap1) {

        throw new NotImplementedException();
    }

    @Override
    public void onMoveCompleted(MoveEvent moveEvent) {

        throw new NotImplementedException();
    }

    @Override
    public void onGameStarted(String s, String s1, String s2) {

        throw new NotImplementedException();
    }

    @Override
    public void onGameStopped(String s, String s1) {

        throw new NotImplementedException();
    }

    @Override
    public void onUserPaused(String s, boolean b, String s1) {

        throw new NotImplementedException();
    }

    @Override
    public void onUserResumed(String s, boolean b, String s1) {

        throw new NotImplementedException();
    }

    @Override
    public void onNextTurnRequest(String s) {

        throw new NotImplementedException();
    }
}

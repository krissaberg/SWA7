package wizard_team.wizards_tale.appwarp_listeners;

import com.badlogic.gdx.Gdx;
import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyData;
import com.shephertz.app42.gaming.multiplayer.client.events.MoveEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.UpdateEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.NotifyListener;

import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Unit;

import java.util.HashMap;
import java.util.Observable;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WTNotificationListener extends Observable implements NotifyListener {
    public static final String tag = "NotificationListener";

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
        setChanged();
        notifyObservers(Pair.with(NotificationEventType.USER_LEFT_ROOM, s));
    }

    @Override
    public void onUserJoinedRoom(RoomData roomData, String s) {
        setChanged();
        notifyObservers(Pair.with(NotificationEventType.USER_JOINED_ROOM, s));
    }

    @Override
    public void onUserLeftLobby(LobbyData lobbyData, String s) {
        throw new NotImplementedException();
    }

    @Override
    public void onUserJoinedLobby(LobbyData lobbyData, String s) {
        throw new NotImplementedException();
    }

    @Override
    public void onChatReceived(ChatEvent chatEvent) {
        setChanged();
        notifyObservers(Pair.with(NotificationEventType.CHAT_RECEIVED, chatEvent));
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
        setChanged();
        notifyObservers(Pair.with(NotificationEventType.UPDATE_PEERS_RECEIVED, updateEvent));
    }

    @Override
    public void onUserChangeRoomProperty(RoomData roomData, String s, HashMap<String, Object> hashMap, HashMap<String, String> hashMap1) {
        setChanged();
        notifyObservers(Quintet.with(NotificationEventType.USER_CHANGE_ROOM_PROPERTY,
                roomData, s, hashMap, hashMap1));
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

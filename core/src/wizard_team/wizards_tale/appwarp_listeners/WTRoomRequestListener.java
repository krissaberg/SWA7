package wizard_team.wizards_tale.appwarp_listeners;

import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;

import java.util.Observable;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WTRoomRequestListener extends Observable implements RoomRequestListener {
    @Override
    public void onSubscribeRoomDone(RoomEvent roomEvent) {
        hasChanged();
        notifyObservers(roomEvent);
    }

    @Override
    public void onUnSubscribeRoomDone(RoomEvent roomEvent) {
        hasChanged();
        notifyObservers(roomEvent);
    }

    @Override
    public void onJoinRoomDone(RoomEvent roomEvent) {
        hasChanged();
        notifyObservers(roomEvent);
    }

    @Override
    public void onLeaveRoomDone(RoomEvent roomEvent) {
        hasChanged();
        notifyObservers(roomEvent);
    }

    @Override
    public void onGetLiveRoomInfoDone(LiveRoomInfoEvent liveRoomInfoEvent) {
        hasChanged();
        notifyObservers(liveRoomInfoEvent);
    }

    @Override
    public void onSetCustomRoomDataDone(LiveRoomInfoEvent liveRoomInfoEvent) {
        hasChanged();
        notifyObservers(liveRoomInfoEvent);
    }

    @Override
    public void onUpdatePropertyDone(LiveRoomInfoEvent liveRoomInfoEvent) {
        hasChanged();
        notifyObservers(liveRoomInfoEvent);
    }

    @Override
    public void onLockPropertiesDone(byte b) {
        throw new NotImplementedException();
    }

    @Override
    public void onUnlockPropertiesDone(byte b) {
        throw new NotImplementedException();
    }

    @Override
    public void onJoinAndSubscribeRoomDone(RoomEvent roomEvent) {
        hasChanged();
        notifyObservers(roomEvent);
    }

    @Override
    public void onLeaveAndUnsubscribeRoomDone(RoomEvent roomEvent) {
        hasChanged();
        notifyObservers(roomEvent);
    }
}


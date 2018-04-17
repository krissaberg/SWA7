package wizard_team.wizards_tale.appwarp_listeners;

import com.badlogic.gdx.Gdx;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;

import java.util.Observable;
import java.util.Observer;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WTRoomRequestListener extends Observable implements RoomRequestListener {
    private String tag = "RoomReqListener";

    @Override
    public void onSubscribeRoomDone(RoomEvent roomEvent) {
        setChanged();
        notifyObservers(roomEvent);
    }

    @Override
    public void onUnSubscribeRoomDone(RoomEvent roomEvent) {
        setChanged();
        notifyObservers(roomEvent);
    }

    @Override
    public void onJoinRoomDone(RoomEvent roomEvent) {
        setChanged();
        notifyObservers(roomEvent);
    }

    @Override
    public void onLeaveRoomDone(RoomEvent roomEvent) {
        setChanged();
        notifyObservers(roomEvent);
    }

    @Override
    public void onGetLiveRoomInfoDone(LiveRoomInfoEvent liveRoomInfoEvent) {
        setChanged();
        notifyObservers(liveRoomInfoEvent);
    }

    @Override
    public void onSetCustomRoomDataDone(LiveRoomInfoEvent liveRoomInfoEvent) {
        setChanged();
        notifyObservers(liveRoomInfoEvent);
    }

    @Override
    public void onUpdatePropertyDone(LiveRoomInfoEvent liveRoomInfoEvent) {
        setChanged();
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
        setChanged();
        EventWrapper eventWrapper = new EventWrapper(
                EventType.JOIN_AND_SUBSCRIBE,
                roomEvent);
        notifyObservers(eventWrapper);
    }

    @Override
    public void onLeaveAndUnsubscribeRoomDone(RoomEvent roomEvent) {
        setChanged();
        notifyObservers(new EventWrapper(
                EventType.JOIN_AND_SUBSCRIBE,
                roomEvent));
    }

}


package wizard_team.wizards_tale.appwarp_listeners;

import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;

import org.javatuples.Pair;

import java.util.Observable;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WTRoomRequestListener extends Observable implements RoomRequestListener {
    private String tag = "RoomReqListener";

    @Override
    public void onSubscribeRoomDone(RoomEvent roomEvent) {
        setChanged();
        notifyObservers(Pair.with(RoomRequestEventType.SUBSCRIBE_ROOM_DONE, roomEvent));
    }

    @Override
    public void onUnSubscribeRoomDone(RoomEvent roomEvent) {
        setChanged();
        notifyObservers(Pair.with(RoomRequestEventType.UNSUBSCRIBE_ROOM_DONE, roomEvent));
    }

    @Override
    public void onJoinRoomDone(RoomEvent roomEvent) {
        setChanged();
        notifyObservers(Pair.with(RoomRequestEventType.JOIN_ROOM_DONE, roomEvent));
    }

    @Override
    public void onLeaveRoomDone(RoomEvent roomEvent) {
        setChanged();
        notifyObservers(Pair.with(RoomRequestEventType.LEAVE_ROOM_DONE, roomEvent));
    }

    @Override
    public void onGetLiveRoomInfoDone(LiveRoomInfoEvent liveRoomInfoEvent) {
        setChanged();
        notifyObservers(Pair.with(RoomRequestEventType.GET_LIVE_ROOM_INFO, liveRoomInfoEvent));
    }

    @Override
    public void onSetCustomRoomDataDone(LiveRoomInfoEvent liveRoomInfoEvent) {
        setChanged();
        notifyObservers(Pair.with(RoomRequestEventType.SET_CUSTOM_ROOM_DATA, liveRoomInfoEvent));
    }

    @Override
    public void onUpdatePropertyDone(LiveRoomInfoEvent liveRoomInfoEvent) {
        setChanged();
        notifyObservers(Pair.with(RoomRequestEventType.UPDATE_PROPERTY_DONE, liveRoomInfoEvent));
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
        notifyObservers(Pair.with(RoomRequestEventType.JOIN_AND_SUBSCRIBE, roomEvent));
    }

    @Override
    public void onLeaveAndUnsubscribeRoomDone(RoomEvent roomEvent) {
        setChanged();
        notifyObservers(Pair.with(RoomRequestEventType.LEAVE_AND_SUBSCRIBE, roomEvent));
    }

}


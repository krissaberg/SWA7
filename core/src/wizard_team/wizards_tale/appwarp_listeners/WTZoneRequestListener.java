package wizard_team.wizards_tale.appwarp_listeners;

import com.shephertz.app42.gaming.multiplayer.client.events.AllRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.AllUsersEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveUserInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.MatchedRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ZoneRequestListener;

import org.javatuples.Pair;

import java.util.Observable;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WTZoneRequestListener extends Observable implements ZoneRequestListener {
    @Override
    public void onDeleteRoomDone(RoomEvent roomEvent) {
        throw new NotImplementedException();
    }

    @Override
    public void onGetAllRoomsDone(AllRoomsEvent allRoomsEvent) {
        setChanged();
        notifyObservers(Pair.with(ZoneRequestEventType.GET_ALL_ROOMS_DONE, allRoomsEvent));
    }

    @Override
    public void onCreateRoomDone(RoomEvent roomEvent) {
        setChanged();
        notifyObservers(Pair.with(ZoneRequestEventType.CREATE_ROOM_DONE, roomEvent));
    }

    @Override
    public void onGetOnlineUsersDone(AllUsersEvent allUsersEvent) {
        throw new NotImplementedException();
    }

    @Override
    public void onGetLiveUserInfoDone(LiveUserInfoEvent liveUserInfoEvent) {
        throw new NotImplementedException();
    }

    @Override
    public void onSetCustomUserDataDone(LiveUserInfoEvent liveUserInfoEvent) {
        throw new NotImplementedException();
    }

    @Override
    public void onGetMatchedRoomsDone(MatchedRoomsEvent matchedRoomsEvent) {
        throw new NotImplementedException();
    }

    @Override
    public void onGetAllRoomsCountDone(AllRoomsEvent allRoomsEvent) {
        setChanged();
        notifyObservers(Pair.with(ZoneRequestEventType.GET_ALL_ROOMS_COUNT_DONE, allRoomsEvent));
    }

    @Override
    public void onGetOnlineUsersCountDone(AllUsersEvent allUsersEvent) {
        throw new NotImplementedException();
    }

    @Override
    public void onGetUserStatusDone(LiveUserInfoEvent liveUserInfoEvent) {
        throw new NotImplementedException();
    }
}

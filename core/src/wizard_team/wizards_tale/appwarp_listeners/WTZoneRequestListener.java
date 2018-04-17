package wizard_team.wizards_tale.appwarp_listeners;

import com.badlogic.gdx.Gdx;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.AllRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.AllUsersEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveUserInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.MatchedRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ZoneRequestListener;

import java.util.Observable;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WTZoneRequestListener extends Observable implements ZoneRequestListener {
    @Override
    public void onDeleteRoomDone(RoomEvent roomEvent) {
        throw new NotImplementedException();
    }

    @Override
    public void onGetAllRoomsDone(AllRoomsEvent allRoomsEvent) {
        Gdx.app.log("ROOMS", "onGetAllRoomsDone");
        setChanged();
        notifyObservers(allRoomsEvent);
    }

    @Override
    public void onCreateRoomDone(RoomEvent roomEvent) {
        System.out.println("Room created");
        System.out.println(roomEvent.getResult());
    }

    @Override
    public void onGetOnlineUsersDone(AllUsersEvent allUsersEvent) {
        if(allUsersEvent.getResult() == WarpResponseResultCode.SUCCESS) {
            System.out.println("Got online users");
            for (String s : allUsersEvent.getUserNames()) {
                System.out.println(s);
            }
        }
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
        hasChanged();
        notifyObservers(allRoomsEvent);
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

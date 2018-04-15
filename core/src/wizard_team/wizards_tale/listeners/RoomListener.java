package wizard_team.wizards_tale.listeners;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;

import wizard_team.wizards_tale.WarpController;


public class RoomListener implements RoomRequestListener{

    private WarpController container;
    public RoomListener(WarpController container) {
        this.container = container;
    }

    @Override
    public void onUpdatePropertyDone(LiveRoomInfoEvent roominfo)
    {
        container.appendResponseResult("UpdatePropertyDone "+roominfo.getProperties().size());
    }

    @Override
    public void onLockPropertiesDone(byte bt)
    {
        container.appendResponseResult("LockPropertiesDone "+bt);
    }

    @Override
    public void onUnlockPropertiesDone(byte bt)
    {
        container.appendResponseResult("UnlockPropertiesDone "+bt);
    }

    @Override
    public void onJoinAndSubscribeRoomDone(RoomEvent roomEvent) {

    }

    @Override
    public void onLeaveAndUnsubscribeRoomDone(RoomEvent roomEvent) {

    }

    @Override
    public void onSubscribeRoomDone(RoomEvent event) {
        container.appendResponseResult("SubscribeRoom "+event.getData().getName());
    }

    @Override
    public void onUnSubscribeRoomDone(RoomEvent event) {
        container.appendResponseResult("UnSubscribeRoom "+event.getData().getName());
    }

    @Override
    public void onJoinRoomDone(RoomEvent event) {
        container.appendResponseResult("onJoinRoom "+event.getResult());
    }

    @Override
    public void onLeaveRoomDone(RoomEvent event) {
        container.appendResponseResult("Leave Room "+event.getData().getName());
    }

    @Override
    public void onGetLiveRoomInfoDone(LiveRoomInfoEvent event) {
        if(event.getResult() == WarpResponseResultCode.SUCCESS){
            String[] users = event.getJoinedUsers();
            String result = "";
            container.appendResponseResult(event.getData().getName()+"(Room Id= " +event.getData().getId()+") Total users "+users.length);
            //for(int i=0; i<users.length; i++){
            //    result += " "+users[i];
            // }
            // container.appendResponseResult(result);
        }
    }

    @Override
    public void onSetCustomRoomDataDone(LiveRoomInfoEvent event) {
        container.appendResponseResult("CustomRoomData "+event.getData().getName()+" "+event.getCustomData().toString());
    }
}
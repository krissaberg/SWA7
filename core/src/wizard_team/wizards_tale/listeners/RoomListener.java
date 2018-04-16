package wizard_team.wizards_tale.listeners;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;

import wizard_team.wizards_tale.WarpController;


public class RoomListener implements RoomRequestListener{

    private WarpController controller;
    public RoomListener(WarpController controller) {
        this.controller = controller;
    }

    @Override
    public void onUpdatePropertyDone(LiveRoomInfoEvent roominfo)
    {
        controller.printResponseResult("UpdatePropertyDone "+roominfo.getProperties().size());
    }

    @Override
    public void onLockPropertiesDone(byte bt)
    {
        controller.printResponseResult("LockPropertiesDone "+bt);
    }

    @Override
    public void onUnlockPropertiesDone(byte bt)
    {
        controller.printResponseResult("UnlockPropertiesDone "+bt);
    }

    @Override
    public void onJoinAndSubscribeRoomDone(RoomEvent roomEvent) {
        controller.printResponseResult("Joined and Subscribed");
        //TODO Add method for creating room if Room is full
    }

    @Override
    public void onLeaveAndUnsubscribeRoomDone(RoomEvent roomEvent) {

    }

    @Override
    public void onSubscribeRoomDone(RoomEvent event) {
        controller.printResponseResult("SubscribeRoom "+event.getData().getName());
    }

    @Override
    public void onUnSubscribeRoomDone(RoomEvent event) {
        controller.printResponseResult("UnSubscribeRoom "+event.getData().getName());
    }

    @Override
    public void onJoinRoomDone(RoomEvent event) {
        controller.printResponseResult("onJoinRoom "+event.getResult());
    }

    @Override
    public void onLeaveRoomDone(RoomEvent event) {
        controller.printResponseResult("Leave Room "+event.getData().getName());
    }

    @Override
    public void onGetLiveRoomInfoDone(LiveRoomInfoEvent event) {
        if(event.getResult() == WarpResponseResultCode.SUCCESS){
            String[] users = event.getJoinedUsers();
            String result = "";
            controller.printResponseResult(event.getData().getName()+"(Room Id= " + event.getData().getId()+") Total users "+users.length);
            for (int i=0; i<users.length; i++) {
                result += " " + users[i];
             }
            controller.printResponseResult(result);
            //TODO Start game when enough users have joined.
        }
    }

    @Override
    public void onSetCustomRoomDataDone(LiveRoomInfoEvent event) {
        controller.printResponseResult("CustomRoomData "+event.getData().getName()+" "+event.getCustomData().toString());
    }
}
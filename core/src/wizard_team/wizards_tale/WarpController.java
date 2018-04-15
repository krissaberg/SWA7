package wizard_team.wizards_tale;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import wizard_team.wizards_tale.listeners.ConnectionListener;
import wizard_team.wizards_tale.listeners.RoomListener;
import wizard_team.wizards_tale.listeners.ZoneListener;
import wizard_team.wizards_tale.listeners.NotificationListener;


public class WarpController {
    private static String apiKey = "00b15641b47f119158942b601cb2dccf0a7bd3836dbe4509a3ba2bf9c983d06f";
    private static String secretKey = "10dcaa6a05b6f0cb0196820fe9cd352ca053887368c15d9b6816b82cd4b86493";
    private WarpClient warpClient;
    // 2110110260
    public WarpController() {
        initAppWarp();
        warpClient.connectWithUserName("testUser");
        System.out.println("[APP] State: " + warpClient.getConnectionState());
        warpClient.addConnectionRequestListener(new ConnectionListener(this));

        warpClient.addZoneRequestListener(new ZoneListener(this));
        warpClient.addRoomRequestListener(new RoomListener(this));
        warpClient.addNotificationListener(new NotificationListener(this));

        /*
        warpClient.addChatRequestListener(new ChatListener(this));
        */
    }

    private void initAppWarp(){
        try {
            WarpClient.initialize(apiKey, secretKey);
            warpClient = WarpClient.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    public void onConnectDone(ConnectEvent e) {
        if(e.getResult()== WarpResponseResultCode.SUCCESS) {
            //callBack.onConnectDone(true);
            System.out.println("TRUE");
        } else {
            System.out.println("FALSE");
            //callBack.onConnectDone(false);
        }
    }
    */

    public void appendLeaveNotifyResult(String s) {
        System.out.println("[APP] " + s);
    }

    public void appendNotifyResult(String s) {
        System.out.println("[APP] " + s);
    }

    public void appendResponseResult(String s) {
        System.out.println("[APP] " + s);
    }

    public WarpClient getClient() {
        return warpClient;
    }

    /*
    public void onConnectDone(boolean status){
        if(status){
            warpClient.initUDP();
            warpClient.joinRoomInRange(1, 1, false);
        }else{
            isConnected = false;
            handleError();
        }
    }
    */
}



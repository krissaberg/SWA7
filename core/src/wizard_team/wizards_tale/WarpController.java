package wizard_team.wizards_tale;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

import wizard_team.wizards_tale.listeners.ConnectionListener;
/*
import wizard_team.wizards_tale.listeners.RoomListener;
import wizard_team.wizards_tale.listeners.ZoneListener;
import wizard_team.wizards_tale.listeners.NotificationListener;
*/

public class WarpController {
    private static String apiKey = "60f7fa6993f2adba097dd817d851bd3a901717ed7af1d034ad435379fc329d0a";
    private static String secretKey = "8d20fbbc76dfe8f4928dcad155b162738c76e54d1eda3e31c92b24c3e6dd65fd";

    private WarpClient warpClient;
    // 2110110260
    public WarpController() {
        initAppWarp();
        warpClient.addConnectionRequestListener(new ConnectionListener(this));
        warpClient.connectWithUserName("user");

        /*
        warpClient.addConnectionRequestListener(new ConnectionListener(this));

        warpClient.addZoneRequestListener(new ZoneListener(this));
        warpClient.addRoomRequestListener(new RoomListener(this));
        warpClient.addNotificationListener(new NotificationListener(this));

        warpClient.addChatRequestListener(new ChatListener(this));
        */
    }

    private void initAppWarp(){
        WarpClient.initialize(apiKey, secretKey);
        try {
            WarpClient.enableTrace(true);
            warpClient = WarpClient.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getConnectionState() {
        return warpClient.getConnectionState();
    }

    public void appendLeaveNotifyResult(String s) {
        System.out.println(s);
    }

    public void appendNotifyResult(String s) {
        System.out.println(s);
    }

    public void printResponseResult(String s) {
        System.out.println(s);
    }

    public WarpClient getClient() {
        return warpClient;
    }

}



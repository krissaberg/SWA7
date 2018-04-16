package wizard_team.wizards_tale;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

import wizard_team.wizards_tale.listeners.ConnectionListener;
import wizard_team.wizards_tale.listeners.RoomListener;


public class WarpController {
    //TODO Put these in constants
    private static String apiKey = "60f7fa6993f2adba097dd817d851bd3a901717ed7af1d034ad435379fc329d0a";
    private static String secretKey = "8d20fbbc76dfe8f4928dcad155b162738c76e54d1eda3e31c92b24c3e6dd65fd";

    private WarpClient warpClient;
    public WarpController(String username) {
        initAppWarp();
        warpClient.addConnectionRequestListener(new ConnectionListener(this));
        warpClient.addRoomRequestListener(new RoomListener(this));

        warpClient.connectWithUserName(username);
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

    public void joinRoom() {
        //TODO Create var or something for room id
        warpClient.initUDP();
        warpClient.joinAndSubscribeRoom("2110110260");
        warpClient.getLiveRoomInfo("2110110260");
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



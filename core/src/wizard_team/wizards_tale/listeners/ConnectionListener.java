package wizard_team.wizards_tale.listeners;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;
import java.util.Timer;
import java.util.TimerTask;

import wizard_team.wizards_tale.WarpController;

public class ConnectionListener implements ConnectionRequestListener{

    private WarpController controller;
    private Timer recoverTimer;
    private int NumberOfRecoverAttempts=0;

    public ConnectionListener(WarpController owner) {
        this.controller = owner;
    }

    @Override
    public void onConnectDone(ConnectEvent event) {
        String message="";
        switch(event.getResult())
        {
            case WarpResponseResultCode.AUTH_ERROR:
                message="Auth Error";
                break;
            case WarpResponseResultCode.BAD_REQUEST:
                message="Bad Request";
                break;
            case WarpResponseResultCode.CONNECTION_ERROR:
                message="Connection Error 5";
                break;
            case WarpResponseResultCode.CONNECTION_ERROR_RECOVERABLE:
                message="Connection Error Recoverable";
                RecoverConnection();
                break;
            case WarpResponseResultCode.SUCCESS:
                message="Connection Success";
                break;
            case WarpResponseResultCode.SUCCESS_RECOVERED:
                message="Success Recovered";
                recoverTimer.cancel();
                recoverTimer=null;
                break;
            default:break;

        }
        controller.printResponseResult("onConnectDone " + message);
    }

    private void RecoverConnection()
    {
        if(recoverTimer==null)
        {
            recoverTimer = new Timer();
            RecoverConnectionTask connectionTask = new RecoverConnectionTask(this);
            recoverTimer.scheduleAtFixedRate(connectionTask,6000,6000);
        }
    }


    @Override
    public void onInitUDPDone(byte b) {
        controller.printResponseResult("Init Udp" + b);
    }

    @Override
    public void onDisconnectDone(ConnectEvent event) {
        controller.printResponseResult("onDisconnectDone " + event.getResult());
    }

    private class RecoverConnectionTask extends TimerTask {

        ConnectionListener owner;
        RecoverConnectionTask(ConnectionListener conn) {
            owner = conn;
            owner.NumberOfRecoverAttempts = 0;
        }

        @Override
        public void run() {
            controller.printResponseResult("In Run Method");
            if (owner.NumberOfRecoverAttempts > 10) {
                owner.NumberOfRecoverAttempts = 0;
                owner.recoverTimer.cancel();
                owner.recoverTimer = null;
                return;
            }
            try {
                owner.NumberOfRecoverAttempts++;
                WarpClient.getInstance().RecoverConnection();
            } catch (Exception e) {
                controller.printResponseResult("Exception " + e.getMessage());
            }
        }
    }
}
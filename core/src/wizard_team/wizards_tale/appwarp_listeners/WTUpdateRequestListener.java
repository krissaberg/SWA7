package wizard_team.wizards_tale.appwarp_listeners;

import com.badlogic.gdx.Gdx;
import com.shephertz.app42.gaming.multiplayer.client.listener.UpdateRequestListener;

import java.util.Observable;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WTUpdateRequestListener extends Observable implements UpdateRequestListener {
    public static final String tag = "UpdReqList";

    @Override
    public void onSendUpdateDone(byte b) {
        Gdx.app.log(tag, String.valueOf(b));
    }

    @Override
    public void onSendPrivateUpdateDone(byte b) {
        Gdx.app.log(tag, String.valueOf(b));
    }
}

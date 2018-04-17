package wizard_team.wizards_tale.appwarp_listeners;

import com.shephertz.app42.gaming.multiplayer.client.listener.UpdateRequestListener;

import java.util.Observable;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WTUpdateRequestListener extends Observable implements UpdateRequestListener {
    @Override
    public void onSendUpdateDone(byte b) {
        throw new NotImplementedException();
    }

    @Override
    public void onSendPrivateUpdateDone(byte b) {
        throw new NotImplementedException();
    }
}

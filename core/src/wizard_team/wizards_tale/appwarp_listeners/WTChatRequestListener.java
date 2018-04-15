package wizard_team.wizards_tale.appwarp_listeners;

import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ChatRequestListener;

import java.util.ArrayList;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WTChatRequestListener implements ChatRequestListener {
    @Override
    public void onSendChatDone(byte b) {
        throw new NotImplementedException();
    }

    @Override
    public void onSendPrivateChatDone(byte b) {
        throw new NotImplementedException();
    }

    @Override
    public void onGetChatHistoryDone(byte b, ArrayList<ChatEvent> arrayList) {
        throw new NotImplementedException();
    }
}

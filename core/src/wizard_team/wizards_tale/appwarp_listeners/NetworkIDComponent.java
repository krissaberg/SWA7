package wizard_team.wizards_tale.appwarp_listeners;

import com.badlogic.ashley.core.Component;

public class NetworkIDComponent implements Component {
    public int id;

    public NetworkIDComponent(int id) {
        this.id = id;
    }
}

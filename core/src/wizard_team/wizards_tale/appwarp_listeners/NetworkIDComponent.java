package wizard_team.wizards_tale.appwarp_listeners;

import com.badlogic.ashley.core.Component;

import java.io.Serializable;

public class NetworkIDComponent implements Component, Serializable {
    public int id;

    public NetworkIDComponent(int id) {
        this.id = id;
    }
}

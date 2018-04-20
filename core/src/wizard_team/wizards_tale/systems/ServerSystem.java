package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;

import wizard_team.wizards_tale.appwarp_listeners.NetworkIDComponent;

public class ServerSystem extends IntervalSystem {
    private final WarpClient warpClient;
    public static final String tag = "ServerSystem";

    public ServerSystem(float interval, WarpClient warpClient) {
        super(interval);
        this.warpClient = warpClient;
    }

    @Override
    protected void updateInterval() {
        processInputs();
        broadcastState();
    }

    private void processInputs() {
    }

    protected void broadcastState() {
        // Broadcast engine state to other players in the same room.
        Engine engine = getEngine();

        ArrayList<ArrayList<Component>> snapshot = new ArrayList<ArrayList<Component>>();

        for (Entity entity : engine.getEntitiesFor(Family.all(NetworkIDComponent.class).get())) {
            ArrayList<Component> componentList = new ArrayList<>();

            for (Component component : entity.getComponents()) {
                if (component instanceof Serializable) {
                    componentList.add(component);
                }
            }

            snapshot.add(componentList);
        }

        byte[] serialized = SerializationUtils.serialize(snapshot);

        warpClient.sendUpdatePeers(serialized);

    }

}

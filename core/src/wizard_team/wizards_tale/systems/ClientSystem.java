package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Queue;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

import org.javatuples.Pair;

public class ClientSystem extends IntervalSystem {
    private final WarpClient warpClient;
    private Queue<GameState> gameStateQueue = new Queue<>();
    private Queue<Pair<Float, InputEvent>> inputQueue = new Queue<>();

    // Send inputs, receive state
    public ClientSystem(float interval, WarpClient warpClient) {
        super(interval);
        this.warpClient = warpClient;
    }

    @Override
    protected void updateInterval() {
        sendInputsToServer();
        updateLocalState();
    }

    private void updateLocalState() {
    }

    private void sendInputsToServer() {
        for (Pair<Float, InputEvent> pair : inputQueue) {
            float time = pair.getValue0();
            InputEvent event = pair.getValue1();
        }
    }
}

package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.ByteArray;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.TimeUtils;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class ClientSystem extends IntervalSystem {
    private final WarpClient warpClient;
    private final Touchpad touchpad;
    private final long startTimeMillis;
    private Queue<GameState> gameStateQueue = new Queue<>();
    private Queue<Pair<Float, InputEvent>> inputQueue = new Queue<>();
    private String tag = "ClientSystem";

    // Send inputs, receive state
    public ClientSystem(float interval, WarpClient warpClient, Touchpad touchpad, long millis) {
        super(interval);
        this.warpClient = warpClient;
        this.touchpad = touchpad;
        this.startTimeMillis = millis;
    }

    @Override
    protected void updateInterval() {
        sendInputsToServer();
        updateLocalState();
    }

    private void updateLocalState() {
    }

    private void sendInputsToServer() {
    }
}

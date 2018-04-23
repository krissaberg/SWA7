package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.Gdx;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;

import java.util.ArrayList;
import java.util.HashMap;

import wizard_team.wizards_tale.WizardsTaleGame;
import wizard_team.wizards_tale.components.ScoreComponent;

public class NetworkSystem extends IntervalSystem {
    WizardsTaleGame game;
    WarpClient warpClient;
    public HashMap<String, Integer> scores = new HashMap<String, Integer>();
    private String tag = "NetworkSystem";

    public NetworkSystem(WizardsTaleGame game) {
        super(1); // Update once per second
        this.game = game;
        this.warpClient = game.getWarpClient();
    }

    public void sendMessage(ChatEvent msg) {
        try {
            String sender = msg.getSender();
            int score = Integer.parseInt(msg.getMessage());
            scores.put(sender, score);
        } catch (Exception e) {
        }
    }

    @Override
    protected void updateInterval() {
        // Broadcast this player's score
        int score = 0;
        for (Entity entity : getEngine().getEntitiesFor(Family.all(ScoreComponent.class).get())) {
            score = entity.getComponent(ScoreComponent.class).deaths;
        }
        Gdx.app.log(tag, "Broadcasting score: " + String.valueOf(score));
        warpClient.sendChat(String.valueOf(score));
    }
}

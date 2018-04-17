package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import wizard_team.wizards_tale.components.constants.Constants;

/**
 * Created by synnovehalle on 17/04/2018.
 */

public class TimedEffectComponent implements Component{
    public int time;
    private Constants.EffectTypes effect;

        public TimedEffectComponent(int time, Constants.EffectTypes effect) {
            this.time = time;
            this.effect = effect;
        }
};

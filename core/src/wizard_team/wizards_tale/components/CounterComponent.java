package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;

public class CounterComponent implements Component {
    public float playTime;

    public CounterComponent(float playTime){
        this.playTime = playTime;
    }


}

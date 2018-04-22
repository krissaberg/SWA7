package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteComponent implements Component {
  public Sprite sprite;
  public float width;
  public float height;

  public SpriteComponent(Texture tex) {
    this.sprite = new Sprite(tex);
    this.width = this.sprite.getWidth();
    this.height = this.sprite.getHeight();
    
  }
};

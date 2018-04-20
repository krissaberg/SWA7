package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.Serializable;

public class SpriteComponent implements Component, Serializable {
  // FIXME:
  // To be able to send over the network, we should only save the texture's filename and size,
  // not the whole texture and sprite. The render system can take care of getting the texture.
  public Sprite sprite;
  public float width;
  public float height;

  public SpriteComponent(Texture tex) {
    this.sprite = new Sprite(tex);
    this.width = this.sprite.getWidth();
    this.height = this.sprite.getHeight();
  }
};

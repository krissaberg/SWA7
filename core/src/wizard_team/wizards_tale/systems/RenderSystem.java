package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import wizard_team.wizards_tale.components.GameTimeComponent;
import wizard_team.wizards_tale.components.PositionComponent;
import wizard_team.wizards_tale.components.SpriteComponent;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class RenderSystem extends IteratingSystem {
  private ComponentMapper<PositionComponent> posMapper =
      ComponentMapper.getFor(PositionComponent.class);
  private ComponentMapper<SpriteComponent> spriteMapper =
      ComponentMapper.getFor(SpriteComponent.class);
  private SpriteBatch batch;

  public RenderSystem(SpriteBatch batch) {
    super(Family.all(PositionComponent.class, SpriteComponent.class).get());
    this.batch = batch;
  }

  public void processEntity(Entity e, float dt) {
    PositionComponent pos = posMapper.get(e);
    SpriteComponent spriteComponent = spriteMapper.get(e);

    Sprite sprite = spriteComponent.sprite;

    sprite.setX(pos.x);
    sprite.setY(pos.y);
    batch.begin();
    batch.draw(sprite.getTexture(), pos.x, pos.y);
    batch.end();
  }
}

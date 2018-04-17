package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import wizard_team.wizards_tale.components.BoundRectComponent;
import wizard_team.wizards_tale.components.PositionComponent;
import wizard_team.wizards_tale.components.SpriteComponent;

import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class RenderSystem extends IteratingSystem {
  private ComponentMapper<PositionComponent> posMapper =
      ComponentMapper.getFor(PositionComponent.class);

  private ComponentMapper<SpriteComponent> spriteMapper =
      ComponentMapper.getFor(SpriteComponent.class);

  private ComponentMapper<BoundRectComponent> boundMapper =
          ComponentMapper.getFor(BoundRectComponent.class);

  private SpriteBatch batch;
  private ShapeRenderer shapeRenderer;

  public RenderSystem(SpriteBatch batch) {
    super(Family.all(PositionComponent.class, SpriteComponent.class).get());
    this.batch = batch;
    shapeRenderer = new ShapeRenderer();
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
    if(boundMapper.has(e)) {
      BoundRectComponent bounds = boundMapper.get(e);
      Rectangle rect = bounds.boundRect;
      shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
      shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
      shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
      shapeRenderer.rect(pos.x, pos.y, rect.width, rect.height);
      shapeRenderer.end();
    }
  }
}

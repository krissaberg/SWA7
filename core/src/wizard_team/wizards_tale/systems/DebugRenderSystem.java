package wizard_team.wizards_tale.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import wizard_team.wizards_tale.components.CellBoundaryComponent;
import wizard_team.wizards_tale.components.CellPositionComponent;
//import wizard_team.wizards_tale.components.SpriteComponent;

//TODO: implment sprite
public class DebugRenderSystem extends IteratingSystem {
  private ComponentMapper<CellPositionComponent> posMapper =
      ComponentMapper.getFor(CellPositionComponent.class);


  private ComponentMapper<CellBoundaryComponent> boundMapper =
          ComponentMapper.getFor(CellBoundaryComponent.class);

  private SpriteBatch batch;
  private ShapeRenderer shapeRenderer;

  public DebugRenderSystem(SpriteBatch batch) {
    super(Family.all(CellPositionComponent.class, CellBoundaryComponent.class).get());
    this.batch = batch;
    shapeRenderer = new ShapeRenderer();
  }

  public void processEntity(Entity e, float dt) {
    CellPositionComponent pos = posMapper.get(e);
    //SpriteComponent spriteComponent = spriteMapper.get(e);
    //Sprite sprite = spriteComponent.sprite;
    //sprite.setX(pos.x);
    //sprite.setY(pos.y);
    //batch.draw(sprite.getTexture(), pos.x, pos.y);

    batch.begin();
    CellBoundaryComponent bounds = boundMapper.get(e);
    Rectangle rect = bounds.boundRect;
    shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
    shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.rect(pos.x, pos.y, rect.width, rect.height);
    shapeRenderer.end();
    batch.end();

  }
}

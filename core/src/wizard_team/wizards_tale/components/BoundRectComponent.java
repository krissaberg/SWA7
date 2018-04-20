package wizard_team.wizards_tale.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

import org.javatuples.Pair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class BoundRectComponent implements Component, Serializable {
    public Rectangle boundRect = new Rectangle(0, 0, 0, 0);

    public BoundRectComponent() {
        this.boundRect = new Rectangle(0, 0, 0, 0);
    }

    public BoundRectComponent(Rectangle rectangle) {
        boundRect = rectangle;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(boundRect.width);
        out.writeObject(boundRect.height);
        out.close();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.boundRect = new Rectangle(0,0,0,0);
        this.boundRect.setWidth((float) in.readObject());
        this.boundRect.setHeight((float) in.readObject());
        in.close();
    }

}

package dk.sdu.mmmi.perib21.bulletsystem;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.perib21.common.data.Entity;
import dk.sdu.mmmi.perib21.common.data.GameData;
import dk.sdu.mmmi.perib21.common.data.World;
import dk.sdu.mmmi.perib21.common.data.entityparts.LifePart;
import dk.sdu.mmmi.perib21.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.perib21.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.perib21.common.services.IBulletPluginService;
import dk.sdu.mmmi.perib21.common.services.IGamePluginService;

import java.awt.*;

public class BulletPlugin implements IGamePluginService, IBulletPluginService {
    private Entity bullet;
    private Entity gunner;

    public BulletPlugin() {this(null);}

    public BulletPlugin(Entity gunner) {
        this.gunner = gunner;
    }

    private Entity createBullet(GameData gameData) {

        PositionPart gunnerPosition = this.gunner.getPart(PositionPart.class);
        MovingPart gunnerMovement = this.gunner.getPart(MovingPart.class);

        float deacceleration = 50;
        float acceleration = 0;
        float maxSpeed = 500;
        float rotationSpeed = 5;
        float radians = gunnerPosition.getRadians();

        Entity bullet = new Bullet();

        bullet.setRadius(1);

        float bx = (float) MathUtils.cos(radians) * this.gunner.getRadius() * bullet.getRadius();
        float x = bx + gunnerPosition.getX();
        float by = (float) MathUtils.sin(radians) * this.gunner.getRadius() * bullet.getRadius();
        float y = by + gunnerPosition.getY();
        float shootSpeed = 350 + (gunnerMovement.getSpeed());

        bullet.setShapeX(new float[4]);
        bullet.setShapeY(new float[4]);
        bullet.setColor(new Color(1,1,1,1));
        bullet.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed, shootSpeed));
        bullet.add(new PositionPart(x, y, radians));
        bullet.add(new LifePart(1, 1));

        return bullet;
    }

    @Override
    public void start(GameData gameData, World world) {
        bullet = createBullet(gameData);
        world.addEntity(bullet);
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(bullet);
    }

    @Override
    public Entity create(Entity gunner, GameData gameData) {
        this.gunner = gunner;
        return this.createBullet(gameData);
    }

    @Override
    public Entity delete() {
        return null;
    }


}

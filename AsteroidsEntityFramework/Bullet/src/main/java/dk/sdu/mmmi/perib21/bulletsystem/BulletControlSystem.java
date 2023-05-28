package dk.sdu.mmmi.perib21.bulletsystem;

import dk.sdu.mmmi.perib21.common.data.Entity;
import dk.sdu.mmmi.perib21.common.data.GameData;
import dk.sdu.mmmi.perib21.common.data.World;
import dk.sdu.mmmi.perib21.common.data.entityparts.LifePart;
import dk.sdu.mmmi.perib21.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.perib21.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.perib21.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService {
    /**
     * This overridden method adds the components entities to the world and adds gamedata.
     * It adds the parts of the relevant entity.
     * It might perform additional action depending on the nature of the components entities.
     * @param gameData
     * @param world
     */
    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {

            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            LifePart lifePart = bullet.getPart(LifePart.class);

            movingPart.process(gameData, bullet);
            positionPart.process(gameData, bullet);
            lifePart.reduceExpiration(gameData.getDelta());
            lifePart.process(gameData, bullet);

            movingPart.setUp(true);


            if (lifePart.getExpiration() <= 0 || lifePart.isTerminated()) {
                world.removeEntity(bullet);
            }

            updateShape(bullet);
        }
    }

    /**
     * Updates the shape of an entity. If an entity changes position and moves, the shape will be updated
     * to reflect that change.
     * Pre-condition: An amount of time have happened in game since the entity has been called.
     * Post-condition: The entity's shape has been updated to the new location
     *
     * @param entity
     */

    private void updateShape(Entity entity) {
        float[] shapeX = entity.getShapeX();
        float[] shapeY = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapeX[0] = (float) (x + Math.cos(radians) * 1);
        shapeY[0] = (float) (y + Math.sin(radians) * 1);

        shapeX[1] = (float) (x + Math.cos(radians) * 0);
        shapeY[1] = (float) (y + Math.sin(radians) * 0);

        shapeX[2] = (float) (x + Math.cos(radians) * 2);
        shapeY[2] = (float) (y + Math.sin(radians) * 2);

        shapeX[3] = (float) (x + Math.cos(radians) * -2);
        shapeY[3] = (float) (y + Math.sin(radians) * -2);

        entity.setShapeX(shapeX);
        entity.setShapeY(shapeY);
    }
}

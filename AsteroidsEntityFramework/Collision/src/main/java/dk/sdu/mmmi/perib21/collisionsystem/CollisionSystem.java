package dk.sdu.mmmi.perib21.collisionsystem;

import dk.sdu.mmmi.perib21.common.data.Entity;
import dk.sdu.mmmi.perib21.common.data.GameData;
import dk.sdu.mmmi.perib21.common.data.World;
import dk.sdu.mmmi.perib21.common.data.entityparts.LifePart;
import dk.sdu.mmmi.perib21.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.perib21.common.services.IPostEntityProcessingService;

public class CollisionSystem implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity crasherEntity : world.getEntities()) {
            for (Entity collidedEntity : world.getEntities()) {
                if (crasherEntity.getID().equals(collidedEntity.getID())) {
                    continue;
                }
                LifePart crasherEntityLifePart = crasherEntity.getPart(LifePart.class);
                if (crasherEntityLifePart.getLife() > 0 && this.collides(crasherEntity, collidedEntity)) {
                    crasherEntityLifePart.setIsHit(true);
                }
            }
        }
    }
    private boolean collides(Entity crasherEntity, Entity collidedEntity) {
        // Get data for collision detection
        PositionPart hitterPositionPart = crasherEntity.getPart(PositionPart.class);
        PositionPart collidedPositionPart = collidedEntity.getPart(PositionPart.class);

        // Calculate distance between
        float dx = (float) (hitterPositionPart.getX() - collidedPositionPart.getX());
        float dy = (float) (hitterPositionPart.getY() - collidedPositionPart.getY());
        float distanceBetween = (float) Math.sqrt(Math.pow(dx,2) + Math.pow(dy, 2));

        // Check if distance is less than the two radius's, meaning that they are hitting each other
        float collisionDistance = crasherEntity.getRadius() + collidedEntity.getRadius();
        return distanceBetween < collisionDistance;
    }
}

package dk.sdu.mmmi.perib21.collisionsystem;

import dk.sdu.mmmi.perib21.common.data.Entity;
import dk.sdu.mmmi.perib21.common.data.GameData;
import dk.sdu.mmmi.perib21.common.data.World;
import dk.sdu.mmmi.perib21.common.data.entityparts.LifePart;
import dk.sdu.mmmi.perib21.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.perib21.common.services.IPostEntityProcessingService;

public class CollisionSystem implements IPostEntityProcessingService {

    /**
     * This process checks two entities:
     * The one that crashes into another entity.
     * The collided entity that the crasher entity collides with.
     * The life part of the entities will be evaluated and is reflected in the game.
     * Pre-condition: Two entities are present in the game. One have crashed into the other.
     * Post-condition: The two entities have taken damage.
     * If the entity was an asteroid, then it  has been splittet or terminated
     * If the entity was a player, bullet or enemy, it has been terminated.
     * @param gameData
     * @param world
     */
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

    /**
     * This method checks for the location of two entities.
     * If the distance between the entities are lesser than the colission distance,
     * then the two entities will be evaluated as collided.
     * Pre-condition: Two entities are present in the game. One is about to crash into the other.
     * Post-condition: The two entities location matches and the collision will be processed.
     * @param crasherEntity
     * @param collidedEntity
     * @return
     */
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

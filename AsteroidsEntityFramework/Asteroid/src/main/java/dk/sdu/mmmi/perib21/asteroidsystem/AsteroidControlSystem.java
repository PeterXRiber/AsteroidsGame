package dk.sdu.mmmi.perib21.asteroidsystem;

import dk.sdu.mmmi.perib21.common.data.Entity;
import dk.sdu.mmmi.perib21.common.data.GameData;
import dk.sdu.mmmi.perib21.common.data.World;
import dk.sdu.mmmi.perib21.common.data.entityparts.LifePart;
import dk.sdu.mmmi.perib21.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.perib21.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.perib21.common.services.IEntityProcessingService;

/**
 * This is the asteroid control system.
 * This class controls the movement and the normal actions of the asteroids in the game.
 * It contains the logic for processing, splitting and updating the shape of an asteroid.
 */
public class AsteroidControlSystem implements IEntityProcessingService {


    /**
     * This overridden method adds the components entities to the world and adds gamedata.
     * It adds the parts of the relevant entity.
     * It might perform additional action depending on the nature of the components entities.
     * @param gameData
     * @param world
     */
    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);
            LifePart lifePart = asteroid.getPart(LifePart.class);

            this.handleAsteroidSplitting(gameData, world, asteroid);

            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);
            lifePart.process(gameData, asteroid);

            if (lifePart.isTerminated()) {
                world.removeEntity(asteroid);
            }

            this.updateShape(asteroid);
        }
    }

    /**
     * --handleAsteroidSpltting--
     * This method handles the process of splitting asteroids based on an asteroids amount of life.
     * Pre-condition: A given asteroid is in the game. The Asteroid has an amount of life.
     * Post-condition: If the asteroid is not terminated by being hit, the method will split the asteroid
     * and smaller asteroids will be created.
     * If the asteroid is evaluated as terminated, the method will not perform a split.
     * @param gameData
     * @param world
     * @param asteroid
     */
    private void handleAsteroidSplitting(GameData gameData, World world, Entity asteroid) {
        // Get parts
        LifePart lifePart = asteroid.getPart(LifePart.class);

        // Do not continue if not hit or already dead
        if (!lifePart.isEntityHit() || lifePart.isTerminated()) {
            return;
        }

        // Create new asteroids through plugin
        AsteroidPlugin asteroidPlugin = new AsteroidPlugin();
        asteroidPlugin.createSplittetAsteroid(gameData, world, asteroid);

        return;
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

        LifePart lifePart = entity.getPart(LifePart.class);

        float[] distances = new float[8];
        switch (lifePart.getLife()) {
            default:
            case 1:
                distances = new float[] {10, 8, 10, 6, 2, 10, 9, 10};
                break;
            case 2:
                distances = new float[] {18, 5, 15, 10, 18, 18, 15, 18};
                break;
            case 3:
                distances = new float[] {25, 20, 23, 21, 26, 18, 25, 25};
                break;
        }

        for (int i = 0; i < 8; i++) {
            shapeX[i] = (float) (x + Math.cos(radians + Math.PI * (i / 4f)) * distances[i]);
            shapeY[i] = (float) (y + Math.sin(radians + Math.PI * (i / 4f)) * distances[i]);
        }

        entity.setShapeX(shapeX);
        entity.setShapeY(shapeY);
    }
}













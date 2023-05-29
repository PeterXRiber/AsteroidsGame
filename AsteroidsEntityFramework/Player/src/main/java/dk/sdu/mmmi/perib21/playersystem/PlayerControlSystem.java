package dk.sdu.mmmi.perib21.playersystem;
import dk.sdu.mmmi.perib21.common.data.Entity;
import dk.sdu.mmmi.perib21.common.data.GameData;
import dk.sdu.mmmi.perib21.common.data.World;
import dk.sdu.mmmi.perib21.common.data.entityparts.GunnerPart;
import dk.sdu.mmmi.perib21.common.data.entityparts.LifePart;
import dk.sdu.mmmi.perib21.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.perib21.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.perib21.common.services.IBulletPluginService;
import dk.sdu.mmmi.perib21.common.services.IEntityProcessingService;
import dk.sdu.mmmi.perib21.common.SPILocater.SPILocator;

import java.util.Collection;

import static dk.sdu.mmmi.perib21.common.data.GameKeys.*;


public class PlayerControlSystem implements IEntityProcessingService {
    /**
     * This overridden method adds the components entities to the world and adds gamedata.
     * It adds the parts of the relevant entity.
     * It might perform additional action depending on the nature of the components entities.
     * @param gameData
     * @param world
     */
    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            GunnerPart gunnerPart = player.getPart(GunnerPart.class);

            movingPart.setLeft(gameData.getKeys().isDown(LEFT));
            movingPart.setRight(gameData.getKeys().isDown(RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(UP));
            
            
            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            lifePart.process(gameData,player);
            gunnerPart.process(gameData,player);

            // Manage the life part
            if (lifePart.isTerminated()) {
                world.removeEntity(player);
            }
            // Manage the gunner part
            gunnerPart.setWeaponActive(gameData.getKeys().isDown(SPACE));
            //New implementation
            if (gunnerPart.getWeaponActive()==true) {
                Collection<IBulletPluginService> bulletPlugins = SPILocator.locateAll(IBulletPluginService.class);
                for (IBulletPluginService bulletPlugin : bulletPlugins) {
                    world.addEntity(bulletPlugin.create(player, gameData));
                }
            }
/*
            //Old implementation
            if (gunnerPart.getWeaponActive()==true) {
                BulletPlugin bulletPlugin = new BulletPlugin();
                world.addEntity(bulletPlugin.create(player,gameData));
            }
 */
            if (lifePart.isTerminated()) {
                world.removeEntity(player);
            }

            updateShape(player);
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
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 8);
        shapey[0] = (float) (y + Math.sin(radians) * 8);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}

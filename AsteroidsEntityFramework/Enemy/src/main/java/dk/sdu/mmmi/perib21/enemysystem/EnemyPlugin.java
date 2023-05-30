package dk.sdu.mmmi.perib21.enemysystem;
import dk.sdu.mmmi.perib21.common.data.Entity;
import dk.sdu.mmmi.perib21.common.data.GameData;
import dk.sdu.mmmi.perib21.common.data.World;
import dk.sdu.mmmi.perib21.common.data.entityparts.GunnerPart;
import dk.sdu.mmmi.perib21.common.data.entityparts.LifePart;
import dk.sdu.mmmi.perib21.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.perib21.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.perib21.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    public EnemyPlugin () {

    }

    @Override
    public void start(GameData gameData, World world) {
        // Add enemies to the world
            enemy = createEnemyShip(gameData);
            world.addEntity(enemy);


    }
    /**
     * Creates an enemy
     * Pre-condition: The game is running. A new enemy will be created given the defined attributes.
     * Post-condition: An enemy has been created and added to the game.
     * @param gameData
     */
    private Entity createEnemyShip(GameData gameData) {

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;

        Entity enemyShip = new Enemy();
        enemyShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed, 50));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.add(new LifePart(1,10000000));
        enemyShip.add(new GunnerPart(2.9f));

        return enemyShip;

    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove enemies
        world.removeEntity(enemy);
    }
}

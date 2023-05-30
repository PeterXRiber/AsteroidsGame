package dk.sdu.mmmi.perib21.asteroidsystem;

import dk.sdu.mmmi.perib21.common.data.Entity;
import dk.sdu.mmmi.perib21.common.data.GameData;
import dk.sdu.mmmi.perib21.common.data.World;
import dk.sdu.mmmi.perib21.common.data.entityparts.LifePart;
import dk.sdu.mmmi.perib21.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.perib21.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.perib21.common.services.IGamePluginService;

import java.awt.*;
import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

    private Entity asteroid;
    private int life;
    private float deacceleration, acceleration, maxSpeed, rotationSpeed;
    private int shapePointCount;
    private Color color;

    public AsteroidPlugin() {
        this(3);
    }

    public AsteroidPlugin(int life) {
        this.life = life;

        this.deacceleration = 0;
        this.acceleration = 0;
        this.maxSpeed = 400;
        this.rotationSpeed = 0;
        this.color = new Color(1,1,1,1);
        this.shapePointCount = 8;
    }

    @Override
    public void start(GameData gameData, World world) {
        for (int i = 0; i < 5; i++) {
            asteroid = createInitialAsteroid(gameData);
            world.addEntity(asteroid);
        }
    }

    /**
     * Creates an asteroid
     * Pre-condition: The game is running. A new asteroid will be created given the defined attributes.
     * Post-condition: An asteroid has been created and added to the game.
     * @param gameData
     */
    public Entity createInitialAsteroid(GameData gameData) {
        float x = 200;
        float y = 200;
        float radians = 5;

        float startSpeed = 0;

        Entity asteroid = new Asteroid();
        this.setAsteroidRadius(asteroid);

        this.buildAsteroid(gameData, asteroid, x, y, radians, startSpeed);

        return asteroid;
    }

    /**
     * An asteroid will be splittet based on the amount of life the asteroid has remaining.
     *
     * Pre-condition: The game is running. An asteroid have been taking damage and will be changes to another kind
     * asteroid.
     * Post-condition: The former asteroid have been removed from the game.
     * The newly created and splittet asteroid has been added to the game world
     * @param gameData
     * @param world
     * @param asteroid
     */
    protected void createSplittetAsteroid(GameData gameData, World world, Entity asteroid) {
        world.removeEntity(asteroid);

        PositionPart positionPart = asteroid.getPart(PositionPart.class);
        MovingPart movingPart = asteroid.getPart(MovingPart.class);
        LifePart lifePart = asteroid.getPart(LifePart.class);

        this.life = lifePart.getLife() - 1;

        if (lifePart.getLife() <= 1) {
            return;
        }

        float[] splits = new float[] {(float) ((Math.PI * 1/2)), (float) ((Math.PI * 1/2) * (-1))};

        for (float split : splits) {
            Entity splittetAsteroid = new Asteroid();

            this.setAsteroidRadius(splittetAsteroid);

            float radians = positionPart.getRadians() + split;

            float bx = (float) Math.cos(radians) * asteroid.getRadius();
            float x = bx + positionPart.getX();
            float by = (float) Math.sin(radians) * asteroid.getRadius();
            float y = by + positionPart.getY();

            float startSpeed = new Random().nextInt(80);

            this.buildAsteroid(gameData, splittetAsteroid, x, y, radians, startSpeed);

            world.addEntity(splittetAsteroid);
        }
    }

    /**
     * Builds the pieces of the asteroids
     *
     * Pre-condition: Asteroid to be initialized and is missing its relevant pieces.
     * Post-condition: The pieces has been added and the asteroid is ready to be created.
     *
     * @param gameData Data for the game
     * @param asteroid World of the game
     * @param x Start x position
     * @param y Start y position
     * @param radians Start radians
     * @param startSpeed Start speed
     */
    private void buildAsteroid(GameData gameData, Entity asteroid, float x, float y, float radians, float startSpeed) {
        asteroid.setShapeX(new float[this.shapePointCount]);
        asteroid.setShapeY(new float[this.shapePointCount]);
        asteroid.add(new MovingPart(this.deacceleration, this.acceleration, this.maxSpeed, this.rotationSpeed, startSpeed));
        asteroid.add(new PositionPart(x, y, radians));
        LifePart lifePart = new LifePart(this.life, 10000000);
        asteroid.add(lifePart);
        this.setAsteroidRadius(asteroid);
    }

    /**
     * The life of the asteroid is evaluated and is used to set the radius
     * Pre-condition: An asteroid that has just been taken damage
     * Post-condition: Asteroid with the radius that matches the life of the asteroid
     * @param asteroid
     */
    private void setAsteroidRadius(Entity asteroid) {
        float radius = 0;
        switch (this.life) {
            case 1:
                radius = 10;
                break;
            case 2:
                radius = 15;
                break;
            case 3:
                radius = 25;
                break;
            default:
                break;
        }
        asteroid.setRadius(radius);
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(asteroid);
    }
}

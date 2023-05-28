package dk.sdu.mmmi.perib21.main;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.mmmi.perib21.asteroidsystem.AsteroidControlSystem;
import dk.sdu.mmmi.perib21.asteroidsystem.AsteroidPlugin;
import dk.sdu.mmmi.perib21.bulletsystem.BulletControlSystem;
import dk.sdu.mmmi.perib21.collisionsystem.CollisionSystem;
import dk.sdu.mmmi.perib21.common.data.Entity;
import dk.sdu.mmmi.perib21.common.data.GameData;
import dk.sdu.mmmi.perib21.common.data.World;
import dk.sdu.mmmi.perib21.common.services.IBulletPluginService;
import dk.sdu.mmmi.perib21.common.services.IEntityProcessingService;
import dk.sdu.mmmi.perib21.common.services.IGamePluginService;
import dk.sdu.mmmi.perib21.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.perib21.common.utilities.SPILocator;
import dk.sdu.mmmi.perib21.enemysystem.EnemyControlSystem;
import dk.sdu.mmmi.perib21.managers.GameInputProcessor;
import dk.sdu.mmmi.perib21.playersystem.PlayerControlSystem;
import dk.sdu.mmmi.perib21.playersystem.PlayerPlugin;
import dk.sdu.mmmi.perib21.enemysystem.EnemyPlugin;
import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

public class Game implements ApplicationListener {
    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private final GameData gameData = new GameData();
    private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private List<IPostEntityProcessingService> entityPostProcessorServiceList = new ArrayList<>();
    private List<IGamePluginService> entityPlugins = new ArrayList<>();
    private IEntityProcessingService playerProcess = new PlayerControlSystem();
    private IEntityProcessingService enemyProcess = new EnemyControlSystem();
    private IEntityProcessingService asteroidProcess = new AsteroidControlSystem();
    private IGamePluginService playerPlugin = new PlayerPlugin();
    private IGamePluginService enemyPlugin = new EnemyPlugin();
    private IGamePluginService asteroidPlugin = new AsteroidPlugin();
    private IPostEntityProcessingService collisionProcess = new CollisionSystem();
    IEntityProcessingService bulletProcess = new BulletControlSystem();
    private World world = new World();

    @Override
    public void create() {
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData)
        );

        entityPlugins.add(playerPlugin);
        entityProcessors.add(playerProcess);


        entityPlugins.add(enemyPlugin);
        entityProcessors.add(enemyProcess);

        entityPlugins.add(asteroidPlugin);
        entityProcessors.add(asteroidProcess);

        entityProcessors.add(bulletProcess);

       entityPostProcessorServiceList.add(collisionProcess);


        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : entityPlugins) {
            iGamePlugin.start(gameData, world);
        }
    }

    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        gameData.getKeys().update();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        for (IPostEntityProcessingService entityPostProcessingService : getPostEntityProcessingServices()) {
            entityPostProcessingService.process(gameData, world);
        }


    }

    private void draw() {
        for (Entity entity : world.getEntities()) {

            sr.setColor(1, 1, 1, 1);


            sr.begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1;
                    i < shapex.length;
                    j = i++) {

                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    private Collection<? extends IGamePluginService> getPluginServices() {
        return SPILocator.locateAll(IGamePluginService.class);
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return SPILocator.locateAll(IEntityProcessingService.class);
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return SPILocator.locateAll(IPostEntityProcessingService.class);
    }
}

package dk.sdu.mmmi.perib21.enemysystem;

import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.perib21.bulletsystem.BulletPlugin;
import dk.sdu.mmmi.perib21.common.data.Entity;
import dk.sdu.mmmi.perib21.common.data.GameData;
import dk.sdu.mmmi.perib21.common.data.World;
import dk.sdu.mmmi.perib21.common.data.entityparts.GunnerPart;
import dk.sdu.mmmi.perib21.common.data.entityparts.LifePart;
import dk.sdu.mmmi.perib21.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.perib21.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.perib21.common.services.IEntityProcessingService;

public class EnemyControlSystem implements IEntityProcessingService {

    private float time = 0f;

    @Override
    public void process(GameData gameData, World world) {


        for (Entity enemy : world.getEntities(Enemy.class)) {

            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);
            GunnerPart gunnerPart = enemy.getPart(GunnerPart.class);

            this.time = (this.time + gameData.getDelta()) % 50;

            float controlRotateAmplifier = MathUtils.random(0.5f,2f);
            float controlGeneralAmplifier = MathUtils.random(0.5f,2f);

            movingPart.setLeft(
                    (MathUtils.sin(time * controlRotateAmplifier +
                            MathUtils.random(0f, 2f)) *
                            controlGeneralAmplifier) < MathUtils.random(-0.3f, -controlGeneralAmplifier)
            );
            movingPart.setRight(
                    (MathUtils.sin(time * controlRotateAmplifier +
                            MathUtils.random(0f, 2f)) *
                            controlGeneralAmplifier) > MathUtils.random(0.8f, controlGeneralAmplifier));

            movingPart.setUp(MathUtils.random(0.01f, 1f) > MathUtils.random(0.5f, 1f));
            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            lifePart.process(gameData,enemy);
            gunnerPart.process(gameData,enemy);

            gunnerPart.setWeaponActive(MathUtils.random(0f,0.55f) > 0.49f);
            if (gunnerPart.getWeaponActive()==true) {
                BulletPlugin bulletPlugin = new BulletPlugin();
                world.addEntity(bulletPlugin.create(enemy,gameData));
            }
            if (lifePart.isTerminated()) {
                world.removeEntity(enemy);
            }
            updateShape(enemy);
        }
    }

    private void updateShape(Entity enemy) {
        float[] shapex = enemy.getShapeX();
        float[] shapey = enemy.getShapeY();
        PositionPart positionPart = enemy.getPart(PositionPart.class);
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

        enemy.setShapeX(shapex);
        enemy.setShapeY(shapey);

    }


}

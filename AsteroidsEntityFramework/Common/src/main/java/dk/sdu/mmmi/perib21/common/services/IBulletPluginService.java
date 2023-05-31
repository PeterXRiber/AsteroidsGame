package dk.sdu.mmmi.perib21.common.services;

import dk.sdu.mmmi.perib21.common.data.Entity;
import dk.sdu.mmmi.perib21.common.data.GameData;

public interface IBulletPluginService {


     /**
      * @param gunner
      * @param gameData
      *
      * Pre-condition: An entity that implements the SPI is loaded into the game
      * and requires the ability to use the weapon thereby asking for a bullet to be
      * created.
      * Post-condition:
      * The entity created a bullet and the bullet is now visible in the game.
      */
     Entity create(Entity gunner, GameData gameData);


     Entity delete();


}

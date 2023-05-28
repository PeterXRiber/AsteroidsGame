package dk.sdu.mmmi.perib21.common.services;

import dk.sdu.mmmi.perib21.common.data.GameData;
import dk.sdu.mmmi.perib21.common.data.World;

public interface IEntityProcessingService {

    /**
     * Processes the entities of the game.
     * Pre-condition: A unit of time in the game has passed since last process has been handled.
     * Post-condition: The entity has been handled and the entity's process has been updated.
     *
     * @param gameData Data for the game
     * @param world World of the game
     *
     */
    void process(GameData gameData, World world);
}

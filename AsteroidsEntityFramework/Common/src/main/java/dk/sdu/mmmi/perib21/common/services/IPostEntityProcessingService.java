package dk.sdu.mmmi.perib21.common.services;

import dk.sdu.mmmi.perib21.common.data.GameData;
import dk.sdu.mmmi.perib21.common.data.World;

/**
 *
 * @author jcs
 */
public interface IPostEntityProcessingService  {
        /**
         * Process entities after all other processes performed on the entities in relation to the IGamePluginService.
         * This can be processes that needs to be processed after all entities has been processed
         * by the processes in the IGamePluginService
         * This could be an in-game effect like collision, poison-effects ect.
         *
         * Pre-condition: A unit of time in the game has passed since last process has been handled.
         * Post-condition: The entity has been handled and the entity's process has been updated.
         *
         * @param gameData
         * @param world
         */
        void process(GameData gameData, World world);
}

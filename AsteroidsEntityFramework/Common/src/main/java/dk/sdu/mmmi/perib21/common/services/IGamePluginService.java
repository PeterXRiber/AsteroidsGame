package dk.sdu.mmmi.perib21.common.services;

import dk.sdu.mmmi.perib21.common.data.GameData;
import dk.sdu.mmmi.perib21.common.data.World;


public interface IGamePluginService {
    /**
     * @param gameData
     * @param world
     *
     *      The entities of the game is created
     *
     *  Pre-condition: The game is being booted and the plugin has not been called yet.
     *  Post-condition: The plugin has been used and the relevant entities have been added to the game world
     */
    void start(GameData gameData, World world);
    /**
     * @param gameData
     * @param world
     *
     * The plugin is stopped
     *
     * Pre-condition: The process of the game is being terminated and the plugin has not been stopped.
     * Post-condition: The plugin has been stopped and the relevant entities have been removed from the game world
     */
    void stop(GameData gameData, World world);
}

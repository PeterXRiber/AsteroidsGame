import dk.sdu.mmmi.perib21.common.services.IGamePluginService;
import dk.sdu.mmmi.perib21.common.services.IEntityProcessingService;
module Asteroid {
    requires Common;
    requires java.desktop;

    provides IGamePluginService with dk.sdu.mmmi.perib21.asteroidsystem.AsteroidPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.perib21.asteroidsystem.AsteroidControlSystem;


}
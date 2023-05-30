import dk.sdu.mmmi.perib21.common.services.IBulletPluginService;
import dk.sdu.mmmi.perib21.common.services.IGamePluginService;
import dk.sdu.mmmi.perib21.common.services.IEntityProcessingService;
module Player {
    requires Common;

    uses IBulletPluginService;

    provides IEntityProcessingService with dk.sdu.mmmi.perib21.playersystem.PlayerControlSystem;
    provides IGamePluginService with dk.sdu.mmmi.perib21.playersystem.PlayerPlugin;

}
import dk.sdu.mmmi.perib21.common.services.IGamePluginService;
import dk.sdu.mmmi.perib21.common.services.IEntityProcessingService;
import dk.sdu.mmmi.perib21.common.services.IBulletPluginService;
module Player {
    requires Common;

    uses IBulletPluginService;

    provides IGamePluginService with dk.sdu.mmmi.perib21.playersystem.PlayerPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.perib21.playersystem.PlayerControlSystem;




}
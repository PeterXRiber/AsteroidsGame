import dk.sdu.mmmi.perib21.common.services.IBulletPluginService;
import dk.sdu.mmmi.perib21.common.services.IEntityProcessingService;
import dk.sdu.mmmi.perib21.common.services.IGamePluginService;

module Enemy {

    requires Common;

    uses IBulletPluginService;

    provides IGamePluginService with dk.sdu.mmmi.perib21.enemysystem.EnemyPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.perib21.enemysystem.EnemyControlSystem;
}
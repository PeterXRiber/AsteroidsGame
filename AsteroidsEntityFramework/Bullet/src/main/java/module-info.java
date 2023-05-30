import dk.sdu.mmmi.perib21.common.services.IGamePluginService;
import dk.sdu.mmmi.perib21.common.services.IEntityProcessingService;
import dk.sdu.mmmi.perib21.common.services.IBulletPluginService;
module Bullet {
    requires Common;
    requires java.desktop;

    provides IGamePluginService with dk.sdu.mmmi.perib21.bulletsystem.BulletPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.perib21.bulletsystem.BulletControlSystem;
    provides IBulletPluginService with dk.sdu.mmmi.perib21.bulletsystem.BulletPlugin;


}
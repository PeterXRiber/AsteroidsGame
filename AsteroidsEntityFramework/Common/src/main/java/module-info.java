import dk.sdu.mmmi.perib21.common.SPILocator.SPILocator;
import dk.sdu.mmmi.perib21.common.services.IBulletPluginService;
import dk.sdu.mmmi.perib21.common.services.IEntityProcessingService;
import dk.sdu.mmmi.perib21.common.services.IGamePluginService;
import dk.sdu.mmmi.perib21.common.services.IPostEntityProcessingService;

module Common {
    requires java.desktop;
    exports dk.sdu.mmmi.perib21.common.data;
    exports dk.sdu.mmmi.perib21.common.events;
    exports dk.sdu.mmmi.perib21.common.services;
    exports dk.sdu.mmmi.perib21.common.SPILocator;
    exports dk.sdu.mmmi.perib21.common.data.entityparts;

    uses IBulletPluginService;
    uses IEntityProcessingService;
    uses IGamePluginService;
    uses IPostEntityProcessingService;

}
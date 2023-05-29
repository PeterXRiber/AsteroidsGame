import dk.sdu.mmmi.perib21.common.services.IEntityProcessingService;
import dk.sdu.mmmi.perib21.common.services.IGamePluginService;
import dk.sdu.mmmi.perib21.common.services.IPostEntityProcessingService;

module Core {
    requires Common;
    requires java.desktop;

    uses IEntityProcessingService;
    uses IGamePluginService;
    uses IPostEntityProcessingService;

}
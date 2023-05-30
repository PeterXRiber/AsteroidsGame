import dk.sdu.mmmi.perib21.common.services.IEntityProcessingService;
import dk.sdu.mmmi.perib21.common.services.IGamePluginService;
import dk.sdu.mmmi.perib21.common.services.IPostEntityProcessingService;
module Core {
    requires Common;
    requires java.desktop;
    requires com.badlogic.gdx;


    uses IGamePluginService;
    uses IEntityProcessingService;
    uses IPostEntityProcessingService;

}
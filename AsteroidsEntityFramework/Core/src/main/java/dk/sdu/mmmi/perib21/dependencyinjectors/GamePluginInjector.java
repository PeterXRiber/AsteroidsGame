package dk.sdu.mmmi.perib21.dependencyinjectors;

import dk.sdu.mmmi.perib21.common.SPILocator.SPILocator;
import dk.sdu.mmmi.perib21.common.data.GameData;
import dk.sdu.mmmi.perib21.common.data.World;
import dk.sdu.mmmi.perib21.common.services.IGamePluginService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("gamePluginInjector")
public class GamePluginInjector {
    public void start(GameData gameData, World world) {
        List<IGamePluginService> iGamePluginServiceList = SPILocator.locateAll(IGamePluginService.class);
        for (IGamePluginService iGamePluginService : iGamePluginServiceList) {
            iGamePluginService.start(gameData,world);
        }
    }
}

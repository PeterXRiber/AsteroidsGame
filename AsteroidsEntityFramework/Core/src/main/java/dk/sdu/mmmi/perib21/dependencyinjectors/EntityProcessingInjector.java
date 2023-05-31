package dk.sdu.mmmi.perib21.dependencyinjectors;

import dk.sdu.mmmi.perib21.common.SPILocator.SPILocator;
import dk.sdu.mmmi.perib21.common.data.GameData;
import dk.sdu.mmmi.perib21.common.data.World;
import dk.sdu.mmmi.perib21.common.services.IEntityProcessingService;
import org.springframework.stereotype.Service;
import java.util.List;
@Service("entityProcessingInjector")
public class EntityProcessingInjector {
    public void process(GameData gameData, World world) {
        List<IEntityProcessingService> iEntityProcessingServiceList = SPILocator.locateAll(IEntityProcessingService.class);
        for (IEntityProcessingService iEntityProcessingService : iEntityProcessingServiceList) {
            iEntityProcessingService.process(gameData,world);
        }
    }
}

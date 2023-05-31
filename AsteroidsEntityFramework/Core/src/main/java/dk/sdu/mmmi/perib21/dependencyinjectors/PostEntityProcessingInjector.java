package dk.sdu.mmmi.perib21.dependencyinjectors;

import dk.sdu.mmmi.perib21.common.SPILocator.SPILocator;
import dk.sdu.mmmi.perib21.common.data.GameData;
import dk.sdu.mmmi.perib21.common.data.World;
import dk.sdu.mmmi.perib21.common.services.IPostEntityProcessingService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("postEntityProcessingInjector")
public class PostEntityProcessingInjector {
    public void process(GameData gameData, World world) {
        List<IPostEntityProcessingService> iPostEntityProcessingServiceList = SPILocator.locateAll
                (IPostEntityProcessingService.class);
        for (IPostEntityProcessingService postEntityProcessingService : iPostEntityProcessingServiceList) {
            postEntityProcessingService.process(gameData,world);
        }
    }
}

package dk.sdu.mmmi.perib21.common.services;

import dk.sdu.mmmi.perib21.common.data.GameData;
import dk.sdu.mmmi.perib21.common.data.World;

public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}

package dk.sdu.mmmi.perib21.common.services;

import dk.sdu.mmmi.perib21.common.data.Entity;
import dk.sdu.mmmi.perib21.common.data.GameData;

public interface IBulletPluginService {

     Entity create(Entity gunner, GameData gameData);


     Entity delete();


}

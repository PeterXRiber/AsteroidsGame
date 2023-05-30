import dk.sdu.mmmi.perib21.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;

    provides IPostEntityProcessingService with dk.sdu.mmmi.perib21.collisionsystem.CollisionSystem;

}
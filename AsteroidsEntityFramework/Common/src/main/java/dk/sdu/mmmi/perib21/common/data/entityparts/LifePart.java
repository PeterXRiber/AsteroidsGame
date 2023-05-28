/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.perib21.common.data.entityparts;

import dk.sdu.mmmi.perib21.common.data.Entity;
import dk.sdu.mmmi.perib21.common.data.GameData;

/**
 *
 * @author Someone
 */
public class LifePart implements EntityPart {

    private int life;
    private boolean entityHit = false;
    private boolean terminated = false;
    private float expiration;

    public LifePart(int life, float expiration) {
        this.life = life;
        this.expiration = expiration;
        this.entityHit = false;
        this.terminated = false;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }


    public float getExpiration() {
        return expiration;
    }

    public void setExpiration(float expiration) {
        this.expiration = expiration;
    }  
    
    public void reduceExpiration(float delta){
        this.expiration -= delta;
    }

    public boolean isEntityHit() {
        return entityHit;
    }
    public void setIsHit(boolean isHit) {
        this.entityHit = isHit;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (this.entityHit) {
            this.life -= 1;
            this.entityHit = false;
        }
        if (this.life <= 0) {
            this.terminated = true;
        }
    }
}

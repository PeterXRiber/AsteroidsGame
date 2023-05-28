package dk.sdu.mmmi.perib21.common.data.entityparts;

import dk.sdu.mmmi.perib21.common.data.Entity;
import dk.sdu.mmmi.perib21.common.data.GameData;

public class GunnerPart implements EntityPart{

    private float coolDownTime;
    private float weaponCoolDown;
    private boolean weaponActive;

    public GunnerPart(float coolDownTime) {
        this.coolDownTime = coolDownTime;
    }

    public void setWeaponActive(boolean weaponActive) {
        if (!weaponActive) {
            this.weaponActive = false;
            return;
        }

        if (weaponCoolDown > 0) {
            this.weaponActive = false;
            return;
        }

        this.weaponActive = true;
        this.weaponCoolDown = this.coolDownTime;

    }

    public boolean getWeaponActive() {
        return this.weaponActive;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (this.weaponCoolDown > 0) {
            this.weaponCoolDown -= gameData.getDelta();
            this.weaponActive = false;
        } else {
            this.coolDownTime = 0;
        }
    }
}

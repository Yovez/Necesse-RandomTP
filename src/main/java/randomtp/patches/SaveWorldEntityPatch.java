package randomtp.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.save.SaveData;
import necesse.engine.world.WorldEntity;
import net.bytebuddy.asm.Advice;
import randomtp.RandomTP;

@ModMethodPatch(target = WorldEntity.class, name = "getSave", arguments = {})
public class SaveWorldEntityPatch {

    @Advice.OnMethodExit
    static void onExit(@Advice.Return(readOnly = false) SaveData saveData) {
        SaveData randomTPSaveData = new SaveData("RANDOMTP");
        randomTPSaveData.addBoolean("confirmation", RandomTP.confirmation, "True/False to confirm when using RandomTP.");
        randomTPSaveData.addBoolean("teleport_to_ocean", RandomTP.teleportToOcean, "True/False. True means you'll spawn in the ocean as how the mod was created. False you'll spawn on the island's spawn point.");
        randomTPSaveData.addInt("cooldown", RandomTP.cooldown, "Amount in seconds for cooldown to use RandomTP again. Set to 0 for no cooldown.");
        randomTPSaveData.addUnsafeString("permission_level", RandomTP.permissionLevel, "Valid inputs are USER, MODERATOR, ADMIN, OWNER, SERVER");
        randomTPSaveData.addInt("min_X", RandomTP.minX, "Minimum X value to randomly pick from. Needs to be lower than max_X");
        randomTPSaveData.addInt("min_Y", RandomTP.minY, "Minimum Y value to randomly pick from. Needs to be lower than max_Y");
        randomTPSaveData.addInt("max_X", RandomTP.maxX, "Maximum X value to randomly pick from. Needs to be greater than min_X");
        randomTPSaveData.addInt("max_Y", RandomTP.maxY, "Maximum Y value to randomly pick from. Needs to be greater than min_Y");
        // System.out.println(randomTPSaveData.getScript());
        saveData.addSaveData(randomTPSaveData);
    }

}

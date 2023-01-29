package randomtp;

import necesse.engine.commands.PermissionLevel;
import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.engine.save.LoadData;
import necesse.engine.world.WorldEntity;
import net.bytebuddy.asm.Advice;

@ModMethodPatch(target = WorldEntity.class, name = "applyLoadData", arguments = {LoadData.class, boolean.class})
public class LoadWorldEntityPatch {

    @Advice.OnMethodEnter
    static void onEnter(@Advice.Argument(0) LoadData loadData) {
        LoadData newLoadData = loadData.getFirstLoadDataByName("RANDOMTP");
        if (newLoadData != null) {
            //System.out.println("NewLoadData != null");
            if (PermissionLevel.valueOf(RandomTP.permissionLevel) == null) {
                System.out.println("[RandomTP] Invalid permission level set. Current permission level \"" + RandomTP.permissionLevel + "\" isn't valid! Please see the config for valid options!");
            } else {
                RandomTP.confirmation = newLoadData.getBoolean("confirmation", true);
                RandomTP.cooldown = newLoadData.getInt("cooldown", 30);
                RandomTP.permissionLevel = newLoadData.getUnsafeString("permission_level", "USER");
                RandomTP.minX = newLoadData.getInt("min_X", -20000);
                RandomTP.minY = newLoadData.getInt("min_Y", -20000);
                RandomTP.maxX = newLoadData.getInt("max_X", 20000);
                RandomTP.maxY = newLoadData.getInt("max_Y", 20000);
            }
            //System.out.println(newLoadData.getScript());
        }
    }

}

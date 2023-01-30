package randomtp;

import necesse.engine.commands.CommandsManager;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.modLoader.annotations.ModEntry;

@ModEntry
public class RandomTP {

    public static boolean confirmation = true;
    public static boolean teleportToOcean = false;
    public static String permissionLevel = PermissionLevel.USER.name();
    public static int cooldown = 30;
    public static int minX = -20000;
    public static int minY = -20000;
    public static int maxX = 20000;
    public static int maxY = 20000;

    public void postInit() {
        CommandsManager.registerServerCommand(new RandomTPCommand());
    }

}

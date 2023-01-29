package randomtp;

import necesse.engine.commands.CommandsManager;
import necesse.engine.modLoader.annotations.ModEntry;

@ModEntry
public class RandomTP {

    public void postInit() {
        CommandsManager.registerServerCommand(new RandomTPCommand());
    }

}

package randomtp;

import necesse.engine.commands.CmdParameter;
import necesse.engine.commands.CommandLog;
import necesse.engine.commands.ModularChatCommand;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.util.TeleportResult;
import necesse.entity.levelEvent.LevelEvent;
import necesse.entity.levelEvent.TeleportEvent;
import necesse.gfx.GameColor;
import necesse.level.maps.Level;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RandomTPCommand extends ModularChatCommand {

    private final List confirmation;
    // Could use the sicknessTime in placement of this entirely, but would make it so can't teleport using scrolls or any other way for a while.
    // First Long is serverClient authentication
    // Second Long is System.getTimeInMillis + 1000 x 30
    // 30 second cooldown
    private final Map<Long, Long> cooldown;

    public RandomTPCommand() {
        super("randomtp", "Teleport to a Random Island", PermissionLevel.USER, false, new CmdParameter[0]);
        confirmation = new ArrayList<Long>();
        cooldown = new HashMap();
    }

    @Override
    public void runModular(Client client, Server server, ServerClient serverClient, Object[] args, String[] errors, CommandLog log) {
        // When player runs command, adds to confirmation list
        if (cooldown.containsKey(serverClient.authentication)) {
            // If current time is greater than last ran time + 30 seconds
            if (System.currentTimeMillis() >= cooldown.get(serverClient.authentication)) {
                // remove from cooldown
                cooldown.remove(serverClient.authentication);
            } else {
                // Else they need to wait
                log.addClient(GameColor.RED.getColorCode() + "[RandomTP] You're on cooldown till the next teleport. " +
                        "You need to wait " + ((cooldown.get(serverClient.authentication) - System.currentTimeMillis()) / 1000) + " more seconds.", serverClient);
                return;
            }
        }
        if (!confirmation.contains(serverClient.authentication)) {
            confirmation.add(serverClient.authentication);
            log.addClient(GameColor.RED.getColorCode() + "[RandomTP] Confirmation Needed. You are about to randomly teleport to a different island, you'll likely spawn in the ocean. Be sure to have a boat handy!", serverClient);
            log.addClient(GameColor.PURPLE.getColorCode() + "[RandomTP] Type this command again to confirm.", serverClient);
            return;
        }
        // When player runs command again removes them from list then executes code as normal
        confirmation.remove(serverClient.authentication);
        // Random X & Y Level coords between -20000 and 20000 for each
        final int randomX = new Random().nextInt(-20000, 20000);
        final int randomY = new Random().nextInt(-20000, 20000);
        // If level doesn't exist (probably won't) then generate the level
        if (server.world.levelManager.getLevel(new LevelIdentifier(randomX, randomY, 0)) == null) {
            server.world.generateNewLevel(randomX, randomY, 0);
        }
        final Level level = server.world.levelManager.getLevel(new LevelIdentifier(randomX, randomY, 0));
        if (serverClient.getLevel().isServerLevel()) {
            LevelEvent e = new TeleportEvent(serverClient, 200, level.getIdentifier(), 10.0F, (newLevel) -> new TeleportResult(true, server.world.levelManager.getLevel(level.getIdentifier()).getWorldEntity().spawnTile));
            serverClient.getLevel().entityManager.addLevelEventHidden(e);
        }
        log.addClient(GameColor.GREEN.getColorCode() + "[RandomTP] Teleported to " + level.getIdentifier().stringID, serverClient);
        cooldown.put(serverClient.authentication, System.currentTimeMillis() + (1000 * 30));
    }
}
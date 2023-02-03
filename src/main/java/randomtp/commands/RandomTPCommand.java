package randomtp.commands;

import necesse.engine.commands.CmdParameter;
import necesse.engine.commands.CommandLog;
import necesse.engine.commands.ModularChatCommand;
import necesse.engine.commands.PermissionLevel;
import necesse.engine.commands.parameterHandlers.PresetStringParameterHandler;
import necesse.engine.commands.parameterHandlers.StringParameterHandler;
import necesse.engine.localization.Localization;
import necesse.engine.network.client.Client;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.util.LevelIdentifier;
import necesse.engine.util.TeleportResult;
import necesse.entity.levelEvent.LevelEvent;
import necesse.entity.levelEvent.TeleportEvent;
import necesse.gfx.GameColor;
import necesse.level.maps.Level;
import randomtp.RandomTP;

import java.awt.*;
import java.util.List;
import java.util.*;

public class RandomTPCommand extends ModularChatCommand {

    private final List<Long> confirmation;
    // Could use the sicknessTime in placement of this entirely, but would make it so can't teleport using scrolls or any other way for a while.
    // First Long is serverClient authentication
    // Second Long is System.getTimeInMillis + 1000 x cooldown (default is 30)
    private final Map<Long, Long> cooldown;

    public RandomTPCommand() {
        super("randomtp", Localization.translate("randomtp", "commandDescription"), PermissionLevel.valueOf(RandomTP.permissionLevel.toUpperCase()), false,
                new CmdParameter("setting", new PresetStringParameterHandler("confirmation", "teleport_to_ocean", "permissionLevel", "cooldown", "min_x", "min_y", "max_x", "max_y"), true, false),
                new CmdParameter("value", new StringParameterHandler(), true, false));
        confirmation = new ArrayList<>();
        cooldown = new HashMap<>();
    }

    @Override
    public void runModular(Client client, Server server, ServerClient serverClient, Object[] args, String[] errors, CommandLog log) {
        if (serverClient == null) {
            System.out.println("This command can't be run through console.");
            return;
        }
        args:
        if (args.length > 0) {
            String setting = (String) args[0];
            String value = (String) args[1];
            if (setting == null)
                break args;
            else if (!serverClient.getPermissionLevel().equals(PermissionLevel.ADMIN)) {
                log.addClient(GameColor.RED.getColorCode() + "[RandomTP] You need to be an Admin to do that!", serverClient);
                return;
            }
            // Switch through all the setting options
            switch (setting) {
                case "confirmation":
                    if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                        RandomTP.confirmation = Boolean.parseBoolean(value);
                        log.addClient(GameColor.GREEN.getColorCode() + "[RandomTP] Set " + setting + " to " + value, serverClient);
                    } else {
                        log.addClient(GameColor.RED.getColorCode() + "[RandomTP] Only true/false values are allowed for changing " + setting + " .", serverClient);
                    }
                    return;
                case "teleport_to_ocean":
                    if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                        RandomTP.teleportToOcean = Boolean.parseBoolean(value);
                        log.addClient(GameColor.GREEN.getColorCode() + "[RandomTP] Set " + setting + " to " + value, serverClient);
                    } else {
                        log.addClient(GameColor.RED.getColorCode() + "[RandomTP] Only true/false values are allowed for changing " + setting + " .", serverClient);
                    }
                    return;
                case "permissionLevel":
                    if (PermissionLevel.valueOf(value.toUpperCase()).name != null) {
                        RandomTP.permissionLevel = value;
                        log.addClient(GameColor.GREEN.getColorCode() + "[RandomTP] Set " + setting + " to " + value, serverClient);
                    } else {
                        log.addClient(GameColor.RED.getColorCode() + "[RandomTP] Valid options are USER, MODERATOR, ADMIN, OWNER, SERVER.", serverClient);
                    }
                    return;
                case "cooldown":
                    if (isInt(value)) {
                        if (Integer.parseInt(value) >= 0) {
                            RandomTP.cooldown = Integer.parseInt(value);
                            log.addClient(GameColor.GREEN.getColorCode() + "[RandomTP] Set " + setting + " to " + value, serverClient);
                        } else {
                            log.addClient(GameColor.RED.getColorCode() + "[RandomTP] Cooldown cannot be set below 0.", serverClient);
                        }
                    } else {
                        log.addClient(GameColor.RED.getColorCode() + "[RandomTP] " + value + " is not a valid number. Please put a number greater than 0.", serverClient);
                    }
                    return;
                case "min_x":
                    if (isInt(value)) {
                        if (Integer.parseInt(value) > RandomTP.maxX) {
                            log.addClient(GameColor.RED.getColorCode() + "[RandomTP] Cannot set min_x greater than max_x (" + RandomTP.maxX + ").", serverClient);
                        } else {
                            RandomTP.minX = Integer.parseInt(value);
                            log.addClient(GameColor.GREEN.getColorCode() + "[RandomTP] Set " + setting + " to " + value, serverClient);
                        }
                    } else {
                        log.addClient(GameColor.RED.getColorCode() + "[RandomTP] " + value + " is not a valid number.", serverClient);
                    }
                    return;
                case "min_y":
                    if (isInt(value)) {
                        if (Integer.parseInt(value) > RandomTP.maxY) {
                            log.addClient(GameColor.RED.getColorCode() + "[RandomTP] Cannot set min_y greater than max_y (" + RandomTP.maxX + ").", serverClient);
                        } else {
                            RandomTP.minY = Integer.parseInt(value);
                            log.addClient(GameColor.GREEN.getColorCode() + "[RandomTP] Set " + setting + " to " + value, serverClient);
                        }
                    } else {
                        log.addClient(GameColor.RED.getColorCode() + "[RandomTP] " + value + " is not a valid number.", serverClient);
                    }
                    return;
                case "max_x":
                    if (isInt(value)) {
                        if (Integer.parseInt(value) < RandomTP.minX) {
                            log.addClient(GameColor.RED.getColorCode() + "[RandomTP] Cannot set max_x lower than min_x (" + RandomTP.maxX + ").", serverClient);
                        } else {
                            RandomTP.maxX = Integer.parseInt(value);
                            log.addClient(GameColor.GREEN.getColorCode() + "[RandomTP] Set " + setting + " to " + value, serverClient);
                        }
                    } else {
                        log.addClient(GameColor.RED.getColorCode() + "[RandomTP] " + value + " is not a valid number.", serverClient);
                    }
                    return;
                case "max_y":
                    if (isInt(value)) {
                        if (Integer.parseInt(value) < RandomTP.minY) {
                            log.addClient(GameColor.RED.getColorCode() + "[RandomTP] Cannot set max_y greater than min_y (" + RandomTP.maxX + ").", serverClient);
                        } else {
                            RandomTP.maxY = Integer.parseInt(value);
                            log.addClient(GameColor.GREEN.getColorCode() + "[RandomTP] Set " + setting + " to " + value, serverClient);
                        }
                    } else {
                        log.addClient(GameColor.RED.getColorCode() + "[RandomTP] " + value + " is not a valid number.", serverClient);
                    }
                    return;
                default:
                    log.addClient(GameColor.RED.getColorCode() + "[RandomTP] Valid settings to change are: confirmation, teleport_to_ocean, permissionLevel, cooldown, min_x, min_y, max_x, max_y.", serverClient);
            }
            return;
        }
        // If command is empty, then carry on
        // If cooldown is enabled (greater than 0)
        if (RandomTP.cooldown > 0)
            if (cooldown.containsKey(serverClient.authentication)) {
                // If current time is greater than last ran time + cooldown (default is 30)
                if (System.currentTimeMillis() >= cooldown.get(serverClient.authentication)) {
                    // remove from cooldown
                    cooldown.remove(serverClient.authentication);
                } else {
                    // Else they need to wait
                    log.addClient(GameColor.RED.getColorCode() + Localization.translate("randomtp", "teleportCooldown", "seconds", ((cooldown.get(serverClient.authentication) - System.currentTimeMillis()) / 1000)), serverClient);
                    return;
                }
            }
        // If confirmation is enabled
        if (RandomTP.confirmation)
            // When player runs command, adds to confirmation list
            if (!confirmation.contains(serverClient.authentication)) {
                confirmation.add(serverClient.authentication);
                log.addClient(GameColor.RED.getColorCode() + Localization.translate("randomtp", "teleportConfirmation", "ocean",
                        RandomTP.teleportToOcean ? Localization.translate("randomtp", "oceanMessage") : ""), serverClient);
                log.addClient(GameColor.PURPLE.getColorCode() + Localization.translate("randomtp", "teleportConfirmation2"), serverClient);
                return;
            } else {
                // When player runs command again removes them from list then executes code as normal
                confirmation.remove(serverClient.authentication);
            }
        // Random X & Y Level coords between min & max for both x & y.
        final int randomX = new Random().nextInt(RandomTP.minX, RandomTP.maxX);
        final int randomY = new Random().nextInt(RandomTP.minY, RandomTP.maxY);
        // If level doesn't exist (probably won't) then generate the level
        if (server.world.levelManager.getLevel(new LevelIdentifier(randomX, randomY, 0)) == null) {
            server.world.generateNewLevel(randomX, randomY, 0);
        }
        final Level level = server.world.levelManager.getLevel(new LevelIdentifier(randomX, randomY, 0));
        // Honestly don't know what this even checks really
        // I saw it once and figured better safe than sorry, so I kept it.
        if (serverClient.getLevel().isServerLevel()) {
            LevelEvent e = new TeleportEvent(serverClient, 200, level.getIdentifier(), 10.0F, (newLevel) -> new TeleportResult(true,
                    RandomTP.teleportToOcean ? new Point(server.world.levelManager.getLevel(newLevel.getIdentifier()).getWorldEntity().spawnTile.x,
                            server.world.levelManager.getLevel(newLevel.getIdentifier()).getWorldEntity().spawnTile.y) : new Point(server.world.levelManager.getLevel(newLevel.getIdentifier()).getWorldEntity().spawnTile.x * 32 + 16,
                            server.world.levelManager.getLevel(newLevel.getIdentifier()).getWorldEntity().spawnTile.y * 32 + 16)));
            serverClient.getLevel().entityManager.addLevelEventHidden(e);
        }
        log.addClient(GameColor.GREEN.getColorCode() + Localization.translate("randomtp", "teleportMessage", "level", level.getIdentifier().stringID), serverClient);
        // If cooldown is enabled (greater than 0) then add them to cooldown list of current time + 1000ms * cooldown setting.
        if (RandomTP.cooldown > 0)
            cooldown.put(serverClient.authentication, System.currentTimeMillis() + (1000L * RandomTP.cooldown));
    }

    // Cheeky way to check if string is an integer the safe way.
    private boolean isInt(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}

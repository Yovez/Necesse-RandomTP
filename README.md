# Teleport to a Random Island

Very small mod to randomly teleport to other islands. Completely server side! Only the server/host needs this mod, other player's don't need to do anything!

**One single command, /randomtp**

Default settings teleport you between -20000 & 20000 on both X & Y coords. You can change that in the world's world.dat file found in the world's zip file.

Features:
Completely configurable in the world.dat file.
Randomly teleport to different locations.
Configurable cooldown rate between teleports.
Configurable confirmation before teleporting.
Configurable Permission setting, defaults to everybody can use the command.
(From 1.4 and on) Change settings from in-game, more info below.
Completely server side! Only the server/host needs this mod, other player's don't need to do anything!

Default Settings:
```
RANDOMTP = {
  confirmation = true, // True/False to confirm when using RandomTP.
  teleport_to_ocean = false, // True/False. True means you'll spawn in the ocean as how the mod was created. False you'll spawn on the island's spawn point.
  cooldown = 30, // Amount in seconds for cooldown to use RandomTP again. Set to 0 for no cooldown.
  permission_level = USER, // Valid inputs are USER, MODERATOR, ADMIN, OWNER, SERVER
  min_X = -20000, // Minimum X value to randomly pick from. Needs to be lower than max_X
  min_Y = -20000, // Minimum Y value to randomly pick from. Needs to be lower than max_Y
  max_X = 20000, // Maximum X value to randomly pick from. Needs to be greater than min_X
  max_Y = 20000 // Maximum Y value to randomly pick from. Needs to be greater than min_Y
}
```

1.4 Update:
```
You can now change settings from in-game! No more worrying about messing up your save file.
Can change any setting from in-game by typing:
/randomtp [setting] [value]
These values behave exactly like in the save file, except if you make a mistake it'll tell you why.
Valid settings include: confirmation, teleport_to_ocean, permissionLevel, cooldown, min_x, min_y, max_x, max_y.
```

Interested in your own 24/7 Dedicated Necesse Server? Start one today! Use code **FIRST50** at checkout for half off your first month.

[![](https://i.imgur.com/MC7itkR.png)](https://apexnode.host)

Workshop Link: https://steamcommunity.com/sharedfiles/filedetails/?id=2924983797

Workshop ID: 2924983797

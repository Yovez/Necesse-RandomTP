**Teleport to a Random Island!**

Very small mod to randomly teleport to other islands. Completely server side! Only the server/host needs this mod, other player's don't need to do anything!

One single command, /randomtp

Default settings teleport you between -20000 & 20000 on both X & Y coords. You can change that in the world's world.dat file found in the world's zip file.

Features:
- Configurable to teleport to the Island in the ocean or directly in the center of the Island.
- Completely configurable in the world.dat file.
- Randomly teleport to different islands.
- Configurable cooldown rate between teleports.
- Configurable confirmation before teleporting.
- Configurable Permission setting, defaults to everybody can use the command.
- Completely server side! Only the server/host needs this mod, other player's don't need to do anything!

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

Want to contribute to the project? Have an issue? Maybe make a fork? Or request a feature?
Check it out on Github: https://github.com/Yovez/RandomTP

Workshop ID: 2924983797

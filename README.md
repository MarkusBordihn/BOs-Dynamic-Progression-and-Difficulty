# ⚔️ Dynamic Player Progression and Difficulty

[![Dynamic Player Progression and Difficulty Downloads](http://cf.way2muchnoise.eu/full_650016_downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/dynamic-player-progression-and-difficulty)
[![Dynamic Player Progression and Difficulty Versions](http://cf.way2muchnoise.eu/versions/Minecraft_650016_all.svg)](https://www.curseforge.com/minecraft/mc-mods/dynamic-player-progression-and-difficulty)

Dynamically balanced the progression and difficulty for new and experienced player on the same server.
In most cases it's difficult to balanced new players with experience players without frustrating one group.

## Features

- Dynamically adjust dealt damage (from the Player)
- Dynamically adjust hurt damage (to the Player)
- Experience points / levels for different kind of areas based on existing stats
- Customization over config file
- Weapon classes with weapon progression (more damage, faster, ...) (WIP)
- Scoreboard (WIP)
- Player specific title (WIP)

## What does the mod do ?

For new players it will increase the dealt damage and decrease hurt damage to the player.
For more experience players it will slight increase the dealt damage per weapon class (over time) and increase the hurt damage to the player for a more challenging experience with additional getting more experience and loot.

With each kill you will earn experience points for a specific areas and weapon classes, which is used to adjust the progression and difficulty per player dynamically.

This means new players will not get killed every few seconds and experience players will get a harder time to kill mobs and need to find new strategies and better equipment.

## 🎱 Commands

The mods has the following commands to get more insights in your stats.

- `/player_progression stats` will provide a overview for the player with their current stats.
- `/player_progression level overview` shows the calculated level and needed experience based on the settings from the configuration. (Op only)

## 🙋FAQ

### Is this a server side / client side mod❓

Most adjustments happen on the server, but for the next features like the player titles and mini hud the mod is required on the client as well.
You should install it on the server and client to make sure everything works as expected for the players.

### Does it work with xyz❓

The adjustments are prioritized with a LOW priority, which mean every mod with a normal or higher priority is able to overwrite or cancel the adjustments. This makes sure it's compatible with other mods with similar features.

## Version Status Overview 🛠️

| Version        | Status                |
| -------------- | --------------------- |
| Fabric Version | ❌ Not planned        |
| Forge 1.16.5   | ❌ Not planned        |
| Forge 1.17.1   | ❌ Not planned        |
| Forge 1.18.1   | ❌ Not planned        |
| Forge 1.18.2   | ✔️ Active development |

## License

The MIT [LICENSE.md](LICENSE.md) applies only to the code in this repository. Images, models and other assets are explicitly excluded.

# Changelog for Dynamic Progression and Difficulty (1.19.1)

## Note

This change log includes the summarized changes.
For the full changelog, please go to the [Git Hub History][history] instead.

### 5.2.0

- Fixed Fabric port dependencies.
- Added additional item class icons.
- Added dealt and hurt damage adjustments to the player stats screen.
- Improved item tooltip manager.

### 5.1.0 beta

- Added registry command to show all unregistered / registered items.
- Added item name mapping for better item detection.
- Added additional check to ignore item to avoid false positive detection.

### 5.0.0 beta

Next major release with new features and improvements.
Note: This version is still in beta and should be used with caution.

- Refactored code and improved performance
- Added Fabric port
- Added extra player stats button and stats overview
- Maximum level is now 150.
- Improved network sync and client side payload for stats.
- Improved death penalty calculation.

### 2023.01.15

- Added Magic Spell Book weapon class.

### 2022.12.18

- Optimized stats calculation by skipping empty weapon classes
- Improved death penalty visibility to avoid confusions about missing experience.

### 2022.12.12

- Added Tinkers Construct to supported mods after
  fixed [[#5007](https://github.com/SlimeKnights/TinkersConstruct/issues/5007)].

### 2022.11.18

- Fixed miss-categorization for specific "hammer" items.
- Disable Player vs. Player states and calculations, if PvP is disabled.

### 2022.10.31

- Added Paxel weapon class for paxel and aiot weapons.
- Added additional weapons for newer mods.
- Fixed smaller visual issues with delayed updates.

### 2022.10.09

- Fixed NullPointerException with specific arrows entities.

### 2022.09.18

- Improved translation and visual appearance.
- Added de_de translation.

### 2022.09.11

- Added rus_RU translation, thanks to XionioXMaster.

### 2022.09.03

- Added claw, claymore, fist, mace and spear weapon class.

### 2022.08.25

- Added Hand to Hand, Katana and Tachi Weapon class.
- Improved compatibility with epic fight mod.

### 2022.08.21

- Added Scythe, Shovel, Staff and Wand Weapon class.
- Improved mapping time and memory usage.

### 2022.08.20

- Improved weapon class detection and stats overview.
- Implemented player message for level ups.
- Improved overall performance.

### 2022.08.19

- Added dynamic weapon class detection to covering more mods without manually defining them.
  **Note:** Please delete your
  existing `config/dynamic_player_progression_and_player_difficulty-common.toml` file to regenerate
  the new one.

### 2022.08.17

- Added configurable durability adjustments for weapons like axe, hoe, pickaxe so that they could be
  better used in fights.

### 2022.08.16

- Added new weapon classes with additional support for about 400 modded items.
- Optimized performance.

### 2022.08.14

- Fixed client side death counter.

### 2022.08.12

- Changed static text to translated text to allow better language support.
- Added additional mod items to the default list.
  **Note:** It could be that you need to delete your
  existing `config/dynamic_player_progression_and_player_difficulty-common.toml` file to regenerate
  the new one.

### 2022.08.10

- Added stats button to directly show current stats and level.

### 2022.08.02

- Added additional mod support
- Added client sync for stats
- Improved tooltips to display synced client side data
- Adjusted default config values
  **Note:** It could be that you need to delete your
  existing `config/dynamic_player_progression_and_player_difficulty-common.toml` file to regenerate
  the new one.

### 2022.08.01

- Added Weapon classes and adjustments based on experience
- Added prepared tooltips for supported weapons

### 2022.07.30

- Released first beta version for more detailed live testing.

[history]: https://github.com/MarkusBordihn/BO-s-Dynamic-Player-Progression-and-Difficulty/commits/

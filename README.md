# ⚔️ LobbyPvP - Simple Hub PvP plugin [![GitHub release](https://img.shields.io/github/v/release/YourName/LobbyPvP?style=flat-square)](https://github.com/YourName/LobbyPvP/releases) [![Spigot Rating](https://img.shields.io/spiget/rating/00000?label=Spigot&style=flat-square)](https://www.spigotmc.org/resources/your-plugin.00000/)

![Plugin Banner](https://imgur.com/a/rORkNPZ)

**LobbyPvP** is an interesting plugin for your minecraft server that will allow players to spend time fighting each other.

## ✨ Features
- 🔧 Great customization
- ⚔️ Killing effects and abilities
- ✅ Configuration reloading
- ⚡ Easy to set up
- 🌟 A fun plugin for everyone

## 📜 How it works?
#### When a player equips the sword, PvP is enabled for them, allowing combat with other players who also have PvP activated. Once the sword is unequipped, the player exits PvP mode and can no longer be attacked.

## 📦 Installation
1. Download the latest version
2. Place `LobbyPvP.jar` in your server's `/plugins/` folder
3. Restart your server (`/reload` or full restart)

## ⚙️ Configuration
Main config file: `plugins/LobbyPvP/config.yml`

Default config:
```yaml
# LobbyPvP by Ulik
# Have an issue? Report it on our Spigot page!

# --------------------------------------------------
# General
# --------------------------------------------------

# Change the cooldown when enabling/disabling PvP (seconds). Set to 0 to disable.
enable-cooldown: 3
disable-cooldown: 3

# Is there a need for a delay before issuing the sword?
# Enable it if you have a plugin that cleans inventory when logging in.
delay-before-giving-sword: false

disabled-worlds:
  - world_nopvp

# If enabled, the speed effect will be given when right-clicking.
right-click-ability:
  enable: true
  cooldown: 15
  speed:
    duration: 5
    amplifier: 0
  strength:
    duration: 5
    amplifier: 0



#  If enabled, the player will receive a regeneration effect when killing another player.
regeneration-on-kill:
  enable: true
  duration: 5
  amplifier: 2

#  Strikes a dead player with lightning without causing damage
lightning-effect-on-kill:
  enable: true


# --------------------------------------------------
# Messages
# --------------------------------------------------

lang:
  prefix: "&b⚔ LobbyPvP ⚔ &6>>> "
  pvp-enabled: '&6&l>&r&a PvP enabled!'
  pvp-enabling: '&aPvP enabling in %time% second(s).'
  pvp-disabled: '&6&l>&r&c PvP disabled!'
  pvp-disabling: '&cPvP disabling in %time% second(s).'
  reloaded: '&aSuccessfully reloaded the LobbyPvP config!'
  kill: '&a%victim% &fwas killed by &c%killer%'
  disabled-in-world: "&cYou may not use this in this world!"
  no-permissions: "&cYou don't have permission!"
  ability-cooldown: "&7The ability will be available in &e%time% &7s"
  ability-activate: "&aYou've gained extra &6damage &aand &6speed&a!"
  usage: "&cUsage: /lobbypvp reload"

# --------------------------------------------------
# Item Customisation
# --------------------------------------------------

items:
  weapon:
    slot: 4
    material: DIAMOND_SWORD
    name: '&aPvP Sword &e(Hold to PvP)'
    # Lvl of sharpness enchantment on sword. Set to 0 to disable.
    sharpness: 1

  helmet:
    # Item to use as the helmet
    material: DIAMOND_HELMET
    # Lvl of protection enchantment on helmet. Set to 0 to disable.
    protection: 1

  chestplate:
    # Item to use as the chestplate
    material: DIAMOND_CHESTPLATE
    # Lvl of protection enchantment on chestplate. Set to 0 to disable.
    protection: 1

  leggings:
    # Item to use as the leggings
    material: DIAMOND_LEGGINGS
    # Lvl of protection enchantment on leggings. Set to 0 to disable.
    protection: 1

  boots:
    # Item to use as the boots
    material: DIAMOND_BOOTS
    # Lvl of protection enchantment on boots. Set to 0 to disable.
    protection: 1
```
## 🎮 Admin commands
#### `/lobbypvp reload `- to reload the configuration. Permission: `lobbypvp.reload`

## 📊 Compatibility
#### Native Version: 1.21.4
#### Supported Versions: 1.21.x
#### Tested Servers: Paper, Spigot

## ❓ Support
#### if you have a problem, please refer to the plugin page in the discussion section 



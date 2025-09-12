# ⚔️ LobbyPvP - Simple Hub PvP plugin

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

###########################################################################################################

# Worlds where a plugin shouldn't work.
# Will not be counted if "worlds-white-list-mode" is enabled
disabled-worlds:
  - world_nopvp

# If enabled, the plugin will only work in the worlds that
# are listed in the "white-list-worlds". It may be useful if you have one lobby and many ordinary worlds.
worlds-white-list-mode:
  enable: false
  white-list-worlds:
    - world_pvp

###########################################################################################################

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

# Should the pvp activation countdown sound be played?
pvp-activation-deactivation-countdown-sound:
  enable: true


# --------------------------------------------------
# Messages
# --------------------------------------------------

# If you don't want the message to be displayed, leave the value in the buckets empty.
lang:
  prefix: "&b⚔ LobbyPvP ⚔ &6>>> "
  pvp-enabled: '&6&l>&r&a PvP enabled!'
  pvp-enabling: '&aPvP enabling in %time% second(s).'
  pvp-disabled: '&6&l>&r&c PvP disabled!'
  pvp-disabling: '&cPvP disabling in %time% second(s).'
  reloaded: '&aSuccessfully reloaded the LobbyPvP config!'
  disabled-in-world: "&cYou may not use this in this world!"
  no-permissions: "&cYou don't have permission!"
  kill-message: "&6>>> &a%victim% &fwas killed by &c%killer%"
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



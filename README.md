A simple text/gui based Giveaways plugin

**Note:** This plugin is in early development so some features may not yet be available.

![Screenshot (50)](https://github.com/wacko77/Giveaways/assets/46010708/b6b6a0f4-0474-4bd0-b015-1ff6c4914647)

## Features:
- Create unique and fun giveaways
- MySQL support
- Flat File support
- Placeholder API support
- Customizable text options in messages.yml
- Choose your storage option in config.yml
- Custom durations
- Permissions work with a valid permission manager plugin such as LuckPerms

## Commands:
- **/giveaways**
  - opens a gui of all active giveaways
- **/giveaway create**
  - creates a giveaway based on what item you're holding (default duration = 60s)
- **/giveaway create (duration)**
  - creates a giveaway and sets the duration (min 60s, max 1d)
- **/giveaway forcestop**
  - forcestops the current giveaway

## Admin Commands:
- **/giveaways removeall**
  - removes all giveaways from active giveaways
- **/giveaway remove (giveaway id)**
  - removes a specific giveaway
- **/giveaway create (duration)**
  - allows an admin to set a duration with no max

## Permissions:
- **giveaways.command**
  - allows the opening of the giveaway gui
- **giveaways.start**
  - allows a player to start a giveaway
- **giveaways.duration**
  - allows a duration to be set
- **giveaways.admin**
  - adds all admin commands
- **giveaways.***
  - children: giveaways.command, giveaways.start, giveaways.duration

## Placeholders
- **%giveaways.participants%**
  - returns the participants for the specific giveaway
- **%giveaways.host%**
  - returns the host for the specific giveaway
- **%giveaways.duration%**
  - returns the remaining time of the specific giveaway

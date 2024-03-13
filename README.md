A simple text/gui based Giveaways plugin

## Features:
- MySQL support
- Flat File support
- Placeholder API support
- Customizable text options in config.yml
- Choose your storage option in config.yml

## Commands:
- **/giveaways**
  - opens a gui of all active giveaways
- **/giveaway create**
  - creates a giveaway based on what item you're holding
- **/giveaway create (duration)**
  - creates a giveaway and sets the duration (min 60s, max 1d)

## Admin Commands:
- **/giveaways removeall**
  - removes all giveaways from active giveaways
- **/giveaway remove (giveaway id)**
  - removes a specific giveaway
- **/giveaway create (duration)**
  - allows an admin to set a duration with no max

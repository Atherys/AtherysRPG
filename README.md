# AtherysRPG
An RPG plugin created for the A'therys Horizons server

## Commands - Permissions

### Base Commands

| Aliases                   | Permission                    | Description                                                         |
|---------------------------|-------------------------------|---------------------------------------------------------------------|
| `/attributes`, `/attribs` | `atherysrpg.attributes.base`  | View own attributes                                                 |
| `/attributes reset`       | `atherysrpg.attributes.reset` | Reset own attributes ( and refund experience spent on attributes )  |
| `/experience`, `/xp`      | `atherysrpg.experience.base`  | View own experience                                                 |
| `/skills`                 | `atherysrpg.skills.base`      | Displays own skills                                                 |
| `/skills pick`            | `atherysrpg.skills.pick`      | Unlocks a new skill, if you have a link to it and enough experience |
| `/skills remove`          | `atherysrpg.skills.remove`    | Removes a skill from own list of skills                             |
| `/skills reset`           | `atherysrpg.skills.reset`     | Reset own skills and returns the spent experience                   |

### Admin Commands

| Aliases                                                | Permission                      | Description                                 |
|--------------------------------------------------------|---------------------------------|---------------------------------------------|
| `/attributes add <player> <attributeType> <amount>`    | `atherysrpg.attributes.add`     | Add attributes to another player            |
| `/attributes remove <player> <attributeType> <amount>` | `atherysrpg.attributes.remove`  | Remove attributes from another player       |
| `/attributes enchant <attributeType> <amount>`         | `atherysrpg.attributes.enchant` | Add attributes to the currently held item   |
| `/experience add <player> <amount>`                    | `atherysrpg.experience.add`     | Add experience to another player            |
| `/exerience remove <player> <amount>`                  | `atherysrpg.experience.remove`  | Remove experience from another player       |
| `/spawnitem <item> <quantity>`                         | `atherysrpg.spawnitem`          | Spawn an item as configured in `items.conf` |

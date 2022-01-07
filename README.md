# Nox

Minecraft mob and difficulty overhaul for Fabric.

## Mob Changes

### Misc..
* Strafing is faster
* Pouncing goes farther and faster
* Melee Goal checks are done more often (barely noticable)
* (Almost all) Mobs can see through walls
* Target finding is more immediate, no random delay

### All monsters..
* Do not have friendly fire 
* Projectiles phase through allies
* Are angered when ran into (relevant to neutral monsters like Endermen)
* +50% base health
* +50% base follow range

### All Golems..
* Projectiles phase through allies
* +100% base health
* +100% base follow range

### Bow AI..
* Prefers a farther range
* Does not shoot shielding entities (you must bait it out)

### Crossbow AI..
* Charges crossbow first and foremost
* Prefers a farther range
* Does not shoot shielding entities (unless the user has piercing)
* Strafes

### Projectile AI..
* Prefers a farther range
* Strafes

### All Zombies..
* Take reduced knockback (unless they're a baby)
* Periodically pounce at their target
* Avoid and flee from the sun (if it affects them)
* Can mine blocks

### All Skeletons..
* Flee from the sun
* Can swim

### Spiders..
* Avoid the sun
* Are immune to fall damage
* Places a cobweb at the victims location upon successful attack

### Creepers..
* Flee blocking entities
* Pounce at their target
* Move during ignition
* Breach walls
* Continue to ignite without line of sight
* Only continue to ignite within a 4 block radius (originally 7 blocks)

### Endermen..
* Can break walls
* Apply 10 seconds of Blindness on aggro
* Apply 10 seconds of Blindness upon successful attack
* Teleport when damaged
* Do not teleport when damaged by being on fire or magic

### Slimes..
* +150% base health
* +30% base speed
* +450% base follow range
* Spawn naturally
* Jump constantly
* Are invulnerable to fall and non-armor-piercing projectile damage
* Spawn a short-lived Poison II effect cloud on death, with a radius proportional to their size

### Witches..
* Spawn more frequently
* Use lingering potions with buffed effect clouds
* Use stronger Slowness
* Are invulnerable to magic and non-armor-piercing projectile damage
* Use better beneficial potions (stronger or longer duration)
* Do not drink when target is in close proximety (7 blocks)

### Wither Skeletons..
* Take reduced knockback
* Can spawn with a bow
* Wither Skeleton Archers: +50% damage
* APply 4 seconds of Wither to the target when in close proximety (2 blocks)

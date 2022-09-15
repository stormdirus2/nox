# Nox

Minecraft mob and difficulty overhaul for Fabric.

Designed with a difficulty of Hard in mind.

In beta.

## Sleep Changes

The size of the box used to detect nearby monsters and prevent sleep has been massively increased.

It spans 101 blocks in width and length, with the center position lying straight on the bed.
There is a minimum upper Y-limit of 20 blocks above the bed position and a minimum lower Y-limit of 20 blocks below the bed position.

The upper Y-limit has to at least go *up* to sea level, and the lower Y-limit has to at least go *down* to sea level.

This means that sky bases will still have to worry about mobs below them on the ground, and cave bases will have to worry about everything above them beneath the surface.

The Glowing status effect is applied to monsters detected by this.

## Misc Changes

### In general..
* Strafing is faster
* Pouncing goes farther and faster
* Melee Goal checks are done more often (barely noticeable)
* (Almost all) Mobs can see through walls
* Target finding is more immediate, no random delay

## Block Breaking Info

Mobs that can mine blocks, only can do so for wooden-level blocks.

Of those blocks, blocks of non-wood material that have a hardness value equal or more than 3 cannot not be mined.

3 or more hardness blocks include, but are not limited to: obsidian, endstone, deepslate, most metal or gem blocks, and their derivatives.

Experimentation is encouraged.

## Monster Changes

### All monsters..
* Do not have friendly fire 
* Are angered when ran into (relevant to neutral monsters like Endermen)
* +50% base follow range
* That naturally spawn with random equipment (i.e. Skeletons and Zombies) will now spawn with better equipment based on the closest Player's equipment and the Local Difficulty

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
* Have increased movement speed (+25% base)
* Avoid and flee from the sun (if it affects them)
* Can mine blocks

### All Skeletons..
* Flee from the sun
* Can swim

### Spiders..
* Avoid the sun
* Are immune to fall damage
* Places a cobweb at the victims' location upon successful attack

### Cave Spiders..
* Inherent changes from Spiders
* Instead of placing a cobweb, apply 10 seconds of Slowness II

### Creepers..
* Flee blocking entities
* Move during ignition
* Breach walls
* Continue to ignite without line of sight
* Only continue to ignite within a 4 block radius (originally 7 blocks)

### Endermen..
* Can break blocks
* Apply 7 seconds of Darkness on aggro
* Apply 7 seconds of Darkness upon successful attack
* Teleport when damaged
* Do not teleport when damaged by being on fire or magic

### Slimes..
* +150% base health
* +30% base speed
* +150% base follow range
* Spawn naturally
* Jump constantly
* Are invulnerable to fall and non-armor-piercing projectile damage
* Spawn a short-lived Poison II effect cloud on death, with a radius and windup proportional to their size

### Witches..
* Spawn more frequently
* Use lingering potions with buffed effect clouds
* Throw potions 25% more often
* Can drink while throwing potions
* Use stronger Slowness
* Are invulnerable to magic
* Use better beneficial potions (stronger or longer duration)
* Run away while drinking a potion

### Wither Skeletons..
* Spawn naturally (in the Nether)
* Take reduced knockback
* Can spawn with a bow
* Wither Skeleton Archers: +50% damage
* Apply 4 seconds of Wither to the target when in close proximity (2 blocks)
* Wither Skeleton Skulls are fire-resistant

### Blazes..
* Strafe
* Prefer a farther range
* Spawn naturally (in the Nether)
* Shoot fireballs twice as often
* Do not shoot shielding entities (you must bait it out)
* Ignite the target on fire for 4 seconds when in close proximity (2 blocks)
* Blaze Rods are fire-resistant (as well as Blaze Powder)

### Ghasts..
* +150% base health
* Spawn more frequently
* Fireballs from are twice as powerful
* Fireballs no longer deal 1000 damage to Ghasts (:concern:)
* Fireball cooldown is 3 times less
* Fireballs from Ghasts bypass shields
* Ghast Tears are fire-resistant

### Magma Cubes
* Inherent changes from Slimes
* Ignite the target on fire for 4 seconds upon successful attack
* On death, instead of a Poison II cloud, Magma Cubes generate lava proportionate to their size
* Magma Cream is fire-resistant

### Piglins..
* Can mine blocks (Brutes as well)
* Aggro unless a Player is wearing only golden armor (you can wear just golden boots)

### Pillagers..
* Can mine blocks

### Vindicators..
* Can mine blocks
* Take reduced knockback

### Evokers..
* +50% base health
* Are invulnerable to magic and non-armor-piercing projectile damage

### Shulkers..
* Apply 5 seconds of Blindness via their projectiles

### Phantoms..
* Phase through blocks (but do not attempt attacks through them)
* Apply 15 seconds of Weakness upon successful attack
* Attacks bypass shields

### Silverfish..
* +100% base speed
* Lunge at their target
* Are invulnerable to fall, drown, and suffocation damage
* Apply 15 seconds of Mining Fatigue III upon successful attack

### Endermites..
* +60% base speed
* Are invulnerable to fall and suffocation damage
* Teleports the victim like a Chorus Fruit upon successful attack

### Guardians..
* Place water upon death
* Spawn naturally

### Drowned..
* Have increased speed in water
* Spawn more often naturally

### Husks..
* Apply Hunger II instead of Hunger I

### Strays..
* Apply Slowness II instead of Slowness I

## Boss Changes

### Wither..
* 200% base health (for a total of 600 HP)
* Rapidly breaks blocks around it
* Spawns 3 Wither Skeletons every 60 seconds when within a 7 block range of the target

### Ender Dragon..
* 300% base health (for a total of 600 HP)
* Is invulnerable to explosion damage
* End Crystals within range cannot be damaged unless they are currently connected to the Ender Dragon
* Dragon Fireballs are faster, fast-acting, and deal double damage
* Periodically fires Dragon Fireballs, regardless of current phase, when outside a 7 block radius
* Dragon Fireball phase fires numerous fireballs
* Attacks much more frequently
* Stalls much less
* Charges at the player much more frequently, and for longer
* Stays in the firebreath phase for half as much time

## Golem Changes

### All Golems..
* Projectiles phase through allies
* +100% base health
* +100% base follow range

### Iron Golems..
* Attack in an AoE around their target (like a sweep attack)

### Snow Golems..
* Fire snowballs twice as often
* Snowballs from Snow Golems deal 4 damage
* +40% max range

# LaserCore

WARNING: This Project **is not finished** yet. Nearly no features are build yet!

LaserCore is an Open Source API for creating LaserTag Games in Minecraft.

Planed Features:
<ul>
    <li>Block Hit Event</li>
    <li>Player Hit Event</li>
</ul>

## How To Build:

Run `gradle build` to Build the Project. The Jar File will be located in: `build/libs/`

## How To Use:

#### Get the API:
```java
LaserCore.getLaserCore();
```

#### Get a gun item:
```java
ItemStack gun = LaserCore.getLaserCore().generateGunItem();
```

#### Get any item as a gun item:
```java
ItemStack customGun = new ItemStack(Material.IRON_HOE);
customGun = LaserCore.getLaserCore().generateGunItem();
```

#### Check if any item is a gun:
```java
ItemStack inHand = player.getInventory().getItemInHand();
boolean isLaserCoreGun = LaserCore.getLaserCore().isGun(inHand);
```
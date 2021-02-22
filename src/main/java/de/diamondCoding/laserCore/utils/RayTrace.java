package de.diamondCoding.laserCore.utils;

import de.diamondCoding.laserCore.events.LaserBlockHitEvent;
import de.diamondCoding.laserCore.events.LaserEntityHitEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class RayTrace {

    World world;
    Vector origin, direction;
    double reach, accuracy;

    /**
     * Makes a really cool RayCast
     *
     * @param world     The world for the RayCast
     * @param origin    The Origin of the raytrace, so where the raytrace starts
     * @param direction The direction of the raytrace
     * @param reach     how long the raytrace should go (in blocks)
     * @param accuracy  how accurate the raytrace should be (in blocks), so in how small increments it goes
     */
    public RayTrace(World world, Vector origin, Vector direction, double reach, double accuracy) {
        this.world = world;
        this.origin = origin;
        this.direction = direction;
        this.reach = reach;
        this.accuracy = accuracy;
    }

    //get a point on the raytrace at X blocks away
    public Vector getPostion(double blocksAway) {
        return origin.clone().add(direction.clone().multiply(blocksAway));
    }

    /**
     * This Methode traverses the RayCast till it hits something
     */
    public void playerShoot(Player player, boolean withParticles, Color laserColor) {
        for (double d = 0; d <= reach; d += accuracy) {
            Vector position = getPostion(d);
            if (withParticles) {
                double yOffset = (1.0D - (d / 3.0D)) * -0.3D;
                if(yOffset > 0.0D) yOffset = 0.0D;
                Vector particleVec = position.clone().add(new Vector(0, yOffset, 0));
                world.spigot().playEffect(particleVec.toLocation(world), Effect.COLOURED_DUST, 0, 1, laserColor.getRed(), laserColor.getGreen(), laserColor.getBlue(), 0.02F, 0, 64);
            }
            Location location = position.toLocation(world);

            //test for entity hit
            Entity[] entities = location.getChunk().getEntities();
            for (Entity entity : entities) {
                if (entity == null || (player != null && entity == player)) continue;
                BoundingBox boundingBox = new BoundingBox(entity);
                if (intersects(position, boundingBox.min, boundingBox.max)) {
                    //if a entity was hit we trigger that event
                    LaserEntityHitEvent laserEntityHitEvent = new LaserEntityHitEvent(entity, player);
                    Bukkit.getServer().getPluginManager().callEvent(laserEntityHitEvent);
                    if (!laserEntityHitEvent.isCancelled()) return; //if the event wasn't canceled we can stop the laser
                }
            }

            //test for block hit
            Block block = world.getBlockAt(location);
            if (block != null) {
                BoundingBox boundingBox = new BoundingBox(block);
                if (block.getType() != Material.AIR && intersects(position, boundingBox.min, boundingBox.max)) {
                    //if a block was hit we trigger that event
                    LaserBlockHitEvent laserBlockHitEvent = new LaserBlockHitEvent(block, player);
                    Bukkit.getServer().getPluginManager().callEvent(laserBlockHitEvent);
                    if (!laserBlockHitEvent.isCancelled()) return; //if the event wasn't canceled we can stop the laser
                }
            }

        }
    }

    /**
     * Tests if the position vector is in between the min and max vector
     *
     * @param position the postion vector
     * @param min      the minimal vector
     * @param max      the maximal vector
     * @return if it intersects
     */
    public static boolean intersects(Vector position, Vector min, Vector max) {
        return !(
                (position.getX() < min.getX() || position.getX() > max.getX())
                || (position.getY() < min.getY() || position.getY() > max.getY())
                || (position.getZ() < min.getZ() || position.getZ() > position.getZ())
        );
    }

}
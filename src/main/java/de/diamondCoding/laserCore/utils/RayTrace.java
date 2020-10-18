package de.diamondCoding.laserCore.utils;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;

public class RayTrace {

    World world;
    Vector origin, direction;
    double reach, accuracy;

    Entity rayCaster;

    /**
     * Makes a realy cool raycast
     *
     * @param world The world or the raycast
     * @param origin The Origin of the raytrace, so where the raytrace starts
     * @param direction The direction of the raytrace
     * @param reach how long the raytrace should go (in blocks)
     * @param accuracy how accurate the raytrace should be (in blocks), so in how small increments it goes
     */
    public RayTrace(World world, Vector origin, Vector direction, double reach, double accuracy) {
        this.world = world;
        this.origin = origin;
        this.direction = direction;
        this.reach = reach;
        this.accuracy = accuracy;
    }

    public void setRayCaster(Entity rayCaster) {
        this.rayCaster = rayCaster;
    }

    //get a point on the raytrace at X blocks away
    public Vector getPostion(double blocksAway) {
        return origin.clone().add(direction.clone().multiply(blocksAway));
    }

    /**
     * This Methode traveses the raycast till it hits something
     *
     * @return The Hit block
     */
    public RayTraceHit traverseTillHit(boolean withParticles) {
        Collection<Entity> entities = world.getNearbyEntities(origin.toLocation(world), reach, reach, reach);
        for (double d = 0; d <= reach; d += accuracy) {
            Vector position = getPostion(d);
            if(withParticles) world.spigot().playEffect(position.toLocation(world), Effect.COLOURED_DUST, 0, 1, 255, 0, 0, 1, 0, 64); //maybe build player specific effects
            Location location = position.toLocation(world);

            //test for entity hit
            for (Entity entity : entities) {
                if(entity==null || (rayCaster!=null && entity == rayCaster)) continue;
                BoundingBox boundingBox = new BoundingBox(entity);
                if(intersects(position, boundingBox.min, boundingBox.max)){
                    return new RayTraceHit(entity);
                }
            }

            //test for block hit
            Block block = world.getBlockAt(location);
            if(block != null) {
                BoundingBox boundingBox = new BoundingBox(block);
                if(block.getType() != Material.AIR && intersects(position, boundingBox.min, boundingBox.max)){
                    return new RayTraceHit(block);
                }
            }
        }
        return null;
    }

    /**
     * Tests if the position vector is in between the min and max vector
     *
     * @param position the postion vector
     * @param min the minamal vector
     * @param max the maximal vectoe
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
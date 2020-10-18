package de.diamondCoding.laserCore.listeners;

import de.diamondCoding.laserCore.LaserCore;
import de.diamondCoding.laserCore.utils.RayTrace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GunListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent playerInteractEvent) {
        Player player = playerInteractEvent.getPlayer();
        ItemStack inHand = player.getInventory().getItemInHand();
        if (inHand != null && LaserCore.getLaserCore().isGun(inHand)) {
            RayTrace rayTrace = new RayTrace(player.getWorld(), player.getEyeLocation().toVector(), player.getEyeLocation().getDirection(), 256D, 0.5D);
            rayTrace.setRayCaster(player);
            rayTrace.traverseShoot(player, true);
            playerInteractEvent.setCancelled(true);
            player.updateInventory();
        }
    }

}

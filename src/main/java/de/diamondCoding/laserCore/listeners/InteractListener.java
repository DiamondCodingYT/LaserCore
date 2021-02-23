package de.diamondCoding.laserCore.listeners;

import de.diamondCoding.laserCore.LaserCore;
import de.diamondCoding.laserCore.configuration.IntValue;
import de.diamondCoding.laserCore.configuration.exceptions.UnknownConfigValueException;
import de.diamondCoding.laserCore.configuration.exceptions.WrongConfigValueTypeException;
import de.diamondCoding.laserCore.events.PlayerShotsPhaserEvent;
import de.diamondCoding.laserCore.utils.RayTrace;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent playerInteractEvent) {
        Player player = playerInteractEvent.getPlayer();
        ItemStack inHand = player.getInventory().getItemInHand();
        if (inHand != null && LaserCore.getLaserCore().isGun(inHand)) {
            //cancel normal behaviour
            playerInteractEvent.setCancelled(true);
            player.updateInventory();

            //check for cooldown
            if(LaserCore.getLaserCore().getCooldownHandler().hasCooldown(player)) return;

            //fire shot event
            IntValue intValue;
            try {
                intValue = LaserCore.getLaserCore().getConfigurationHandler().getIntValue("gunCooldown");
            } catch (WrongConfigValueTypeException | UnknownConfigValueException e) { //this would be weird
                e.printStackTrace();
                return;
            }
            int cooldownMilliseconds = intValue.getValue(); //When we have features Like burst this can be set to something smaller
            PlayerShotsPhaserEvent playerShotsPhaserEvent = new PlayerShotsPhaserEvent(player, Color.fromRGB(255, 0, 0), cooldownMilliseconds);
            Bukkit.getServer().getPluginManager().callEvent(playerShotsPhaserEvent);
            if (playerShotsPhaserEvent.isCancelled()) return; //if the event was canceled we dont need to do the shoot

            //set cooldown
            LaserCore.getLaserCore().getCooldownHandler().setCooldown(player, playerShotsPhaserEvent.getCooldownMilliseconds());

            //do shoot stuff
            player.playSound(player.getLocation(), Sound.SHOOT_ARROW, 2.0F, 2.0F);
            RayTrace rayTrace = new RayTrace(player.getWorld(), player.getEyeLocation().toVector(), player.getEyeLocation().getDirection(), 256D, 0.5D);
            rayTrace.playerShoot(player, true, playerShotsPhaserEvent.getLaserColor());

        }
    }

}

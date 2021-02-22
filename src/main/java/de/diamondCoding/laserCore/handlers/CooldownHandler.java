package de.diamondCoding.laserCore.handlers;

import de.diamondCoding.laserCore.LaserCore;
import de.diamondCoding.laserCore.utils.Cooldown;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CooldownHandler {

    @Getter
    private final HashMap<Player, Cooldown> cooldowns;

    public CooldownHandler() {
        cooldowns = new HashMap<>();
        Bukkit.getScheduler().runTaskTimerAsynchronously(LaserCore.getLaserCore(), this::tick, 1L, 1L);
    }

    //I should thread safe this soon!
    private void tick() {
        Set<Player> remove = new HashSet<>();
        cooldowns.forEach((player, cooldown) -> {
            if(cooldown.getEnd() < System.currentTimeMillis()) {
                remove.add(player);
            }
            if(player.isOnline()) {
                updateGunItemsCooldown(player);
            }
        });
        remove.forEach(cooldowns::remove);
    }

    /**
     * Checks if the player has a cooldown
     *
     * @param player The Players who's cooldown will be set
     * @return if the player is still in cooldown. (Can't shoot the Phaser at the moment)
     */
    public boolean hasCooldown(Player player) {
        if(!cooldowns.containsKey(player)) return false;
        long cooldownEnd = cooldowns.get(player).getEnd();
        return cooldownEnd > System.currentTimeMillis();
    }

    /**
     * Sets the player's Gun Cooldown. If the player already has a cooldown the cooldown will be overwritten. If the
     * <code>milliseconds</code> are <code>0</code>, the cooldown will be removed if there is one.
     *
     * @param player The Player who's cooldown will be set
     * @param milliseconds The Cooldown length in milliseconds.
     */
    public void setCooldown(Player player, int milliseconds) {
        if(milliseconds < 0) {
            throw new IllegalArgumentException("The cooldown time can't be negative.");
        } else if(milliseconds == 0) { //no cooldown
            cooldowns.remove(player); //removes the cooldown if it exists
            return;
        }
        Cooldown cooldown = new Cooldown(System.currentTimeMillis(), System.currentTimeMillis() + milliseconds);
        cooldowns.put(player, cooldown);
    }

    /**
     * Calculates the cooldown progress of a player
     *
     * @param player The Player who's cooldown progress will be calculated
     * @return a float between 0 and 1 wich is the progress of the cooldown. 1.0F if the player is not in cooldown
     * @throws  IllegalArgumentException if the player is null or not online
     */
    public float getCooldownProgress(Player player) {
        if(player == null) throw new IllegalArgumentException("The player can not be null");
        if(!player.isOnline()) throw new IllegalArgumentException("The player has to be online");
        Cooldown cooldown = cooldowns.get(player);
        if(cooldown != null) {
            return getCooldownProgress(cooldown);
        } else {
            return 1.0F;
        }
    }

    /**
     * Calculates the progress of a cooldown
     *
     * @param cooldown The Cooldown of wich the progress is calculated
     * @return a float between 0 and 1 wich is the progress of the cooldown
     * @throws IllegalArgumentException if the cooldown is null
     */
    public float getCooldownProgress(@NonNull Cooldown cooldown) {
        if(cooldown == null) throw new IllegalArgumentException("The cooldown can not be null");
        float totalTime = cooldown.getEnd() - cooldown.getStart();
        float progressedTime = cooldown.getEnd() - System.currentTimeMillis();
        if(progressedTime < 0) progressedTime = 0;
        return progressedTime / totalTime;
    }

    /**
     * Updates the durability of the gun items in a players inventory to represent the cooldown
     *
     * @param player The players who's items will be updated
     */
    public void updateGunItemsCooldown(@NonNull Player player) {
        if(player == null) throw new IllegalArgumentException("The player can not be null");
        if(!player.isOnline()) throw new IllegalArgumentException("The player has to be online");
        //get players progress
        float progress = getCooldownProgress(player);
        //iterate over all the items in the players inventory and set the item durability
        ItemStack[] items = player.getInventory().getContents();
        for (ItemStack item : items) {
            if (item != null && LaserCore.getLaserCore().isGun(item)) {
                short maxDurability = item.getType().getMaxDurability();
                short durability = (short) (progress * maxDurability);
                item.setDurability(durability);
            }
        }
    }

}

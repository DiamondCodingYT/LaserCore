package de.diamondCoding.laserCore;

import de.diamondCoding.laserCore.utils.Message;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class LaserCore extends JavaPlugin {

    public LaserCore laserCore;
    public LaserCore getLaserCore() {
        return laserCore;
    }

    @Override
    public void onEnable() {
        laserCore = this;
    }

    public ItemStack generateGunItem() {
        //generate gun itemstack
        ItemStack gun = new ItemStack(Material.IRON_BARDING);
        ItemMeta meta = gun.getItemMeta();
        meta.setDisplayName(Message.ITEM_GUN.getMessage());
        gun.setItemMeta(meta);
        //add gun tag and return
        return makeGun(gun);
    }

    public ItemStack makeGun(ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        //TODO: add a gun nbt tag
        return itemStack;
    }

}

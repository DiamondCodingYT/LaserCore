package de.diamondCoding.laserCore;

import de.diamondCoding.laserCore.commands.LaserCoreCommand;
import de.diamondCoding.laserCore.utils.Message;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class LaserCore extends JavaPlugin {

    @Getter
    static LaserCore laserCore;

    @Override
    public void onEnable() {
        laserCore = this;

        //Register the Commands
        getCommand("lasercore").setExecutor(new LaserCoreCommand());
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
        NBTTagCompound nbtTagCompound = nmsStack.getTag();
        if(nbtTagCompound==null) nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setBoolean("isLaserCoreGun", true);
        nmsStack.setTag(nbtTagCompound);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public boolean isGun(ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nbtTagCompound = nmsStack.getTag();
        return nbtTagCompound!=null && nbtTagCompound.hasKey("isLaserCoreGun") && nbtTagCompound.getBoolean("isLaserCoreGun");
    }

}

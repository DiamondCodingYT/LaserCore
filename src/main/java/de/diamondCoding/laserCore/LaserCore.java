package de.diamondCoding.laserCore;

import de.diamondCoding.laserCore.commands.LaserCoreCommand;
import de.diamondCoding.laserCore.commands.subcommands.ConfigCommand;
import de.diamondCoding.laserCore.handlers.ConfigurationHandler;
import de.diamondCoding.laserCore.handlers.CooldownHandler;
import de.diamondCoding.laserCore.listeners.InteractListener;
import de.diamondCoding.laserCore.commands.subcommands.GunCommand;
import de.diamondCoding.laserCore.commands.subcommands.IsGunCommand;
import de.diamondCoding.laserCore.commands.subcommands.MakeGunCommand;
import de.diamondCoding.laserCore.utils.Message;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class LaserCore extends JavaPlugin {

    @Getter
    private static LaserCore laserCore;
    @Getter
    private ConfigurationHandler configurationHandler;
    @Getter
    private CooldownHandler cooldownHandler;

    @Override
    public void onEnable() {
        laserCore = this;

        //Handlers
        configurationHandler = new ConfigurationHandler();
        if(!configurationHandler.isInitSuccess()) {
            System.out.println("ConfigurationHandler init failed. Disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        cooldownHandler = new CooldownHandler();

        //Register the Commands
        LaserCoreCommand executor = new LaserCoreCommand();
        executor.registerSubCommand("gun", new GunCommand());
        executor.registerSubCommand("isgun", new IsGunCommand());
        executor.registerSubCommand("makegun", new MakeGunCommand());
        executor.registerSubCommand("config", new ConfigCommand());
        getCommand("lasercore").setExecutor(executor);

        //Register the Listeners
        getServer().getPluginManager().registerEvents(new InteractListener(), this);

    }

    public ItemStack generateGunItem() {
        //generate gun itemstack
        ItemStack gun = new ItemStack(Material.IRON_HOE); //IRON_BARDING
        ItemMeta meta = gun.getItemMeta();
        meta.setDisplayName(Message.ITEM_GUN.getMessage());
        gun.setItemMeta(meta);
        //add gun tag and return
        return makeGun(gun);
    }

    public ItemStack makeGun(@NotNull ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nbtTagCompound = nmsStack.getTag();
        if(nbtTagCompound==null) nbtTagCompound = new NBTTagCompound();
        nbtTagCompound.setBoolean("isLaserCoreGun", true);
        nmsStack.setTag(nbtTagCompound);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public boolean isGun(ItemStack itemStack) {
        if(itemStack == null) return false;
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nbtTagCompound = nmsStack.getTag();
        return nbtTagCompound != null && nbtTagCompound.hasKey("isLaserCoreGun") && nbtTagCompound.getBoolean("isLaserCoreGun");
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericAttribute;

import Br.GenericAttribute.Datas.AttData;
import Br.GenericAttribute.Datas.AttType;
import Br.GenericAttribute.Datas.Data;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Bryan_lzh
 */
public class Main extends JavaPlugin {

    public static void LoadEntrySetting() {
        if (!Data.Plugin.getDataFolder().exists()) {
            Data.Plugin.saveDefaultConfig();
        }
        FileConfiguration config = Data.Plugin.getConfig();
        Data.Fix = Utils.AnalyticalItem(config.getString("Entry.Item.fix"));
        Data.Add = Utils.AnalyticalItem(config.getString("Entry.Item.add"));
    }

    @Override
    public void onEnable() {
        Data.Plugin = this;
        for (AttType at : AttType.values()) {
        }
        EntryManager.LoadAllEntrys();
        LoadEntrySetting();
        Bukkit.getPluginManager().registerEvents(new AttrListener(), this);

        (new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Utils.getOnlinePlayers()) {
                    AttData at = Utils.getAtt(p);
                    double h = p.getHealth();
                    h += at.getAttr(AttType.Recover);
                    if (h >= p.getMaxHealth()) {
                        h = p.getMaxHealth();
                    }
                    p.setHealth((int) h);

                }
            }

        }).runTaskTimer(this, 20L, 20L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equals("GenericAttribute") && (sender instanceof Player)) {
            Player player = (Player) sender;
            player.openInventory(Utils.getAttachInv(player));
            return true;
        }
        if (cmd.getName().equals("GenericAttributeGet") && (sender instanceof Player) && sender.isOp()) {
            Player p = (Player) sender;
            for (ItemStack is : EntryManager.getAllItems()) {
                p.getWorld().dropItem(p.getLocation(), is);
            }
            p.getWorld().dropItem(p.getLocation(), Data.Add.clone());
            p.getWorld().dropItem(p.getLocation(), Data.Fix.clone());
            return true;
        }
        return super.onCommand(sender, cmd, label, args);
    }

}

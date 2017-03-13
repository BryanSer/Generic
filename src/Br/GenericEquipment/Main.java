/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericEquipment;

import Br.GenericEquipment.Effects.EffectListener;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Bryan_lzh
 */
public class Main extends JavaPlugin {

    public static Main Main;

    @Override
    public void onEnable() {
        Main = this;
        if (!this.getDataFolder().exists()) {
            this.saveDefaultConfig();
            File d = this.getDataFolder();
            d = new File(d, "Items");
            d.mkdirs();
        }
        EffectManager.LoadAllEffects();
        EffectManager.LoadAllItems();
        Bukkit.getPluginManager().registerEvents(new EffectListener(), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("GenericEquipment") && sender.isOp() && args.length >= 1 &&(sender instanceof Player)) {
            Player p = (Player) sender;
            Item i = EffectManager.Items.get(args[0]);
            p.getInventory().addItem(i.getItemStack());
        }
        return super.onCommand(sender, command, label, args); //To change body of generated methods, choose Tools | Templates.
    }

}

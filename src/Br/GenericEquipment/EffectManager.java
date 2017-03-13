/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericEquipment;

import Br.GenericAttribute.Datas.AttData;
import Br.GenericEquipment.Effects.Effect;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Bryan_lzh
 */
public class EffectManager {

    public static Map<String, EffectData> Effects = new HashMap<>();
    public static Map<String, Item> Items = new HashMap<>();
    public static Map<Integer, Item> FindMap = new HashMap<>();

    public static void LoadAllItems() {
        File fold = Main.Main.getDataFolder();
        File d = new File(fold, "Items");
        for (File f : d.listFiles()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
            new Item(config);
            try {
                config.save(f);
            } catch (IOException ex) {
                Logger.getLogger(EffectManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static Map<String, Long> CacheTime = new HashMap<>();
    private static Map<String, List<Effect>> PlayerCache = new HashMap<>();

    public static List<Effect> getEffects(Player p) {
        if (CacheTime.containsKey(p.getName()) && CacheTime.get(p.getName()) + 2000 > System.currentTimeMillis()) {
            return PlayerCache.get(p.getName());
        }
        Map<String, Integer> Es = new HashMap<>();
        for (ItemStack is : p.getInventory().getArmorContents()) {
            if (is == null) {
                continue;
            }
            Item i = FindItem(is);
            if (Es.containsKey(i.Code)) {
                Es.put(i.Code, Es.get(i.Code) + 1);
            }
        }
        List<Effect> r = new ArrayList<>();
        for (Map.Entry<String, Integer> e : Es.entrySet()) {
            String key = e.getKey();
            Integer value = e.getValue();
            EffectData effs = Effects.get(key);
            r.addAll(effs.getEffect(value));
        }
        CacheTime.put(p.getName(), System.currentTimeMillis());
        PlayerCache.put(p.getName(), r);
        return r;
    }

    public static void LoadAllEffects() {
        FileConfiguration config = Main.Main.getConfig();
        for (String key : config.getKeys(false)) {
            EffectData ed = new EffectData(config, key);
            Effects.put(ed.getName(), ed);
        }
    }

    public static Item FindItem(ItemStack is) {
        if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
            ItemMeta im = is.getItemMeta();
            String s = im.getLore().get(im.getLore().size() - 1);
            s = s.replaceAll("§", "");
            try {
                int v = Integer.parseInt(s);
                if (FindMap.containsKey(v)) {
                    return FindMap.get(v);
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static EffectData getEffectData(String s) {
        return Effects.get(s);
    }
}

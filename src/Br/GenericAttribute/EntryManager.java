/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericAttribute;

import Br.GenericAttribute.Datas.Data;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Bryan_lzh
 */
public class EntryManager {

    private static Map<String, EntryManager> EntryManagers = new HashMap<>();

    public static int getSize() {
        return EntryManagers.size();
    }

    public static List<ItemStack> getAllItems() {
        List<ItemStack> i = new ArrayList<>();
        for (EntryManager e : EntryManagers.values()) {
            i.add(e.item.clone());
        }
        return i;
    }

    public static EntryManager FixManager(ItemStack is) {
        for (Map.Entry<String, EntryManager> e : EntryManagers.entrySet()) {
            if (is.isSimilar(e.getValue().item)) {
                return e.getValue();
            }
        }
        return null;
    }

    private ItemStack item;
    private Map<String, Entry> Entrys = new LinkedHashMap<>();
    private Map<Integer, Integer> Chance = new HashMap<>();
    private int Max;

    private Random ran = new Random();

    public Entry[] getRandomEntrys() {
        List<Entry> en = new ArrayList<>();
        int c = ran.nextInt(Max);
        int r = 0;
        for (Map.Entry<Integer, Integer> e : Chance.entrySet()) {
            if (c <= e.getValue()) {
                r = e.getKey();
                break;
            } else {
                c -= e.getValue();
            }
        }
        int rr = r;
        for (int i = 0; i < r; i++) {
            Entry add = null;
            int maxLoop = 0;
            do {
                add = this.getRandomEntry();
                maxLoop++;
                if (maxLoop >= 20) {
                    add = null;
                    rr--;
                    break;
                }
            } while (en.contains(add));
            if (add != null) {
                en.add(add);
            }
        }
        return en.toArray(new Entry[rr]);
    }

    public Entry getRandomEntry() {
        int index = ran.nextInt(Entrys.size());
        Entry last = null;
        for (Entry e : Entrys.values()) {
            index--;
            last = e;
            if (index <= 0) {
                return e;
            }
        }
        return last;
    }

    public static void LoadAllEntrys() {
        if (!Data.Plugin.getDataFolder().exists()) {
            Data.Plugin.saveDefaultConfig();
        }
        FileConfiguration config = Data.Plugin.getConfig();
        ConfigurationSection cs = config.getConfigurationSection("Entry.EntryPool");
        for (String key : cs.getKeys(false)) {
            ItemStack is = Utils.AnalyticalItem(cs.getString(key + ".Item"));
            String ename = cs.getString(key + ".Entry");
            if (!Data.Plugin.getDataFolder().exists()) {
                Data.Plugin.saveDefaultConfig();
            }
            File f = Data.Plugin.getDataFolder();
            f = new File(f, ename);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                }
                continue;
            }
            YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
            EntryManager em = new EntryManager();
            em.item = is;
            for (String ss : c.getKeys(false)) {
                try {
                    Entry e = new Entry(c.getString(ss + ".DisplayName"), c.getString(ss + ".Attrs"));
                    if (Data.Entrys.containsKey(e.getDisplayName())) {
                        e = Data.Entrys.get(e.getDisplayName());
                    }
                    Data.Entrys.put(e.getDisplayName(), e);
                    em.Entrys.put(e.getDisplayName(), e);
                } catch (Exception e) {
                }
            }
            em.Max = 0;
            for (String s : cs.getStringList(key + ".Chance")) {
                String sv[] = s.split("\\|");
                int chance = Integer.parseInt(sv[1]);
                em.Max += chance;
                em.Chance.put(Integer.parseInt(sv[0]), chance);
            }
            EntryManager.EntryManagers.put(key, em);
        }
    }
}

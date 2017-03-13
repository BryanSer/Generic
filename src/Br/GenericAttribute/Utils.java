/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericAttribute;

import Br.GenericAttribute.Datas.AttData;
import Br.GenericAttribute.Datas.Data;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Server;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.Dye;
import org.bukkit.material.Wool;

/**
 *
 * @author Bryan_lzh
 */
public class Utils {

    public static String EntryAttrCode = "§r§6§e§a§2§r§b     ";//词条属性前缀
    public static String FixedEntryCode = "§r§f§e§a§2§c§r§6§l【固定】";//固定属性前缀
    public static String InvPrefixCode = "§r§b§a装备清洗";

    public static Inventory getAttachInv(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, InvPrefixCode);
        ItemStack 玻璃板 = new ItemStack(Material.STAINED_GLASS_PANE);
        玻璃板.setAmount(1);
        玻璃板.setDurability((short) 4);
        ItemMeta im = 玻璃板.getItemMeta();
        im.setDisplayName(" ");
        玻璃板.setItemMeta(im);
        inv.setItem(0, 玻璃板.clone());
        inv.setItem(2, 玻璃板.clone());
        inv.setItem(9, 玻璃板.clone());
        inv.setItem(11, 玻璃板.clone());
        inv.setItem(18, 玻璃板.clone());
        inv.setItem(20, 玻璃板.clone());
        玻璃板.setDurability((short) 11);
        inv.setItem(3, 玻璃板.clone());
        inv.setItem(4, 玻璃板.clone());
        inv.setItem(5, 玻璃板.clone());
        inv.setItem(12, 玻璃板.clone());
        inv.setItem(14, 玻璃板.clone());
        inv.setItem(21, 玻璃板.clone());
        inv.setItem(22, 玻璃板.clone());
        inv.setItem(23, 玻璃板.clone());
        玻璃板.setDurability((short) 5);
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&l在这里拿走清洗完成的装备"));
        玻璃板.setItemMeta(im);
        inv.setItem(6, 玻璃板.clone());
        inv.setItem(7, 玻璃板.clone());
        inv.setItem(8, 玻璃板.clone());
        inv.setItem(15, 玻璃板.clone());
        inv.setItem(17, 玻璃板.clone());
        inv.setItem(24, 玻璃板.clone());
        inv.setItem(25, 玻璃板.clone());
        inv.setItem(26, 玻璃板.clone());
        玻璃板.setDurability((short) 14);
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&l上方放入装备"));
        List<String> tl = new ArrayList<>();
        tl.add(ChatColor.translateAlternateColorCodes('&', "&a&l下方放入卷轴"));
        im.setLore(tl);
        玻璃板.setItemMeta(im);
        inv.setItem(10, 玻璃板.clone());
        ItemStack 玻璃瓶 = new ItemStack(Material.DRAGONS_BREATH);
        玻璃瓶.setAmount(1);
        inv.setItem(13, 玻璃瓶);
        return inv;
    }

    public static ItemStack AnalyticalItem(String s) {
        ItemStack item;
        try {
            item = new ItemStack(Material.getMaterial(Integer.valueOf(s.split(" ")[0])));
        } catch (NumberFormatException e) {
            item = new ItemStack(Material.getMaterial(s.split(" ")[0]));
        }
        int i = 0;
        for (String data : s.split(" ")) {
            if (i == 0) {
                i++;
                continue;
            }
            if (i == 1) {
                try {
                    item.setAmount(Integer.valueOf(data));
                } catch (NumberFormatException e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l在读取物品: " + s + " 时出现错误"));
                }
                i++;
                continue;
            }
            if (i == 2) {
                try {
                    item.setDurability(Short.valueOf(data));
                } catch (NumberFormatException e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l在读取物品: " + s + " 时出现错误"));
                }
                i++;
                continue;
            }
            if (data.toLowerCase().contains("name:")) {
                data = data.substring(data.indexOf(":") + 1);
                data = ChatColor.translateAlternateColorCodes('&', data);
                data = data.replaceAll("_", " ");
                ItemMeta im = item.getItemMeta();
                im.setDisplayName(data);
                item.setItemMeta(im);
                continue;
            }
            if (data.toLowerCase().contains("lore:")) {
                data = data.substring(data.indexOf(":") + 1);
                String lores[] = data.split("\\|");
                int o = 0;
                List<String> LoreList = new ArrayList<>();
                for (String os : lores) {
                    lores[o] = lores[o].replaceAll("_", " ");
                    lores[o] = ChatColor.translateAlternateColorCodes('&', lores[o]);
                    o++;
                }
                LoreList.addAll(Arrays.asList(lores));
                ItemMeta im = item.getItemMeta();
                im.setLore(LoreList);
                item.setItemMeta(im);
                continue;
            }
            if (data.toLowerCase().contains("color:")) {
                data = data.substring(data.indexOf(":") + 1);
                if (item.getData() instanceof Dye) {
                    Dye d = (Dye) item.getData();
                    d.setColor(DyeColor.valueOf(data));
                    item.setData(d);
                    continue;
                }
                if (item.getData() instanceof Wool) {
                    Wool w = (Wool) item.getData();
                    w.setColor(DyeColor.valueOf(data));
                    item.setData(w);
                    continue;
                }
                if (item.getItemMeta() instanceof LeatherArmorMeta) {
                    LeatherArmorMeta lam = (LeatherArmorMeta) item.getItemMeta();
                    lam.setColor(Color.fromRGB(Integer.valueOf(data)));
                    item.setItemMeta(lam);
                    continue;
                }
            }
        }
        Bukkit.getConsoleSender().sendMessage(item.toString());
        return item;
    }

    public static ItemStack setItemStackEntrys(ItemStack is, Entry... ees) {
        is = is.clone();
        ItemMeta im = is.getItemMeta();
        List<String> lore = im.getLore();
        if (lore == null) {
            lore = new LinkedList<>();
        }
        List<String> newlore = new LinkedList<>(lore);
        boolean fix = false;
        for (int i = 0; i < lore.size(); i++) {
            String s = lore.get(i);
            if (s.contains(FixedEntryCode)) {
                fix = true;
                continue;
            }
            for (String es : Data.Entrys.keySet()) {
                if (s.contains(es)) {
                    newlore.remove(s);
                    break;
                }
            }
        }//移除完成
        for (Entry e : ees) {
            if (fix) {
                fix = false;
                continue;
            }
            newlore.add(e.getDisplayName() + "§b-" + e.getType().getDisplayName() + ": "
                    + ((e.getVaule() >= 0) ? "+" : "-") + e.getVaule() + (e.getType().isPer() ? "%" : ""));
        }
        im.setLore(newlore);
        is.setItemMeta(im);
        return is;
    }

    public static Collection<Player> getOnlinePlayers() {
        try {
            Method onlinePlayerMethod = Server.class.getMethod("getOnlinePlayers");
            if (onlinePlayerMethod.getReturnType().equals(Collection.class)) {
                return (Collection<Player>) onlinePlayerMethod.invoke(Bukkit.getServer());
            } else {
                return Arrays.asList((Player[]) onlinePlayerMethod.invoke(Bukkit.getServer()));
            }
        } catch (Exception ex) {
        }
        return new ArrayList<>();
    }

    public static List<String> setLeftTimes(List<String> lore, int t) {
        lore = new LinkedList<>(lore);
        boolean replaced = false;
        for (int i = 0; i < lore.size(); i++) {
            String s = lore.get(i);
            if (s.contains("剩余清洗次数:") || s.contains("§c无法继续清洗")) {
                if (t != 0) {
                    lore.set(i, "§6剩余清洗次数:" + t);
                    replaced = true;
                    break;
                } else {
                    lore.set(i, "§c无法继续清洗");
                    replaced = true;
                    break;
                }
            }
        }
        if (!replaced) {
            lore.add("§6剩余清洗次数:" + t);
        }
        return lore;
    }

    public static int getLeftTimes(List<String> lore) {
        if (lore == null) {
            lore = new LinkedList<>();
        }
        int left = 5;
        for (String s : lore) {
            if (s.contains("剩余清洗次数:")) {
                s = s.split("次数:")[1];
                left = Integer.parseInt(s);
                break;
            }
            if (s.contains("无法继续清洗")) {
                left = 0;
                break;
            }
        }
        return left;
    }

    public static AttData getAtt(Player p) {
        if (Data.CacheTime.containsKey(p.getName()) && Data.CacheTime.get(p.getName()) >= System.currentTimeMillis() - 1000) {
            return Data.PlayerCache.get(p.getName());
        }
        AttData at = new AttData(p);
        Data.CacheTime.put(p.getName(), System.currentTimeMillis());
        Data.PlayerCache.put(p.getName(), at);
        return at;
    }

    @Deprecated
    public static void LoadEntrys() {/*
        if (!Data.Plugin.getDataFolder().exists()) {
            Data.Plugin.saveDefaultConfig();
        }
        File f = Data.Plugin.getDataFolder();
        f = new File(f, "entry.yml");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        for (String key : config.getKeys(false)) {
            try {
                Entry e = new Entry(config.getString(key + ".DisplayName"), config.getStringList(key + ".Attrs"));
                Data.Entrys.put(e.getDisplayName(), e);
            } catch (Exception e) {
            }
        }*/
    }
}

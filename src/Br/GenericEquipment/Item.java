/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericEquipment;

import Br.GenericAttribute.Datas.AttType;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Bryan_lzh
 */
public final class Item {

    String Name;
    int ID;
    short Durability;
    String DisplayName;
    List<String> lore;
    String Level;
    Map<AttType, Double> attrs = new EnumMap<>(AttType.class);
    String Code;

    ItemStack item;

    DecimalFormat DF = new DecimalFormat("#.#");

    public Item(FileConfiguration config) {
        this.Name = config.getString("Display.Name");
        this.ID = config.getInt("Display.ID");
        this.Durability = (short) config.getInt("Display.Durability");
        this.DisplayName = ChatColor.translateAlternateColorCodes('&', config.getString("Info.Display"));
        this.lore = this.getTopLore(config.getString("Info.TopLore"));
        this.Level = ChatColor.translateAlternateColorCodes('&', config.getString("Info.Level"));
        for (String s : config.getStringList("Attr")) {
            for (AttType at : AttType.values()) {
                if (s.startsWith(at.name())) {
                    s = s.split(":")[1];
                    if (s.contains("%")) {
                        s = s.replaceAll("%", "");
                    }
                    double d = Double.parseDouble(s);
                    this.attrs.put(at, d);
                }
            }
        }
        this.Code = ChatColor.translateAlternateColorCodes('&', config.getString("Set.Code"));
        this.item = this.ali(config);
        EffectManager.FindMap.put(config.getInt("Set.FindCode"), this);
        EffectManager.Items.put(this.Name, this);
    }

    public ItemStack getItemStack() {
        return this.item;
    }

    private ItemStack ali(FileConfiguration config) {
        ItemStack is = new ItemStack(Material.getMaterial(ID));
        is.setDurability(Durability);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(this.DisplayName);
        List<String> lore = new ArrayList<>(this.lore);
        lore.add("§b------------§6星级§b------------");
        lore.add(this.Level);
        lore.add("§b------------§a属性§b------------");
        for (Map.Entry<AttType, Double> e : attrs.entrySet()) {
            lore.add("§l" + e.getKey().getDisplayName() + ": +" + DF.format(e.getValue()));
        }
        lore.add("§b------------§c套装§b------------");
        EffectData ed = EffectManager.getEffectData(this.Code);
        lore.addAll(ed.getLore());
        String toFindCode;
        if (config.contains("Set.FindCode")) {
            toFindCode = this.toFindCode(config.getInt("Set.FindCode"));
        } else {
            toFindCode = this.toFindCode();
            config.set("Set.FindCode", toFindCode);
        }
        lore.add(toFindCode);
        return is;
    }

    public List<String> getTopLore(String lore) {
        List<String> LoreList = new ArrayList<>();
        if (lore.contains("|")) {
            String[] lores = lore.split("\\|");
            for (String os : lores) {
                os = os.replaceAll("_", " ");
                os = ChatColor.translateAlternateColorCodes('&', os);
                LoreList.add(os);
            }
        } else {
            lore = lore.replaceAll("_", " ");
            lore = ChatColor.translateAlternateColorCodes('&', lore);
            LoreList.add(lore);
        }
        return LoreList;
    }

    public String toFindCode() {
        String s = this.hashCode() + "";
        String v = "";
        for (char c : s.toCharArray()) {
            v += "§" + c;
        }
        return s;
    }

    public String toFindCode(int a) {
        String s = a + "";
        String v = "";
        for (char c : s.toCharArray()) {
            v += "§" + c;
        }
        return s;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.Name);
        hash = 83 * hash + this.ID;
        hash = 83 * hash + this.Durability;
        hash = 83 * hash + Objects.hashCode(this.attrs);
        hash = 83 * hash + Objects.hashCode(this.Code);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (this.Durability != other.Durability) {
            return false;
        }
        if (!Objects.equals(this.Name, other.Name)) {
            return false;
        }
        if (!Objects.equals(this.Code, other.Code)) {
            return false;
        }
        return Objects.equals(this.attrs, other.attrs);
    }

}

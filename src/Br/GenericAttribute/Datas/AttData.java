package Br.GenericAttribute.Datas;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Bryan_lzh
 */
public final class AttData {

    private Map<AttType, Double> Attrs = new EnumMap<>(AttType.class);

    public void Map() {
        for (AttType at : AttType.values()) {
            if (!this.Attrs.containsKey(at)) {
                this.Attrs.put(at, 0d);
            }
        }
    }

    public AttData() {
        this.Map();
    }

    public void setMaxHealth(Player p) {
        double max = 20d + this.getAttr(AttType.MaxHealth);
        p.setMaxHealth((int) max);
        p.setHealthScale(40d);
        p.setHealthScaled(true);
        p.setWalkSpeed(0.2f);
        if (this.getAttr(AttType.MovingSpeed) != 0d) {
            p.setWalkSpeed((float) (0.2f + 0.2f * this.getAttr(AttType.MovingSpeed) * 0.01f));
        }
    }

    public AttData(Player p) {
        for (ItemStack is : p.getInventory().getArmorContents()) {
            if (is != null) {
                this.Add(is);
            }
        }
        if (p.getItemInHand() != null) {
            this.Add(p.getItemInHand());
        }
        if (p.getInventory().getItemInOffHand() != null) {
            ItemStack is = p.getInventory().getItemInOffHand();
            if (is.getType() == Material.SHIELD) {
                this.Add(is);
            }
        }
        this.Map();
        this.setMaxHealth(p);
    }

    public AttData(ItemStack is) {
        this.Add(is);
        this.Map();
    }

    public void add(AttType at, double d) {
        if (this.Attrs.containsKey(at)) {
            d += this.Attrs.get(at);
        }
        this.Attrs.put(at, d);
    }

    public void Add(ItemStack is) {
        boolean debug = false;
        if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
            List<String> lore = is.getItemMeta().getLore();
            A:
            for (String s : lore) {
                for (AttType at : AttType.values()) {
                    try {
                        if (s.contains(at.getDisplayName())) {
                            String v = s.split(": ")[1];
                            if (v.contains("%")) {
                                v = v.replaceAll("%", "");
                            }
                            double d = Double.parseDouble(v);
                            if (debug) {
                                Bukkit.broadcastMessage(at.getDisplayName() + ":" + v + " = " + d);
                            }
                            this.add(at, d);
                            continue A;
                        }
                    } catch (Exception e) {
                        continue A;
                    }
                }
            }
        }
    }

    public void Add(Map<AttType, Double> map) {
        for (Map.Entry<AttType, Double> e : map.entrySet()) {
            AttType at = e.getKey();
            Double d = e.getValue();
            d += this.Attrs.get(at);
            this.Attrs.put(at, d);
        }
    }

    public AttData Add(AttData ad) {
        for (Map.Entry<AttType, Double> E : ad.Attrs.entrySet()) {
            double d = 0d;
            if (this.Attrs.containsKey(E.getKey())) {
                d = Attrs.get(E.getKey());
            }
            d += E.getValue();
        }
        return this;
    }

    public double getAttr(AttType at) {
        return this.Attrs.get(at);
    }
    static Random ran = new Random();

    public boolean Chance(AttType at) {
        if (!at.isPer()) {
            return false;
        }
        return ran.nextInt(99) + 1 <= this.getAttr(at);
    }

}

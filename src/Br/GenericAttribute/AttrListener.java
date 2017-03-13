/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericAttribute;

import Br.GenericAttribute.Datas.Data;
import Br.GenericAttribute.Datas.AttData;
import Br.GenericAttribute.Datas.AttType;
import Br.GenericAttribute.Datas.AttrEndEvent;
import Br.GenericAttribute.Datas.AttrEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Bryan_lzh
 */
public class AttrListener implements Listener {

    @EventHandler
    public void onInvEvent(InventoryEvent evt) {
        for (HumanEntity e : evt.getViewers()) {
            if (e instanceof Player) {
            }
        }
    }

    @EventHandler
    public void onSwitch(PlayerSwapHandItemsEvent evt) {
        if (evt.getOffHandItem().getType() == Material.SHIELD) {
            evt.getPlayer().sendMessage("§d§l持盾属性已生效~");
        }
    }

    @EventHandler
    public void onClick2(InventoryClickEvent evt) {
        HumanEntity p = evt.getWhoClicked();
        if (p.getInventory().getItemInOffHand() == evt.getCurrentItem()) {
            if (evt.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
                if (evt.getCursor().getType() == Material.SHIELD) {
                    p.sendMessage("§d§l持盾属性已生效~");
                    return;
                }
            }
            if (evt.getAction() == InventoryAction.DROP_ONE_CURSOR) {
                if (evt.getCursor().getType() == Material.SHIELD) {
                    p.sendMessage("§d§l持盾属性已生效~");
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onChange(PlayerItemHeldEvent evt) {
        Utils.getAtt(evt.getPlayer());
    }

    @EventHandler
    public void onClose(InventoryCloseEvent evt) {
        if (evt.getView().getTopInventory() == null) {
            return;
        }
        if (evt.getView().getTopInventory().getTitle() == null) {
            return;
        }
        if (!evt.getView().getTopInventory().getTitle().equals(Utils.InvPrefixCode)) {
            return;
        }
        Inventory inv = evt.getView().getTopInventory();
        for (ItemStack is : inv) {
            if (is == null) {
                continue;
            }
            if (is.getType() == Material.STAINED_GLASS_PANE || is.getType() == Material.DRAGONS_BREATH) {
                continue;
            }
            evt.getPlayer().getWorld().dropItemNaturally(evt.getPlayer().getLocation(), is);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent evt) {
        if (evt.getClickedInventory() == null) {
            return;
        }
        if (evt.getClickedInventory().getTitle() == null) {
            return;
        }
        if (!evt.getClickedInventory().getTitle().equals(Utils.InvPrefixCode)) {
            return;
        }
        if (evt.getSlot() == 16) {
            if (evt.getAction().toString().contains("PICKUP")) {
                return;
            }
            evt.setCancelled(true);
            return;
        }
        if (evt.getCurrentItem() == null || evt.getCurrentItem().getType() == Material.AIR || evt.getCurrentItem().getAmount() == 0) {
            return;
        }
        if (evt.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
            evt.setCancelled(true);
            return;
        }
        if (evt.getCurrentItem().getType() == Material.DRAGONS_BREATH) {
            evt.setCancelled(true);
            ItemStack item = evt.getWhoClicked().getOpenInventory().getTopInventory().getItem(1);
            ItemStack so = evt.getWhoClicked().getOpenInventory().getTopInventory().getItem(19);
            if (isEmpty(so)) {
                return;
            }
            if (isEmpty(item)) {
                return;
            }
            if (so.isSimilar(Data.Fix)) {
                ItemStack result = item.clone();
                ItemMeta im = result.getItemMeta();
                List<String> lore = im.getLore();
                if (lore == null) {
                    lore = new LinkedList<>();
                }
                Out:
                for (int i = 0; i < lore.size(); i++) {
                    String s = lore.get(i);
                    for (String key : Data.Entrys.keySet()) {
                        if (s.contains(key)) {
                            lore.set(i, Utils.FixedEntryCode + s);
                            break Out;
                        }
                    }
                }
                im.setLore(lore);
                result.setItemMeta(im);
                Inventory inv = evt.getWhoClicked().getOpenInventory().getTopInventory();
                inv.setItem(1, null);
                inv.setItem(16, result);
                if (so.getAmount() == 1) {
                    inv.setItem(19, null);
                } else {
                    so = so.clone();
                    so.setAmount(so.getAmount() - 1);
                    inv.setItem(19, so);
                }
                return;
            }
            if (so.isSimilar(Data.Add)) {
                ItemMeta im = item.getItemMeta();
                List<String> lore = im.getLore();
                if (lore == null) {
                    lore = new LinkedList<>();
                }
                lore = Utils.setLeftTimes(lore, Utils.getLeftTimes(lore) + 3);
                im.setLore(lore);
                item.setItemMeta(im);
                ItemStack result = item.clone();
                Inventory inv = evt.getWhoClicked().getOpenInventory().getTopInventory();
                inv.setItem(1, null);
                inv.setItem(16, result);
                if (so.getAmount() == 1) {
                    inv.setItem(19, null);
                } else {
                    so = so.clone();
                    so.setAmount(so.getAmount() - 1);
                    inv.setItem(19, so);
                }
                return;
            }
            EntryManager em = EntryManager.FixManager(so);
            if (em != null) {
                int leftTimes = Utils.getLeftTimes(item.getItemMeta().getLore());
                if (leftTimes <= 0) {
                    return;
                }
                ItemStack result = Utils.setItemStackEntrys(item, em.getRandomEntrys());
                ItemMeta im = result.getItemMeta();
                List<String> lore = im.getLore();
                if (lore == null) {
                    lore = new LinkedList<>();
                }
                lore = Utils.setLeftTimes(lore, leftTimes - 1);
                im.setLore(lore);
                result.setItemMeta(im);
                Inventory inv = evt.getWhoClicked().getOpenInventory().getTopInventory();
                inv.setItem(1, null);
                inv.setItem(16, result);
                if (so.getAmount() == 1) {
                    inv.setItem(19, null);
                } else {
                    so = so.clone();
                    so.setAmount(so.getAmount() - 1);
                    inv.setItem(19, so);
                }
                return;
            }
            /*
            if (so.isSimilar(Entry.Wash)) {
                int leftTimes = Utils.getLeftTimes(item.getItemMeta().getLore());
                if (leftTimes <= 0) {
                    return;
                }
                int es = Entry.getRandomEntrysNumber();
                List<Entry> entrys = new ArrayList<>();
                for (int i = 0; i < es; i++) {
                    Entry en;
                    do {
                        en = Utils.getRandomEntry();
                    } while (entrys.contains(en));
                    entrys.add(en);
                }
                ItemStack result = Utils.setItemStackEntrys(item, entrys.toArray(new Entry[es]));
                ItemMeta im = result.getItemMeta();
                List<String> lore = im.getLore();
                if (lore == null) {
                    lore = new LinkedList<>();
                }
                lore = Utils.setLeftTimes(lore, leftTimes - 1);
                im.setLore(lore);
                result.setItemMeta(im);
                Inventory inv = evt.getWhoClicked().getOpenInventory().getTopInventory();
                inv.setItem(1, null);
                inv.setItem(16, result);
                if (so.getAmount() == 1) {
                    inv.setItem(19, null);
                } else {
                    so = so.clone();
                    so.setAmount(so.getAmount() - 1);
                    inv.setItem(19, so);
                }
                return;
            }*/
        }
    }

    public boolean isEmpty(ItemStack is) {
        return is == null || is.getType() == Material.AIR || is.getAmount() == 0;
    }

    public DecimalFormat DF = new DecimalFormat("#");

    Map<String, Long> CD = new HashMap<>();

    @EventHandler
    public void onAtk(EntityDamageByEntityEvent evt) {
        boolean debug = true;
        AttData ea = null;
        AttData da = null;
        Entity damager = evt.getDamager();
        if (evt.getEntity() instanceof Player) {
            Player p = (Player) evt.getEntity();
            ea = Utils.getAtt(p);
        }
        if (damager instanceof Player) {
            Player p = (Player) evt.getDamager();
            da = Utils.getAtt(p);
            damager = p;
        }
        if (evt.getDamager() instanceof Projectile) {
            Projectile pro = (Projectile) evt.getDamager();
            if (pro.getShooter() instanceof Player) {
                Player p = (Player) pro.getShooter();
                da = Utils.getAtt(p);
                damager = p;
            }
        }
        if (damager instanceof Player) {
            Player p = (Player) damager;
            if (this.CD.containsKey(p.getName()) && this.CD.get(p.getName()) + 630 > System.currentTimeMillis()) {
                this.CD.put(p.getName(), System.currentTimeMillis());
                return;
            }
            this.CD.put(p.getName(), System.currentTimeMillis());
        }
        if (ea == null) {
            ea = new AttData();
        }
        if (da == null) {
            da = new AttData();
        }
        if (damager instanceof Player) {
            Player p = (Player) damager;
            AttrEvent ae = new AttrEvent(p, da);
            Bukkit.getPluginManager().callEvent(ae);
            da = ae.getDamagerAttData();
        }

        double dam = evt.getDamage();//受到伤害
        if (debug) {
            damager.sendMessage("基础伤害:" + dam);
        }
        dam += da.getAttr(AttType.Atk);
        if (debug) {
            damager.sendMessage("+=Atk" + dam);
        }
        //dam *= (double) (1d + (da.getAttr(AttType.AtkInc) * 0.01d));
        dam = dam + (evt.getDamage() * ((da.getAttr(AttType.AtkInc) * 0.01d)));
        if (debug) {
            damager.sendMessage("+=AtkInc" + dam);
        }
        dam = dam * ((double) (1d - ((ea.getAttr(AttType.DefInc) * 0.01d))));
        if (debug) {
            damager.sendMessage("-=DefInc" + dam);
        }
        dam -= ea.getAttr(AttType.Def);
        if (debug) {
            damager.sendMessage("-=Def" + dam);
        }
        if (da.Chance(AttType.Crit)) {
            double d = evt.getDamage() * da.getAttr(AttType.CritDam) * 0.01d;
            dam += d;
            evt.getEntity().sendMessage("§c你受到了一次暴击");
            damager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c会心一击！造成&e&l" + DF.format(dam)
                    + "&c伤害"));
        }
        if (da.Chance(AttType.VampireCha) && da.getAttr(AttType.Vampire) > 0d) {
            damager.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c触发嗜血！汲取&e&l" + DF.format(da.getAttr(AttType.Vampire))
                    + "&c生命值！"));
            if (damager instanceof LivingEntity) {
                LivingEntity le = (LivingEntity) damager;
                double h = le.getHealth();
                h += da.getAttr(AttType.Vampire);
                if (h >= le.getMaxHealth()) {
                    h = le.getMaxHealth();
                }
                le.setHealth(h);
            }
        }
        if (evt.getEntity() instanceof Player) {
            Player p = (Player) evt.getEntity();
            AttrEndEvent aee = new AttrEndEvent(ea, da, evt, p, dam);
            Bukkit.getPluginManager().callEvent(aee);
            if (aee.isCancelled()) {
                evt.setCancelled(true);
                return;
            }
        }
        evt.setDamage(dam);
    }
}

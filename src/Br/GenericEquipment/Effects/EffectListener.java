/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericEquipment.Effects;

import Br.GenericAttribute.Datas.AttrEndEvent;
import Br.GenericAttribute.Datas.AttrEvent;
import Br.GenericAttribute.Datas.BlockEvent;
import Br.GenericAttribute.Datas.CritEvent;
import Br.GenericAttribute.Datas.VampireEvent;
import Br.GenericEquipment.EffectManager;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 *
 * @author Bryan_lzh
 */
public class EffectListener implements Listener {

    @EventHandler
    public void onAttr(AttrEvent evt) {
        List<Effect> effs = EffectManager.getEffects(evt.getPlayer());
        for (Effect e : effs) {
            e.onEvent(evt);
        }
    }

    @EventHandler
    public void onAttrEnd(AttrEndEvent evt) {
        List<Effect> effs = EffectManager.getEffects(evt.getEntity());
        for (Effect e : effs) {
            e.onEvent(evt);
        }
    }

    @EventHandler
    public void onVam(VampireEvent evt) {
        List<Effect> effs = EffectManager.getEffects(evt.getPlayer());
        for (Effect e : effs) {
            e.onEvent(evt);
        }
    }

    @EventHandler
    public void onBlock(BlockEvent evt) {
        List<Effect> effs = EffectManager.getEffects(evt.getPlayer());
        for (Effect e : effs) {
            e.onEvent(evt);
        }
    }

    @EventHandler
    public void onCrit(CritEvent evt) {
        List<Effect> effs = EffectManager.getEffects(evt.getPlayer());
        for (Effect e : effs) {
            e.onEvent(evt);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent evt) {
        List<Effect> effs = EffectManager.getEffects(evt.getPlayer());
        for (Effect e : effs) {
            e.onEvent(evt);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent evt) {
        if (evt.getEntity() instanceof Player) {
            Player p = (Player) evt.getEntity();
            List<Effect> effs = EffectManager.getEffects(p);
            for (Effect e : effs) {
                e.onEvent(evt);
            }
        }
    }
}

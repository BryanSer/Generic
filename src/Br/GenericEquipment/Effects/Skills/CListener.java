/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericEquipment.Effects.Skills;

import Br.GenericAttribute.Datas.AttType;
import Br.GenericAttribute.Datas.AttrEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 *
 * @author Bryan_lzh
 */
public class CListener implements Listener {

    @EventHandler
    public void onAttr(AttrEvent evt) {
        if (Counterattack.DamInc.containsKey(evt.getPlayer().getName())) {
            evt.getDamagerAttData().add(AttType.AtkInc, Counterattack.DamInc.get(evt.getPlayer().getName()));
            Counterattack.DamInc.remove(evt.getPlayer().getName());
        }
    }
}

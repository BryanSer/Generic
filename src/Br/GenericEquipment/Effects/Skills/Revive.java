/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericEquipment.Effects.Skills;

import Br.GenericAttribute.Datas.AttrEndEvent;
import Br.GenericEquipment.Effects.Skill;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/**
 *
 * @author Bryan_lzh
 */
public class Revive extends Skill {

    int cd;
    Map<String, Long> CDs = new HashMap<>();
    
    private Revive(int i[]){
        this.cd = i[0];
    }
    
    public static Skill getSkill(int... i) {
        return new Revive(i);
    }

    @Override
    public void onSkill(Event evt) {
        if (!(evt instanceof AttrEndEvent)) {
            return;
        }
        AttrEndEvent aee = (AttrEndEvent) evt;
        Player p = aee.getEntity();
        if (this.CDs.containsKey(p.getName()) && this.CDs.get(p.getName()) + this.cd * 1000L > System.currentTimeMillis()) {
            return;
        }
        if (aee.getFinalDamage() >= aee.getEntity().getHealth()) {
            aee.setCancelled(true);
            aee.getEntity().setHealth(aee.getEntity().getMaxHealth());
            this.CDs.put(p.getName(), System.currentTimeMillis());
        }
    }

    @Override
    public Class<? extends Event> getEvent() {
        return AttrEndEvent.class;
    }

}

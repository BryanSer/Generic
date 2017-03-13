/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericEquipment.Effects.Skills;

import Br.GenericAttribute.Datas.CritEvent;
import Br.GenericEquipment.Effects.Skill;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Bryan_lzh
 */
public class Excitation extends Skill {

    int time;
    int cd;
    Map<String, Long> CDs = new HashMap<>();

    private Excitation(int[] i) {
        this.time = i[0];
        this.cd = i[1];
    }

    public static Skill getSkill(int... i) {
        return new Excitation(i);
    }

    @Override
    public void onSkill(Event evt) {
        if (!(evt instanceof CritEvent)) {
            return;
        }
        CritEvent ce = (CritEvent) evt;
        Player p = ce.getPlayer();
        if (this.CDs.containsKey(p.getName()) && this.CDs.get(p.getName()) + this.cd * 1000l > System.currentTimeMillis()) {
            return;
        }
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time * 20, 1));
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time * 20, 1));
        this.CDs.put(p.getName(), System.currentTimeMillis());
    }

    @Override
    public Class<? extends Event> getEvent() {
        return CritEvent.class;
    }

}

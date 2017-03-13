/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericEquipment.Effects.Skills;

import Br.GenericEquipment.Effects.Skill;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Bryan_lzh
 */
public class Unstoppable extends Skill {

    double health;
    int time;
    int cd;
    Map<String, Long> CDs = new HashMap<>();

    private Unstoppable(int[] i) {
        this.health = (i[0] * 0.01d);
        this.time = i[1];
        this.cd = i[2];
    }

    public static Skill getSkill(int... i) {
        return new Unstoppable(i);
    }

    @Override
    public void onSkill(Event evt) {
        if (!(evt instanceof EntityDamageEvent)) {
            return;
        }
        EntityDamageEvent ede = (EntityDamageEvent) evt;
        Player p = (Player) ede.getEntity();
        if (this.CDs.containsKey(p.getName()) && this.CDs.get(p.getName()) + this.cd * 1000L > System.currentTimeMillis()) {
            return;
        }
        if (p.getHealth() / p.getMaxHealth() <= this.health) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time * 20, 4));
            this.CDs.put(p.getName(), System.currentTimeMillis());
        }
    }

    @Override
    public Class<? extends Event> getEvent() {
        return EntityDamageEvent.class;
    }

}

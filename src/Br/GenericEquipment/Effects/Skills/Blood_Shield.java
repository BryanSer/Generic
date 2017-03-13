/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericEquipment.Effects.Skills;

import Br.GenericAttribute.Datas.VampireEvent;
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
public class Blood_Shield extends Skill {

    int sh;
    int cd;
    Map<String, Long> CDs = new HashMap<>();

    private Blood_Shield(int[] i) {
        this.sh = i[0];
        this.cd = i[1];
    }

    public static Skill getSkill(int... i) {
        return new Blood_Shield(i);
    }

    @Override
    public void onSkill(Event evt) {
        if (!(evt instanceof VampireEvent)) {
            return;
        }
        VampireEvent ve = (VampireEvent) evt;
        Player p = ve.getPlayer();
        if (this.CDs.containsKey(p.getName()) && this.CDs.get(p.getName()) + this.cd * 1000l > System.currentTimeMillis()) {
            return;
        }
        int level = sh / 4;
        p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 500, level));
        this.CDs.put(p.getName(), System.currentTimeMillis());

    }

    @Override
    public Class<? extends Event> getEvent() {
        return VampireEvent.class;
    }

}

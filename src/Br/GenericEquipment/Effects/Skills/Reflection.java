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
public class Reflection extends Skill {

    double 反伤;
    int cd;
    Map<String, Long> CDs = new HashMap<>();

    private Reflection(int[] i) {
        this.反伤 = i[0] * 0.01d;
        this.cd = i[1];
    }

    public static Skill getSkill(int... i) {
        return new Reflection(i);
    }

    @Override
    public void onSkill(Event evt) {
        if (!(evt instanceof AttrEndEvent)) {
        }
        AttrEndEvent aee = (AttrEndEvent) evt;
        Player p = aee.getEntity();
        if (this.CDs.containsKey(p.getName()) && this.CDs.get(p.getName()) + this.cd * 1000L > System.currentTimeMillis()) {
            return;
        }
        double d = aee.getFinalDamage() * this.反伤;
        aee.setFinalDamage(aee.getFinalDamage() + d);
        aee.getEntity().sendMessage("§6反伤技能触发 造成了" + d + "额外伤害");

        this.CDs.put(p.getName(), System.currentTimeMillis());
    }

    @Override
    public Class<? extends Event> getEvent() {
        return AttrEndEvent.class;
    }

}

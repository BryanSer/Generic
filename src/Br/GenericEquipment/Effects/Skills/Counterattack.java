/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericEquipment.Effects.Skills;

import Br.GenericAttribute.Datas.BlockEvent;
import Br.GenericEquipment.Effects.Skill;
import Br.GenericEquipment.Main;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/**
 *
 * @author Bryan_lzh
 */
public class Counterattack extends Skill {

    static boolean first = false;

    public static Map<String, Integer> DamInc = new HashMap<>();

    int vaule;

    private Counterattack(int[] i) {
        this.vaule = i[0];
    }

    public static Skill getSkill(int... i) {
        if (!first) {
            Bukkit.getPluginManager().registerEvents(new CListener(), Main.Main);
            first = true;
        }
        return new Counterattack(i);
    }

    @Override
    public void onSkill(Event evt) {
        if (!(evt instanceof BlockEvent)) {
            return;
        }
        BlockEvent be = (BlockEvent) evt;
        Player p = be.getPlayer();
        DamInc.put(p.getName(), this.vaule);
    }

    @Override
    public Class<? extends Event> getEvent() {
        return BlockEvent.class;
    }

}

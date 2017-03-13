/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericEquipment.Effects.Skills;

import Br.GenericEquipment.Effects.Skill;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Bryan_lzh
 */
public class Buff extends Skill {

    PotionEffect pe;

    private Buff(int[] i) {
        pe = new PotionEffect(PotionEffectType.getById(i[0]), 50, i[1] - 1);
    }
    
    public static Skill getSkill(int... i) {
        return new Buff(i);
    }

    @Override
    public void onSkill(Event evt) {
        if (!(evt instanceof PlayerMoveEvent)) {
            return;
        }
        PlayerMoveEvent pme = (PlayerMoveEvent) evt;
        pme.getPlayer().addPotionEffect(pe);
    }

    @Override
    public Class<? extends Event> getEvent() {
        return PlayerMoveEvent.class;
    }

}

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
public class Shield extends Skill {

    double thr;
    double sh;
    int cd;
    Map<String, Long> CDs = new HashMap<>();

    private Shield(int... i) {
        this.thr = (i[0] * 0.01d);
        this.sh = (i[1] * 0.01d);
        this.cd = i[2];
    }

    public static Skill getSkill(int... i) {
        return new Shield(i);
    }

    @Override
    public void onSkill(Event evt) {
        if (!(evt instanceof EntityDamageEvent)) {
            return;
        }
        EntityDamageEvent entityDamageEvent = (EntityDamageEvent) evt;
        Player p = (Player) entityDamageEvent.getEntity();
        if (this.CDs.containsKey(p.getName()) && this.CDs.get(p.getName()) + this.cd * 1000L > System.currentTimeMillis()) {
            return;
        }
        if (p.getHealth() / p.getMaxHealth() <= this.thr) {
            int shi = (int) (p.getMaxHealth() * this.sh);
            int level = shi / 4;
            p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 500, level));
            this.CDs.put(p.getName(), System.currentTimeMillis());
        }
    }

//    public static void main(String[] args){
//        try {
//            Method m = Shield.class.getMethod("getSkill", int[].class);
//            System.out.println(m.invoke(null,new int[]{1,2}));
//        } catch (NoSuchMethodException ex) {
//            Logger.getLogger(Shield.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SecurityException ex) {
//            Logger.getLogger(Shield.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            Logger.getLogger(Shield.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalArgumentException ex) {
//            Logger.getLogger(Shield.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InvocationTargetException ex) {
//            Logger.getLogger(Shield.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    @Override
    public Class<? extends Event> getEvent() {
        return EntityDamageEvent.class;
    }
}

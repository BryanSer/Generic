/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericEquipment.Effects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.Event;

/**
 *
 * @author Bryan_lzh
 */
public class SkillEffect extends Effect {

    public enum SkillType {
        Shield(Br.GenericEquipment.Effects.Skills.Shield.class),
        Blood_Shield(Br.GenericEquipment.Effects.Skills.Blood_Shield.class),
        Unstoppable(Br.GenericEquipment.Effects.Skills.Unstoppable.class),
        Excitation(Br.GenericEquipment.Effects.Skills.Excitation.class),
        Counterattack(Br.GenericEquipment.Effects.Skills.Counterattack.class),
        Reflection(Br.GenericEquipment.Effects.Skills.Reflection.class),
        Revive(Br.GenericEquipment.Effects.Skills.Revive.class),
        Buff(Br.GenericEquipment.Effects.Skills.Buff.class);

        private Class<? extends Skill> c;

        SkillType(Class<? extends Skill> s) {
            this.c = s;
        }

        public Skill 构造(int[] obj) {
            try {
                Method m = c.getMethod("getSkill", int[].class);
                return (Skill) m.invoke(null, obj);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(SkillEffect.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(SkillEffect.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(SkillEffect.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(SkillEffect.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(SkillEffect.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }

    }

    private List<Skill> Skills = new ArrayList<>();

    @Override
    public void Anlysis(String v) {
        String[] str = v.split(",");
        for (String s : str) {
            for (SkillType st : SkillType.values()) {
                if (s.contains(st.name())) {
                    String[] va = s.split(":")[1].split("&");
                    int vaule[] = new int[va.length];
                    for (int i = 0; i < vaule.length; i++) {
                        vaule[i] = Integer.parseInt(va[i]);
                    }
                    Skill skill = st.构造(vaule);
                    if (skill != null) {
                        Skills.add(skill);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void onEvent(Event evt) {
        for (Skill sk : Skills) {
            if (sk.getEvent().isInstance(evt)) {
                sk.onSkill(evt);
            }
        }
    }

}

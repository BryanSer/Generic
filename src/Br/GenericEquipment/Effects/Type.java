/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericEquipment.Effects;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bryan_lzh
 */
public enum Type {
    None("None", NoneEffect.class),
    Attr("Attr", AttrEffect.class),
    Skill("Skill", SkillEffect.class);
    private String Name;
    private Class<? extends Effect> c;

    private Type(String name, Class<? extends Effect> e) {
        this.c = e;
        this.Name = name;
    }

    public String getName() {
        return Name;
    }

    public Effect 创建实例() {
        try {
            return this.c.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(Type.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Type.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Effect[] Anlysis(String v) {
        String str[] = (v.contains("|")) ? v.split("\\|") : new String[]{v};
        List<Effect> effects = new ArrayList<>();
        int index = 0;
        for (String s : str) {
            for (Type t : Type.values()) {
                if (s.contains(t.getName())) {
                    Effect eff = t.创建实例();
                    if (eff != null) {
                        if (s.contains(":[")) {
                            s = s.substring(s.indexOf("[") + 1, s.indexOf("]"));
                        }
                        eff.Anlysis(s);
                        effects.add(eff);
                    }
                    break;
                }
            }
            index++;
        }
        return effects.toArray(new Effect[effects.size()]);
    }

}

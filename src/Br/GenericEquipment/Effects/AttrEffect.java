/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericEquipment.Effects;

import Br.GenericAttribute.Datas.AttType;
import Br.GenericAttribute.Datas.AttrEvent;
import java.util.EnumMap;
import java.util.Map;
import org.bukkit.event.Event;

/**
 *
 * @author Bryan_lzh
 */
public class AttrEffect extends Effect {

    Map<AttType, Double> Attrs = new EnumMap<>(AttType.class);

    @Override
    public void Anlysis(String v) {
        String[] str = v.split(",");
        for (String s : str) {
            for (AttType at : AttType.values()) {
                if (s.contains(at.name())) {
                    s = s.split(":")[1];
                    double d = Double.parseDouble(s);
                    Attrs.put(at, d);
                }
            }
        }
    }

    @Override
    public String toString() {
        String s = "Attr:[";
        for (Map.Entry<AttType, Double> e : Attrs.entrySet()) {
            s = s + e.getKey().name() + ":" + e.getValue() + ",";
        }
        s = s.substring(0, s.length() - 1);
        s = s + "]";
        return s;
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof AttrEvent) {
            AttrEvent evt = (AttrEvent) e;
            evt.getDamagerAttData().Add(Attrs);
        }
    }

}

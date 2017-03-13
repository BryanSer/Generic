/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericAttribute;

import Br.GenericAttribute.Datas.AttType;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;

/**
 *
 * @author Bryan_lzh
 */
public class Entry {

    //private Map<AttType, Double> Attrs = new EnumMap<>(AttType.class);
    private String DisplayName;
    private AttType type;
    private double vaule;

    public Entry(String sd, String s) {
        this.DisplayName = ChatColor.translateAlternateColorCodes('&', sd);
        try {
            String k[] = s.split(":");
            AttType a = AttType.valueOf(k[0]);
            if (k[1].contains("%")) {
                k[1] = k[1].replaceAll("%", "");
            }
            double d = Double.parseDouble(k[1]);
            this.type = a;
            this.vaule = d;
        } catch (Exception e) {
        }
    }

    public AttType getType() {
        return type;
    }

    public double getVaule() {
        return vaule;
    }
    
    public String getDisplayName() {
        return DisplayName;
    }
}

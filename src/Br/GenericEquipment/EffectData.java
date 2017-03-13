/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericEquipment;

import Br.GenericEquipment.Effects.Effect;
import Br.GenericEquipment.Effects.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author Bryan_lzh
 */
public class EffectData {

    Effect[] level1;
    Effect[] level2;
    Effect[] level3;
    Effect[] level4;
    private String Name;
    private List<String> lore;

    public EffectData(FileConfiguration config, String key) {
        this.Name = config.getString(key + ".Name");
        this.lore = config.getStringList(key + ".Lore");
        for (int i = 0; i < lore.size(); i++) {
            String get = lore.get(i);
            lore.set(i, Name + get);
        }
        this.level1 = Type.Anlysis(config.getString(key + ".Effect.1"));
        this.level2 = Type.Anlysis(config.getString(key + ".Effect.2"));
        this.level3 = Type.Anlysis(config.getString(key + ".Effect.3"));
        this.level4 = Type.Anlysis(config.getString(key + ".Effect.4"));
    }

    public List<Effect> getEffect(int level) {
        List<Effect> effs = new ArrayList<>();
        if (level >= 1) {
            effs.addAll(Arrays.asList(level1));
        }
        if (level >= 2) {
            effs.addAll(Arrays.asList(level2));
        }
        if (level >= 3) {
            effs.addAll(Arrays.asList(level3));
        }
        if (level >= 4) {
            effs.addAll(Arrays.asList(level4));
        }
        return effs;
    }

    public String getName() {
        return Name;
    }

    public List<String> getLore() {
        return lore;
    }

}

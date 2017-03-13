/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericEquipment.Effects;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

/**
 *
 * @author Bryan_lzh
 */
public abstract class Skill {

    public abstract void onSkill(Event evt);
    
    public abstract Class<? extends Event> getEvent();
}

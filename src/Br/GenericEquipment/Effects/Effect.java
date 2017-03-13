/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericEquipment.Effects;

import org.bukkit.event.Event;

/**
 *
 * @author Bryan_lzh
 */
public abstract class Effect{

    protected Type type;

    public abstract void Anlysis(String s);

    
    public abstract void onEvent(Event evt);
}

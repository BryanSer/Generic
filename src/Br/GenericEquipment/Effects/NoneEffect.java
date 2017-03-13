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
public class NoneEffect extends Effect {
    
    public NoneEffect(){
        super.type = Type.None;
    }

    @Override
    public void Anlysis(String s) {
    }

    @Override
    public String toString() {
        return "None";
    }

    @Override
    public void onEvent(Event evt) {
    }

}

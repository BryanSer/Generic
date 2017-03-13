package Br.GenericAttribute.Datas;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Bryan_lzh
 */
public class AttrEvent extends Event {

    Player p;
    AttData da;

    public AttrEvent(Player p, AttData AD) {
        this.p = p;
        this.da = AD;
    }

    public Player getPlayer() {
        return p;
    }

    public AttData getDamagerAttData() {
        return da;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Br.GenericAttribute.Datas;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 *
 * @author Bryan_lzh
 */
public class AttrEndEvent extends Event implements Cancellable{

    AttData ea;
    AttData da;
    EntityDamageByEntityEvent evt;
    Player Entity;
    double FinalDamage;

    public AttrEndEvent(AttData ea, AttData da, EntityDamageByEntityEvent evt, Player entity,double d) {
        this.ea = ea;
        this.da = da;
        this.evt = evt;
        this.Entity = Entity;
        this.FinalDamage = d;
    }

    public AttData getEntityAttr() {
        return ea;
    }

    public AttData getDamagerAttr() {
        return da;
    }

    public void setFinalDamage(double FinalDamage) {
        this.FinalDamage = FinalDamage;
    }

    public double getFinalDamage() {
        return FinalDamage;
    }

    public EntityDamageByEntityEvent getEvent() {
        return evt;
    }

    public Player getEntity() {
        return Entity;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return can;
    }
    
    boolean can = false;

    @Override
    public void setCancelled(boolean cancel) {
        can = cancel;
    }
}

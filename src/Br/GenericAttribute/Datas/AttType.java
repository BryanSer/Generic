package Br.GenericAttribute.Datas;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Bryan_lzh
 */
public enum AttType {
    Atk("伤害"),
    Crit("会心几率", true),
    CritDam("会心加成", true),
    MovingSpeed("移速", true),
    VampireCha("嗜血几率", true),
    Vampire("嗜血加成"),
    Recover("生命恢复"),
    MaxHealth("生命值"),
    Def("护甲"),
    Level("等级"),
    AtkInc("伤害加成", true),
    DefInc("伤害减免", true);
    private String DisplayName;
    private boolean Per = false;

    private AttType(String dp) {
        this.DisplayName = dp;
    }

    private AttType(String dp, boolean per) {
        this.DisplayName = dp;
        this.Per = per;
    }

    public String getDisplayName() {
        return this.DisplayName;
    }

    public boolean isPer() {
        return this.Per;
    }
}

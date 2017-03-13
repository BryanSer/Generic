package Br.GenericAttribute.Datas;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Br.GenericAttribute.Entry;
import Br.GenericAttribute.Main;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Bryan_lzh
 */
public class Data {

    public static Main Plugin;
    public static LinkedHashMap<String, Entry> Entrys = new LinkedHashMap<>();
    public static Map<String, Long> CacheTime = new HashMap<>();
    public static Map<String, AttData> PlayerCache = new HashMap<>();
    public static ItemStack Fix;
    public static ItemStack Add;

}

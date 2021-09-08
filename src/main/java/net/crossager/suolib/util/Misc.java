package net.crossager.suolib.util;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Misc {
    public static Location toBlockLocation(Location loc){
        return new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }
    public static boolean compare(Object o, Object obj){
        if (o == null && obj == null) return true;
        if (o == null) return false;
        if (obj == null) return false;
        return o.equals(obj);
    }
    public static <T> T[] toArray(T... t){
        List<T> list = new ArrayList<>();
        for (T i : t){
            list.add(i);
        }
        return (T[]) list.toArray();
    }
}

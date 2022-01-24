package net.crossager.suolib.util;

import com.google.gson.Gson;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {
    public static Location toBlockLocation(Location loc){
        return new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }
    public static boolean compare(Object o, Object obj){
        if (o == null && obj == null) return true;
        if (o == null) return false;
        if (obj == null) return false;
        return o.equals(obj);
    }
    public static boolean isSimilar(Object o1, Object o2){
        if (o1 == null && o2 == null) return true;
        if (o1 == null) return false;
        if (o2 == null) return false;
        if(o1.getClass() != o2.getClass()) return false;
        return new Gson().toJson(o1).equals(new Gson().toJson(o2));
    }
    public static <T> List<T> toList(T[] t){
        List<T> list = new ArrayList<>();
        for (T i : t){
            list.add(i);
        }
        return list;
    }
    public static <T> T[] toArray(T... t){
        return t;
    }
    public static void log(String s){
        log(Level.INFO, s);
    }
    public static void log(Level l,String s){
        Logger.getLogger("SuoLib").log(l, s);
    }

    public static OSType getSystemOS(){
        String s = System.getProperty("os.name").toLowerCase();
        return s.contains("win") ? OSType.WINDOWS : (s.contains("mac") ? OSType.OSX : (s.contains("solaris") ? OSType.SOLARIS :
                (s.contains("sunos") ? OSType.SOLARIS : (s.contains("linux") ? OSType.LINUX : (s.contains("unix") ? OSType.LINUX : OSType.UNKNOWN)))));
    }
}

package net.crossager.suolib.util;

import net.crossager.common.reflection.MethodUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class NMSUtils {
    public static Class<?> getCraftBukkitClass(String name){
        return getVersionDependantClass("org.bukkit.craftbukkit", name);
    }

    public static Class<?> getVersionDependantClass(String pack, String name){
        String version = Bukkit.getServer().getClass().getPackage().getName().split(".")[3];
        try {
            return Class.forName(pack + "." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static EntityPlayer getHandle(Player p){
        try {
            return (EntityPlayer) MethodUtils.getMethod(p.getClass(), "getHandle", true).invoke(p);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getHandle(Object o){
        try {
            return MethodUtils.getMethod(o.getClass(), "getHandle", true).invoke(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static WorldServer getHandle(World w){
        try {
            return (WorldServer) MethodUtils.getMethod(w.getClass(), "getHandle", true).invoke(w);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MinecraftServer getServer(Server s){
        try {
            return (MinecraftServer) (DedicatedServer) MethodUtils.getMethod(s.getClass(), "getServer", true).invoke(s);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static IChatBaseComponent toChatBaseComponent(String s){
        try {
            return ((IChatBaseComponent) MethodUtils.getMethod(NMSUtils.getCraftBukkitClass("util.CraftChatMessage"), "fromStringOrNull", true, String.class).invoke(null, s));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }
}

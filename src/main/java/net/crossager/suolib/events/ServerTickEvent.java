package net.crossager.suolib.events;

import net.crossager.suolib.player.ServerUtility;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ServerTickEvent extends Event {
    private static final HandlerList list = new HandlerList();
    @NotNull
    @Override
    public HandlerList getHandlers() {
        return list;
    }
    public static HandlerList getHandlerList(){
        return list;
    }
    public double getTickTime(){
        return ServerUtility.getTps();
    }
}

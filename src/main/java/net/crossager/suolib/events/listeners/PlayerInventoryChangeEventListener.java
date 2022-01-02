package net.crossager.suolib.events.listeners;

import net.crossager.suolib.SuoLib;
import net.crossager.suolib.events.PlayerInventoryChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.PlayerInventory;

public class PlayerInventoryChangeEventListener implements Listener {
    private SuoLib plugin;

    public PlayerInventoryChangeEventListener(SuoLib plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void event(InventoryClickEvent e){
        if(e.getInventory() instanceof PlayerInventory)
            e.setCancelled(call((Player) e.getWhoClicked()));
    }

    @EventHandler
    public void event(PlayerDropItemEvent e){
        e.setCancelled(call(e.getPlayer()));
    }

    @EventHandler
    public void event(PlayerItemConsumeEvent e){
        e.setCancelled(call(e.getPlayer()));
    }

    @EventHandler
    public void event(PlayerSwapHandItemsEvent e){
        e.setCancelled(call(e.getPlayer()));
    }

    @EventHandler
    public void event(PlayerItemBreakEvent e){
        boolean cancel = call(e.getPlayer());
        if(cancel) e.getBrokenItem().setAmount(1);
    }

    @EventHandler
    public void event(PlayerItemDamageEvent e){
        e.setCancelled(call(e.getPlayer()));
    }

    @EventHandler
    public void event(PlayerItemMendEvent e){
        e.setCancelled(call(e.getPlayer()));
    }

    @EventHandler
    public void event(EntityPickupItemEvent e){
        if(e.getEntity() instanceof Player) e.setCancelled(call((Player) e.getEntity()));
    }
    private boolean call(Player p){
        PlayerInventoryChangeEvent event = new PlayerInventoryChangeEvent(p);
        plugin.getServer().getPluginManager().callEvent(event);
        return event.isCancelled();
    }
}

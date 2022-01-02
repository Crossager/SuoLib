package net.crossager.suolib.gui;

import net.crossager.suolib.gui.events.ButtonClick;
import net.crossager.suolib.SuoLib;
import net.minecraft.network.protocol.game.PacketPlayOutWindowItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.Predicate;

public class InventoryGui implements Listener, Gui {
    private Inventory inv;
    private Map<Integer, ButtonClick> buttons = new HashMap<>();

    public InventoryGui(int size, String name, JavaPlugin java){
        inv = Bukkit.createInventory(null, size, name);
        Bukkit.getServer().getPluginManager().registerEvents(this, java);
    }
    public InventoryGui(int size, String name){
        this(size, name, SuoLib.getProvider().getPlugin());
    }

    public void openGui(Player p){
        p.openInventory(inv);
    }

    public void setIf(Predicate<Integer> p, ItemStack item){
        for(int i = 0; i < inv.getSize(); i++){
            if(p.test(i)){
                setElement(i, item);
            }
        }
    }
    public void border(ItemStack item){
        for(int i = 0; i < inv.getSize(); i++){
            if(i < 9) setElement(i, item);
            if((i + 1) % 9 == 0){
                setElement(i, item);
                setElement(i - 8, item);
            }
            if(i > (inv.getSize() - 9)) setElement(i, item);
        }
    }

    @EventHandler
    public void event(InventoryClickEvent e){
        if (e.getInventory().equals(inv)) {
            e.setCancelled(true);
            if(buttons.containsKey(e.getRawSlot())){
                buttons.get(e.getRawSlot()).click((Player) e.getWhoClicked(), e.getClick());
            }
        }
    }
    public void setElement(int i, ItemStack element){
        if(buttons.containsKey(i)) buttons.remove(i);
        if (element instanceof Button)buttons.put(i, ((Button)element).getClick());
        inv.setItem(i, element);
    }
    public void setElement(int i, Button element){
        if(buttons.containsKey(i)) buttons.remove(i);
        buttons.put(i, element.getClick());
        inv.setItem(i, element);
    }

    public ItemStack getElement(int i){
        if (inv.getItem(i) == null) return new ItemStack(Material.AIR);
        return inv.getItem(i);
    }
    public List<ItemStack> getElements() {
        return Arrays.asList(inv.getContents());
    }
    public final List<Player> getViewers(){
        List<Player> p = new ArrayList<>();
        for(HumanEntity h : inv.getViewers()){
            p.add((Player) h);
        }
        return p;
    }
    public void clear(){
        Arrays.asList(inv.getContents()).clear();
    }

    public static final ItemStack placeholder(Material type) {
        ItemStack itemStack = new ItemStack(type, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(" ");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public int getSize() {
        return inv.getSize();
    }

    public Inventory asInventory(){return inv;}
}

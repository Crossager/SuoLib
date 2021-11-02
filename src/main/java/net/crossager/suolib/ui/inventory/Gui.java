package net.crossager.suolib.ui.inventory;

import net.crossager.suolib.SuoLib;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Gui implements Listener {
    private Inventory inv;
    private List<ItemStack> elements;
    private int size;

    public Gui(int size, String name){
        elements = new ArrayList<>(size);
        this.size = size;
        Bukkit.getServer().getPluginManager().registerEvents(this, SuoLib.getPlugin());
        inv = Bukkit.createInventory(null, size, name);
    }

    private void createInventory(){
        for (int i = 0; i < elements.size(); i++){
            inv.setItem(i, getElement(i));
        }
    }

    public void openGui(Player p){
        createInventory();
        p.openInventory(inv);
    }

    @EventHandler
    public void event(InventoryClickEvent e){
        if (e.getClickedInventory().equals(inv)) {
            e.setCancelled(true);
            if(elements.get(e.getSlot()) instanceof Button){
                ((Button)elements.get(e.getSlot())).click(new ButtonClick(e.getClick(), (Player) e.getWhoClicked()));
            }
        }
    }
    public void setElement(ItemStack element, int i){
        elements.set(i, element);
    }

    public ItemStack getElement(int i){
        if (elements.get(i) == null) return new ItemStack(Material.AIR);
        return elements.get(i);
    }
    public List<ItemStack> getElements() {

        return elements;
    }

    public void clear(){
        elements.clear();
    }

    public static final ItemStack placeholder(Material type) {
        ItemStack itemStack = new ItemStack(type, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public int getSize() {
        return size;
    }
}

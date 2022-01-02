package net.crossager.suolib.gui.events;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface ButtonClick {
    public void click(Player p, ClickType type);
}

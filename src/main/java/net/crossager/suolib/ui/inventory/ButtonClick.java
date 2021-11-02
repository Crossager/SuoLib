package net.crossager.suolib.ui.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class ButtonClick {
    private final Player clicker;
    private final ClickType type;

    public ButtonClick(ClickType type, Player clicker){
        this.type = type;
        this.clicker = clicker;
    }

    public ClickType getType() {
        return type;
    }

    public Player getClicker() {
        return clicker;
    }
}

package net.crossager.suolib.server;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

public class ServerNPCClick {
    private final Player clicker;
    private final EquipmentSlot hand;
    private final boolean isLeftClick;

    public ServerNPCClick(Player clicker, EquipmentSlot hand, boolean isLeftClick){
        this.clicker = clicker;
        this.hand = hand;
        this.isLeftClick = isLeftClick;
    }

    public Player getClicker() {
        return clicker;
    }

    public EquipmentSlot getHand() {
        return hand;
    }

    public boolean isLeftClick() {
        return isLeftClick;
    }
}

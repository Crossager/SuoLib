package net.crossager.suolib.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

public interface PlayerEntityInteraction {
    void click(Player clicker, EquipmentSlot hand, boolean isLeftClick);
}

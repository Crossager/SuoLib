package net.crossager.suolib.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class PlayerUseInvalidEntityEvent extends PlayerEvent {

    private final int entityId;
    private final Vector position;
    private final EquipmentSlot hand;

    public PlayerUseInvalidEntityEvent(Player who, int entityId, Vector position) {
        this(who, entityId, position, EquipmentSlot.HAND);
    }

    public PlayerUseInvalidEntityEvent(Player who, int entityId, Vector position, @NotNull EquipmentSlot hand) {
        super(who);
        this.entityId = entityId;
        this.position = position;
        this.hand = hand;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public Vector getClickedPosition() {
        return position;
    }

    @NotNull
    public EquipmentSlot getHand() {
        return this.hand;
    }

    private static final HandlerList HANDLERS = new HandlerList();
    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList(){return HANDLERS;}
}

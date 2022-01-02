package net.crossager.suolib.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerAttackInvalidEntityEvent extends PlayerEvent {

    private final int entityId;

    public PlayerAttackInvalidEntityEvent(Player who, int entityId) {
        super(who);
        this.entityId = entityId;
    }

    public int getEntityId() {
        return this.entityId;
    }

    private static final HandlerList HANDLERS = new HandlerList();
    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList(){return HANDLERS;}
}

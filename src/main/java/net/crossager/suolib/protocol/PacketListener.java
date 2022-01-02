package net.crossager.suolib.protocol;

import net.minecraft.network.protocol.Packet;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public abstract class PacketListener<P extends Packet<?>> implements Cancellable {
    private final Class<P> packet;
    private boolean cancel = false;

    public PacketListener(Class<P> type){
        this.packet = type;
    }

    public Class<P> getPacket() {
        return packet;
    }

    public abstract void listen(P packet, Player player);

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }
}

package net.crossager.suolib.protocol;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import net.crossager.suolib.SuoLib;
import net.crossager.suolib.util.NMSUtils;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class PacketReader implements Listener {

    private SuoLib plugin;
    private PacketManager pm;

    public PacketReader(SuoLib plugin, PacketManager pm){
        this.plugin = plugin;
        this.pm = pm;
        for(Player p : Bukkit.getOnlinePlayers()){
            this.inject(p);
        }
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void join(PlayerJoinEvent e){
        inject(e.getPlayer());
        }
    public boolean inject(Player player){
        Channel channel = NMSUtils.getHandle(player).b.a.k;
        PacketReader reader = this;
        if(channel.pipeline().get("PacketInReader") != null)
            channel.pipeline().remove("PacketInReader");

        try {
            channel.pipeline().addAfter("decoder", "PacketInReader", new MessageToMessageDecoder<Packet<? extends PacketListener>>() {
                @Override
                protected void decode(ChannelHandlerContext channelHandlerContext, Packet<? extends PacketListener> packet, List<Object> list) throws Exception {
                    try {
                        boolean cancelled = false;
                        for (net.crossager.suolib.protocol.PacketListener<? extends Packet<? extends PacketListener>> listener : pm.getListeners()) {
                            if (packet.getClass().equals(listener.getPacket())) {
                                cancelled = reader.read(listener, packet, player, cancelled);
                            }
                        }
                        if (!cancelled) list.add(packet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        if(channel.pipeline().get("PacketOutReader") != null)
            channel.pipeline().remove("PacketOutReader");
        try {
            channel.pipeline().addAfter("encoder", "PacketOutReader", new MessageToMessageEncoder<Packet<?>>() {
                @Override
                protected void encode(ChannelHandlerContext channelHandlerContext, Packet<?> packet, List<Object> list) throws Exception {
                    try {
                        boolean cancelled = true;
                        for (net.crossager.suolib.protocol.PacketListener<? extends Packet<? extends PacketListener>> listener : pm.getListeners()) {
                            if (packet.getClass().equals(listener.getPacket())) {
                                cancelled = reader.read(listener, packet, player, cancelled);
                            }
                        }
                        if (!cancelled) list.add(packet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean uninject(Player player){
        try {
            Channel channel = NMSUtils.getHandle(player).b.a.k;
            if (channel.pipeline().get("PacketOutReader") != null)
                channel.pipeline().remove("PacketOutReader");
            if (channel.pipeline().get("PacketInReader") != null)
                channel.pipeline().remove("PacketInReader");
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private <P extends Packet<? extends PacketListener>> boolean read(net.crossager.suolib.protocol.PacketListener<P> listener, Packet<? extends PacketListener> p, Player pl, boolean cancel){
        listener.setCancelled(cancel);
        listener.listen((P) p, pl);
        return listener.isCancelled();
    }
}
